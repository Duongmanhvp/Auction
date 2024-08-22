import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import { jwtDecode } from 'jwt-decode';

/// convention for global function declaration:
/// - stateful functions (functions that refer to global variables) are declared with function expressions
/// - stateless functions are declared with arrow functions

const WS_URL = import.meta.env.VITE_WS_URL;

let stompState = null;
// {
//     connection: null,
//     receiptCallbackRegistry: null,
//     reconnectInterval: null,
//     userId: null
// };
const controlCallbacks = {};
const connectCallbacks = [];
const disconnectCallbacks = [];

function setOnConnect(callback) {
  if (typeof callback !== 'function') {
    throw new Error('Callback is required');
  }
  connectCallbacks.push(callback);
};

function setOnDisconnect(callback) {
  if (typeof callback !== 'function') {
    throw new Error('Callback is required');
  }
  disconnectCallbacks.push(callback);
};

async function setup() {
  stompState = {};
  const token = localStorage.getItem('token');
  if (!token) {
    throw new Error('No token found');
  }
  stompState.userId = jwtDecode(token).id;
  stompState.receiptCallbackRegistry = {};
  await setupConnection();
};

async function teardown() {
  teardownConnection();
  clearInterval(stompState.reconnectInterval);
  stompState = null;
};

function onControlMessage(type, callback) {
  controlCallbacks[type] = callback;
};

/// if callback is not provided, the function should return a promise
function subscribe(destination, onMessage, callback, errorCallback) {
  if (!stompState.connection?.client.connected) {
    throw new Error('Not connected');
  }
  const { client, subscriptionRegistry } = stompState.connection;

  const headers = generateHeaders();
  const subscription = client.subscribe(destination, onMessage, headers);

  const messageId = headers['receipt'];
  if (callback !== undefined) {
    registerReceiptCallback(messageId,
      (message) => {
        subscriptionRegistry[messageId] = subscription;
        callback?.(message);
      },
      errorCallback);
  } else {
    return new Promise((resolve, reject) => {
      registerReceiptCallback(messageId,
        (message) => {
          subscriptionRegistry[messageId] = subscription;
          resolve(message);
        },
        reject);
    });
  }
}

// return if the destination was subscribed (and thus unsubscribed)
function unsubscribe(destination) {
  if (!stompState.connection?.client.connected) {
    throw new Error('Not connected');
  }
  const { subscriptionRegistry } = stompState.connection;
  const subscription = subscriptionRegistry[destination];
  if (subscription) {
    subscription.unsubscribe();
    delete subscriptionRegistry[destination];
  }
  return !!subscription;
}

/// if callback is not provided, the function should return a promise
function send(destination, payload, callback, errorCallback) {
  if (!stompState.connection?.client.connected) {
    throw new Error('Not connected');
  }

  const headers = generateHeaders();
  stompState.connection.client.publish({
    destination,
    body: JSON.stringify(payload),
    headers
  });

  const messageId = headers['receipt'];
  if (callback !== undefined) {
    registerReceiptCallback(messageId, callback, errorCallback);
  } else {
    return new Promise((resolve, reject) => {
      registerReceiptCallback(messageId, resolve, reject);
    });
  }
}

export default {
  setOnConnect,
  setOnDisconnect,
  setup,
  teardown,
  onControlMessage,
  subscribe,
  unsubscribe,
  send
};



async function setupConnection() {
  stompState.connection = await createConnection(localStorage.getItem('token'));
  setupErrorHandler();
  setupSubscriptions();
  callConnectCallbacks();
}

function teardownConnection() {
  if (stompState.connection) {
    if (stompState.connection.client.connected) {
      stompState.connection.client.deactivate();
      callDisconnectCallbacks();
    }
    stompState.connection = null;
  }
}

function generateHeaders() {
  return {
    "receipt": uuidv4(),
    "Authorization": `Bearer ${localStorage.getItem('token')}`
  }
}

function registerReceiptCallback(messageId, callback, errorCallback) {
  stompState.receiptCallbackRegistry[messageId] = [callback, errorCallback];

  /// not the same as reading `stompState.connection.client.active` directly, see function for details
  const isStillActive = getSessionChecker();
  if (errorCallback) {
    setTimeout(() => {
      if (isStillActive()) {
        if (stompState.receiptCallbackRegistry[messageId]) {
          delete stompState.receiptCallbackRegistry[messageId];
          errorCallback(new Error('Request timed out'));
        }
      }
    }, 5000);
  }
}



const createConnection = async (token) => {
  const client = await createStompClient(token);
  const subscriptionRegistry = {};
  return { client, subscriptionRegistry };
}

