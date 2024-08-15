import { Client } from '@stomp/stompjs';
import Websocket from 'ws';
import { v4 as uuidv4 } from 'uuid';
import jwt from 'jsonwebtoken';

const wsurl = 'ws://localhost:8080/auction/ws';

const stompState = {
    websocket: null,
    client: null,
    subscriptionRegistry: null,
    receiptCallbackRegistry: null,
    controlCallbacks: {},
    session: null
};

const stompApi = {
    async onPageLoad() {
      const token = localStorage.getItem('token');
      if (!token) {
          return;
      }
      setup();
    },
    
    async onLogin() {
      const token = localStorage.getItem('token');
      if (!token) {
          throw new Error('No token found');
      }
      setup();
    },
    
    async onLogout() {
      if (stompState.client) {
          stompState.client.deactivate();
      }
      if (stompState.websocket) {
          stompState.websocket.close();
      }
      stompState.client = null;
      stompState.websocket = null;
      stompState.subscriptionRegistry = null;
      stompState.receiptCallbackRegistry = null;
      stompState.session.active = false; // do NOT remove, other callbacks may check this
      stompState.session = null;
    },
    
    onControlMessage(type, callback) {
        stompState.controlCallbacks[type] = callback;
    },
    
    // if callback is not provided, the function should return a promise
    subscribe(destination, onMessage, callback, errorCallback) {
        if (!stompState.client) {
            throw new Error('Not connected');
        }
    
        const headers = generateHeaders();
        const subscription = stompState.client.subscribe(destination, onMessage, headers);
        
        const messageId = headers['receipt'];
        if (callback !== undefined) {
            registerReceiptCallback(messageId,
                (message) => {
                    stompState.subscriptionRegistry[messageId] = subscription;
                    callback(message);
                }, 
                errorCallback);
        } else {
            return new Promise((resolve, reject) => {
                registerReceiptCallback(messageId, 
                    (message) => {
                        stompState.subscriptionRegistry[messageId] = subscription;
                        resolve(message);
                    },    
                    reject);
            });
        }
    },
    
    // return if the destination was subscribed (and thus unsubscribed)
    unsubscribe(destination) {
        if (!stompState.client) {
            throw new Error('Not connected');
        }
        const subscription = stompState.subscriptionRegistry[destination];
        if (subscription) {
            subscription.unsubscribe();
            delete stompState.subscriptionRegistry[destination];
        }
        return !!subscription;
    },
    
    // if callback is not provided, the function should return a promise
    send(destination, payload, callback, errorCallback) {
        if (!stompState.client) {
            throw new Error('Not connected');
        }
    
        const headers = generateHeaders();
        stompState.client.publish({ 
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


async function setup() {
    const token = localStorage.getItem('token');
    stompState.websocket = await createWebSocket(token);
    stompState.client = await createStompClient(stompState.ws, token);
    stompState.subscriptionRegistry = {};
    stompState.receiptCallbackRegistry = {};
    stompState.session = { 
        userId: jwt.decode(localStorage.getItem('token')).id, 
        active: true 
    };
  
    setupSubscriptions();
}

function createWebSocket(token) {
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

function createStompClient(ws, token) {
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

export default stompApi;

function setupSubscriptions() {
    if (!stompState.client) {
        throw new Error('Not connected');
    }

    const userId = stompState.session.userId;

    // note that this call must be placed before any other subscribe calls,
    // as this is what enables receiving responses to other requests,
    // including `subscribe` itself
    subscribe(
        `/user/${this}/queue/receipts`, 
        (message) => {
            handleReceipt(message);
        },
        () => console.log('subscribed to receipts'), 
        () => {throw new Error('Failed to subscribe to receipts')}
    );
    // actually placing this first is fine too, as both are in the same function call (js's async rules)
    subscribe(
        `/user/${userId}/queue/control`, 
        (message) => {
            handleControlMessage(message);
        },
        () => console.log('subscribed to control'),
        () => {throw new Error('Failed to subscribe to control')}
    );
}

function generateHeaders() {
    return {
        "receipt": uuidv4(),
        "Authorization": `Bearer ${localStorage.getItem('token')}`
    }
}

// this creates a stateful function that reference the session at the time this function is called
// use to check if the session is still active when callbacks are 
// this is not quite the same as reading `stompState.session.active` directly
function getSessionChecker() {
    // note that extracting the session into a variable is necessary
    // because the session object may be updated by the time the callback is called
    const currentSession = stompState.session;
    return () => currentSession.active;
}

function handleControlMessage(message) {
    const body = JSON.parse(message.body);
    const type = body['type'];
    const payload = body['data'];
    const callback = stompState.controlCallbacks[type];
    if (callback) {
        callback(payload);
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

function registerReceiptCallback(messageId, callback, errorCallback) {
    stompState.receiptCallbackRegistry[messageId] = [callback, errorCallback];

    /// not the same as reading `stompState.session.active` directly, see function for details
    const isStillActive = getSessionChecker();
    setTimeout(() => {
        if (isStillActive()) {
            if (stompState.receiptCallbackRegistry[messageId]) {
                delete stompState.receiptCallbackRegistry[messageId];
                errorCallback?.(new Error('Request timed out'));
            }
        }
    }, 5000);
}