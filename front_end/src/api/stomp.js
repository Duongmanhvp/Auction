import { Client } from '@stomp/stompjs';
import Websocket from 'ws';
import { v4 as uuidv4 } from 'uuid';
import jwt from 'jsonwebtoken';

/// here we have a convention that stateful functions (functions that refer to variables) are declared with function expressions
/// and stateless functions are declared with arrow functions

const wsurl = 'ws://localhost:8080/auction/ws';

const stompState = {
    connection: null,
    receiptCallbackRegistry: null,
    userId: null
};
const controlCallbacks = {};
const connectCallbacks = [];
const disconnectCallbacks = [];

const stompApi = {
    setOnConnect(callback) { 
        if (typeof callback !== 'function') {
            throw new Error('Callback is required');
        }
        connectCallbacks.push(callback);
    },
    
    setOnDisconnect(callback) {
        if (typeof callback !== 'function') {
            throw new Error('Callback is required');
        }
        disconnectCallbacks.push(callback);
    },

    async setup() {
        await setupConnection();
        stompState.receiptCallbackRegistry = {};
        stompState.userId = jwt.decode(token).id;
    },
    
    async teardown() {
        teardownConnection();
        stompState.receiptCallbackRegistry = null;
        stompState.userId = null;
    },
    
    onControlMessage(type, callback) {
        controlCallbacks[type] = callback;
    },
    
    // if callback is not provided, the function should return a promise
    subscribe(destination, onMessage, callback, errorCallback) {
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
    },
    
    // return if the destination was subscribed (and thus unsubscribed)
    unsubscribe(destination) {
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
    },
    
    // if callback is not provided, the function should return a promise
    send(destination, payload, callback, errorCallback) {
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
    },
};

export default stompApi;



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
  const websocket = await createWebSocket(token);
  const client = await createStompClient(websocket, token);
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

    // note that this call must be placed before any other subscribe calls,
    // as this is what enables receiving responses to other requests,
    // including `subscribe` itself
    subscribe(
        `/user/${stompState.userId}/queue/receipts`, 
        (message) => {
            handleReceipt(message);
        },
        () => console.log('subscribed to receipts'), 
        () => {throw new Error('Failed to subscribe to receipts')}
    );
    // actually placing this first is fine too, as both are in the same function call (js's async rules)
    subscribe(
        `/user/${stompState.userId}/queue/control`, 
        (message) => {
            handleControlMessage(message);
        },
        () => console.log('subscribed to control'),
        () => {throw new Error('Failed to subscribe to control')}
    );
}


function onDisconnect() {
  teardownConnection(); 
  const reconnectInterval = setInterval(async function() {
      setupConnection();
      clearInterval(reconnectInterval);
      connectCallbacks.forEach(callback => callback());
  }, 5000);
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
  return () => currentConnection?.connected;
}





const createWebSocket = (token) => {
  return new Promise ((resolve, reject) => {
      const socket = new WebSocket(wsurl, undefined, {
          headers: {
              Authorization: `Bearer ${token}`
          }
      });
      socket.onopen = () => {
          console.log('WebSocket connected');
          resolve(socket);
      };
      socket.onerror = (ev) => {
          console.error('WebSocket error', ev);
          reject(ev);
      };
  });
}

const createStompClient = (ws, token) => {
  return new Promise((resolve, reject) => {
      let initialized = false;
      const client = Client({
          webSocketFactory: () => ws,
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
    
      client.activate();
  });
}