function setupErrorHandler() {
  if (!stompState.connection?.client.connected) {
    throw new Error('Not connected');
  }

  const client = stompState.connection.client;
  client.onStompError = (frame) => {
    console.error('STOMP error', frame);
    onDisconnect();
  };
  client.onWebSocketClose = (event) => {
    console.log('WebSocket closed', event);
    onDisconnect();
  };
  client.onWebSocketError = (error) => {
    console.error('WebSocket error', error);
    onDisconnect();
  }
  client.onDisconnect = () => {
    console.log('Disconnected from WebSocket');
    onDisconnect();
  };
}

function setupSubscriptions() {
  if (!stompState.connection?.client.connected) {
    throw new Error('Not connected');
  }

  // this is a workaround for spring STOMP's lack of support for RECEIPT frame
  // this call must be placed before any other subscribe calls,
  // as this is what enables receiving responses to other requests,
  // including `subscribe` itself
  subscribe(
    `/user/${stompState.userId}/queue/receipts`,
    (message) => {
      handleReceipt(message);
    },
    () => console.log('subscribed to receipts'),
    () => { throw new Error('Failed to subscribe to receipts') }
  );
  // actually placing this first is fine too, as both are in the same function call (js's async rules)
  subscribe(
    `/user/${stompState.userId}/queue/control`,
    (message) => {
      handleControlMessage(message);
    },
    () => console.log('subscribed to control'),
    () => { throw new Error('Failed to subscribe to control') }
  );
}


function onDisconnect() {
  if (stompState && !stompState.reconnectInterval) {
    teardownConnection();
    stompState.reconnectInterval = setInterval(async function () {
      await setupConnection();
      clearInterval(stompState.reconnectInterval);
      stompState.reconnectInterval = null;
      connectCallbacks.forEach(callback => callback());
    }, 5000);
  }
}

function callConnectCallbacks() {
  connectCallbacks.forEach(callback => {
    try {
      callback();
    } catch (error) {
      console.error('Error in connect callback', error);
    }
  });
}

function callDisconnectCallbacks() {
  disconnectCallbacks.forEach(callback => {
    try {
      callback();
    } catch (error) {
      console.error('Error in disconnect callback', error);
    }
  });
}


function handleControlMessage(message) {
  const body = JSON.parse(message.body);
  const type = body['type'];
  const payload = body['data'];
  const callback = controlCallbacks[type];
  if (callback !== undefined) {
    callback?.(payload);
  }
  else {
    console.log('Unhandled control message:', type, payload);
  }
}

function handleReceipt(message) {
  const messageId = message.headers['receipt-id'];
  const response = JSON.parse(message.body);
  const callbackEntry = stompState.receiptCallbackRegistry[messageId];
  delete stompState.receiptCallbackRegistry[messageId];
  if (!callbackEntry) {
    return;
  }

  const { success } = response;
  const [callback, errorCallback] = callbackEntry;
  (success ? callback : errorCallback)?.(response);
}

// this creates a stateful function that reference the session at the time this function is called
// use to check if the session is still active when callbacks are
// this is not quite the same as reading `stompState.session.active` directly
function getSessionChecker() {
  // note that extracting the session into a variable is necessary
  // because the session object may be updated by the time the callback is called
  const currentConnection = stompState.connection?.client
  return () => stompState && currentConnection?.connected;
}



const createWebSocket = (token) => {
  console.log(`connecting to ${WS_URL} with token ${token}`);
  const socket = new WebSocket(WS_URL, [
    'Authorization-Bearer', token
  ]);
  socket.onopen = () => {
    console.log('WebSocket connected');
  };
  socket.onerror = (ev) => {
    console.error('WebSocket error', ev);
  };
  socket.onclose = (ev) => {
    console.log('WebSocket closed', ev);
  };
  return socket;
}

const createStompClient = (token) => {
  console.log('Creating STOMP client');
  return new Promise((resolve, reject) => {
    let initialized = false;
    const client = new Client({
      webSocketFactory: () => createWebSocket(token),
      connectHeaders: {
        login: "",
        passcode: "",
        Authorization: `Bearer ${token}`
      },
      reconnectDelay: 0,
      onConnect: (frame) => {
        console.log('Connected: ' + frame);
        initialized = true;
        resolve(client);
      },
      onWebSocketError: (error) => {
        console.error('WebSocket error', error);
        if (!initialized) {
          reject(error);
        }
      },
      onWebSocketClose: (event) => {
        console.log('WebSocket closed', event);
        if (!initialized) {
          reject(event);
        }
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame);
        if (!initialized) {
          reject(frame);
        }
      },
      onDisconnect: () => {
        console.log('Disconnected from WebSocket');
      }
    });
    client.debug = console.log;
    console.log('Activating STOMP client');
    client.activate();
  });
}
