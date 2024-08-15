import stompApi from './stomp';
import httpApi from './axios';

const config = {
    onJoinableAuction: null,
}

/**
 * @typedef {Object} AuctionSession
 * @property {Function} isOpen - Check if the session is open
 * @property {Function} bid - Place a bid
 * @property {Function} comment - Place
 * @property {Function} leave - Leave the auction room
 * @property {Function?} onStart - Callback for when the auction starts
 * @property {Function?} onEnd - Callback for when the auction ends
 * @property {Function?} onNotification - Callback for notifications
 * @property {Function?} onBid - Callback for bid updates
 * @property {Function?} onComment - Callback for comment updates
 */

/**
 * @param {string | number} auctionId
 * @param {Object} callbacks 
 * @param {Function?} callbacks.onStart
 * @param {Function?} callbacks.onEnd
 * @param {Function?} callbacks.onNotification
 * @param {Function?} callbacks.onBid
 * @param {Function?} callbacks.onComment
 * @returns {Promise<AuctionSession>}
 */
async function joinAuctionRoom(auctionId, callbacks) {
    try {
        const response = await (await httpApi.post(`/v1/auctions/${auctionId}/join`)).json();
        const wasActive = response['is_active'];

        const session = {
            onStart: callbacks.onStart,
            onEnd: callbacks.onEnd,
            onNotification: callbacks.onNotification,
            onBid: callbacks.onBid,
            onComment: callbacks.onComment
        };
        let sessionJoined = true;
        let sessionActive = wasActive;
        Object.defineProperties(session, {
            auctionId: {
                value: auctionId,
                writable: false
            },
            isJoined: {
                get: () => sessionJoined,
                writable: false
            },
            isActive: {
                get: () => sessionActive,
                writable: false
            },
            bid: {
                value: (amount) => bid(auctionId, amount),
                writable: false
            },
            comment: {
                value: (content) => comment(auctionId, content),
                writable: false
            },
            getCurrentPrice: {
                value: () => getCurrentPrice(auctionId),
                writable: false
            },
            getPastComments: {
                value: (from) => getPastComments(auctionId, from),
                writable: false
            },
            leave: {
                value: function() {
                    leaveAuctionRoom(auctionId)
                    sessionJoined = false;
                    delete this.isActive
                    delete this.bid
                    delete this.comment
                    delete this.getCurrentPrice
                    delete this.getPastComments
                    delete this.leave
                },
                writable: false
            }
        });

        const controlPromise = stompApi.subscribe(`/topic/auction/${auctionId}/control`, 
            (message) => {
                const body = JSON.parse(message.body);
                const type = body.type;
                if (type === "start") {
                    if (!wasActive) {
                        subscribeBid(auctionId, session);
                        subscribeComment(auctionId, session);
                    }
                    sessionActive = true;
                    session.onStart?.(body);
                }
                if (type === "end") {
                    sessionActive = false;
                    if (sessionJoined) {
                      session.leave();
                    }
                    session.onEnd?.(body);
                }
            });

        const notificationPromise = stompApi.subscribe(`/topic/auction/${auctionId}/notifications`, 
            (message) => {
                const { body } = message;
                session.onNotification?.(body);
            }); 

        const bidPromise = wasActive ? subscribeBid(auctionId, session) : null;
        const commentPromise = wasActive ? subscribeComment(auctionId, session) : null;
        
        await Promise.all([controlPromise, notificationPromise, bidPromise, commentPromise]);

        return session;
    } catch (error) {
        throw error;
    }
}
      
async function leaveAuctionRoom(auctionId) {
    stompApi.unsubscribe(`/topic/auction/${auctionId}/bids`);
    stompApi.unsubscribe(`/topic/auction/${auctionId}/comments`);
    stompApi.unsubscribe(`/topic/auction/${auctionId}/notifications`);
    stompApi.unsubscribe(`/topic/auction/${auctionId}/control`);
    await httpApi.post(`/v1/auctions/${auctionId}/leave`);
}

async function bid(auctionId, amount) {
    return stompApi.send(`/app/auction/${auctionId}/bid`, amount);
}

async function comment(auctionId, content) {
    return stompApi.send(`/app/auction/${auctionId}/comment`, content);
}

async function getCurrentPrice(auctionId) {
    return stompApi.send(`/app/auction/${auctionId}/last-price`);
}

async function getPastComments(auctionId, from) {
    const fromParam = from ? `?from=${from.toISOString()}` : '';
    return httpApi.get(`/app/auction/${auctionId}/comments${fromParam}`);
}

const auctionSessionApi = {
    config,
    joinAuctionRoom,
    leaveAuctionRoom,
    bid,
    comment,
    getCurrentPrice,
    getPastComments
}

export default auctionSessionApi;

function subscribeBid(auctionId, callbacks) {
    return stompApi.subscribe(`/topic/auction/${auctionId}/bids`, (message) => {
        const body = JSON.parse(message.body);
        body['created_at'] = parseDateTimeArray(body['created_at']);
        callbacks.onBid?.(body);
    });
}

function subscribeComment(auctionId, callbacks) {
    return stompApi.subscribe(`/topic/auction/${auctionId}/comments`, (message) => {
        const body = JSON.parse(message.body);
        body['created_at'] = parseDateTimeArray(body['created_at']);
        callbacks.onComment?.(body);
    });
}

function parseDateTimeArray(dateArray) {
    const [year, month, day, hour, minute, second] = dateArray;
    return new Date(year, month - 1, day, hour, minute, second);
}

stompApi.onControlMessage('joinable', (message) => {
    config.onJoinableAuction?.(message);
})

