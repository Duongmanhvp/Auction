<template>
    <div v-if="auction" class=" fixed left-0 top-24 container flex p-1 h-5/6">
        <button @click="goBack" class="absolute -top-16 right-96 z-50 text-gray-500 hover:text-gray-700 mt-20">
            <img src="../../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
        </button>
        <div class="w-1/3 bg-black">
            <div class="flex justify-center items-center h-screen relative">
                <img v-for="(image, index) in images" :key="index" :src="image.src" alt="Session"
                    v-show="index === currentImageIndex" class="max-w-full max-h-full object-contain" />
                <button @click="prevImage"
                    class="absolute left-2 top-1/2 transform -translate-y-1/2 bg-white bg-opacity-50 p-2 rounded-full">
                    <img src="../../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
                </button>
                <button @click="nextImage"
                    class="absolute right-2 top-1/2 transform -translate-y-1/2 bg-white bg-opacity-50 p-2 rounded-full">
                    <img src="../../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
                </button>
            </div>
        </div>
        <div class="flex w-2/3  p-4 bg-white">
            <div class="relative w-1/2">
                <h1 class="text-2xl font-bold text-gray-800 mb-4 min-h-[1.5em]">{{ auction.title }}</h1>
                <div class="border-b-2 border-gray-300 mb-4"></div>
                <div class="mb-4 min-h-[1.5em]">
                    <h2 v-if="sessionState === 'PENDING'" class="text-md font-semibold text-blue-400-600 mb-2">Auction start
                        after: {{ timeUntilStart }} </h2>
                    <h2 v-else-if="sessionState === 'IN_PROGRESS'" class="text-md font-semibold text-green-600 mb-2">Time
                        remaining: {{ timeLeft }}</h2>
                    <h2 v-else-if="sessionState === 'FINISHED'" class="text-md font-semibold text-red-600 mb-2">Auction has ended
                    </h2>
                </div>
                <div class="border-b-2 border-gray-300 mb-4"></div>
                <div class="mb-4">
                    <h2 class="text-xl font-semibold text-gray-700 mb-6">Session Details</h2>
                    <!-- <p class="text-gray-700 mb-2"><strong>Description:</strong> {{ auction.sessionDetail.description }}</p> -->
                    <p class="text-gray-700 mb-2"><strong>Starting Price:</strong> {{ formattedStartingBid }}</p>
                    <p class="text-gray-700 mb-2"><strong>Stepping Price:</strong> {{ formattedSteppingPrice }}</p>
                    <p class="text-gray-700 mb-2"><strong>Start Time:</strong> {{ auction.startTime ?? '?' }}</p>
                    <p class="text-gray-700 mb-2"><strong>End Time:</strong> {{ auction.endTime ?? '?' }}</p>
                    <div class="border-b-2 border-gray-300 my-8"></div>
                    <div class="absolute bottom-0">
                        <p :class="{ 'text-orange-500': isCurrentPriceYours, 'text-gray-700': !isCurrentPriceYours }"
                                class="text-gray-700 mb-2 text-xl">
                            <strong>Current Price:</strong> {{ formattedCurrentPrice }} VND
                        </p>
                        <div class="flex items-center mb-4 text-xl">
                            <span class="text-gray-700 mr-2"><strong>Your Price:</strong></span>
                            <input v-model="yourPriceInput" type="text" @input="adjustYourPrice" @keydown.enter="handlePlaceBid"
                                @keydown.up="increasePrice" @keydown.down="decreasePrice"
                                class="border p-2 rounded w-44 text-right font-mono" :step="steppingPrice" /> VND
                        </div>
                        <button @click="handlePlaceBid" :disabled="!biddable" :class="[biddable ? 'bg-green-500' : 'bg-gray-500']"
                            class="text-white p-2 rounded mt-4 w-full">
                            Place Bid
                        </button>
                    </div>
                    
                </div>
            </div>
            <div class="h-full w-px bg-gray-300 ml-4"></div>
            <div class="relative w-1/2">
                <div class="p-2">
                    <a-card v-for="(noti, index) in notifications" :key="index" hoverable
                        class="h-auto bg-white shadow-lg rounded-lg mb-2">
                        <template #actions>
                        </template>
                        <a-card-meta :title="index + 1" :description="noti.content"></a-card-meta>
                    </a-card>
                </div>
                <div class="">
                    <a-list item-layout="horizontal" :data-source="comments"
                        class="p-5 overflow-y-scroll max-h-96 custom-scrollbar">
                        <template #renderItem="{ item }">
                            <a-list-item :key="item.id">
                                <a-list-item-meta :description="item.content">
                                    <template #title>
                                        <a class="font-bold">{{ item.name }}</a>
                                    </template>
                                    <template #avatar>
                                        <a-avatar :src="item.avatar" />
                                    </template>
                                </a-list-item-meta>
                            </a-list-item>
                        </template>
                    </a-list>
                    <div class="absolute w-full bottom-[0] mt-4 flex rounded space-x-2">
                        <input v-model="myCommentInput" @keydown.enter="handleComment" type="text" placeholder="Enter your comment..."
                            class="flex-1 w-full ml-3 border p-2 rounded-lg" />
                        <button @click="handleComment" class="bg-green-300 text-white p-2 rounded-lg hover:bg-green-400">
                            <img src="../../../../assets/icon/send.svg" alt="Next" class="w-6 h-6" />
                        </button>
                    </div>
                </div>
                
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watchEffect, defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { differenceInMilliseconds, differenceInSeconds, formatDistance, parse, parseISO } from 'date-fns';
import { useStore } from 'vuex';
import { jwtDecode } from 'jwt-decode';
import stompApi from '../../../../api/stomp';
import sessionApi from '../../../../api/auctionSession';
import auctionApi from '../../../../api/auctions';
import productApi from '../../../../api/products';

const IMAGE_PREFIX = import.meta.env.VITE_IMAGE_PREFIX;

const store = useStore();

const route = useRoute();
const router = useRouter();

// const props = defineProps({
//     auctionId: {
//         type: Number,
//         required: true,
//     }
// });

// const { auctionId } = props;

const auctionId = route.params.id;
const userId = jwtDecode(localStorage.getItem('token')).id;
console.log(userId);

const auctionInfoRef = ref(null);
const auction = computed(() => auctionInfoRef.value || {});
const product = computed(() => auction.value.product || {});

const goBack = () => {
    router.back();
};

import image1 from '../../../../assets/images/image1.jpg';
import image2 from '../../../../assets/images/image2.jpg';
import image3 from '../../../../assets/images/image3.jpg';
import authApi from '../../../../api/auths';
const images = computed(() => {
    const raw = product.value?.image?.split(", ");
    console.log(raw);
    const img = raw?.map((src) => ({ 
        src: IMAGE_PREFIX + src
    })) || [];
    console.log(img);
    return img;
});

const currentImageIndex = ref(0);

function prevImage() {
    if (currentImageIndex.value > 0) {
        currentImageIndex.value--;
    } else {
        currentImageIndex.value = images.value.length - 1;
    }
};

function nextImage() {
    if (currentImageIndex.value < images.value.length - 1) {
        currentImageIndex.value++;
    } else {
        currentImageIndex.value = 0;
    }
};


const baseDate = new Date();
let diffseconds = 0;
const now = ref(baseDate);
const timeUntilStart = computed(() => {
    if (!auction.value.startTime) {
        return null;
    }
    const startTime = parse(auction.value.startTime, 'yyyy-MM-dd HH:mm:ss', new Date());
    return formatTimeLeft(now.value, startTime);
})
const timeLeft = computed(() => {
    if (!auction.value.endTime) {
        return null;
    }
    const endTime = parse(auction.value.endTime, 'yyyy-MM-dd HH:mm:ss', new Date());
    return formatTimeLeft(now.value, endTime);
})

function updateCountdown() {
    const theNow = new Date();
    const diff = Math.floor(differenceInSeconds(theNow, baseDate));
    if (diff > diffseconds) {
        diffseconds = diff;
        now.value = theNow;
    }
};

let countdownInterval = null;

function formatTimeLeft(from, to) {
    let inSeconds = differenceInSeconds(to, from);
    const negative = inSeconds < 0;
    inSeconds = Math.abs(inSeconds);
    const hours = Math.floor(inSeconds / 3600);
    const minutes = Math.floor(inSeconds / 60) % 60;
    const seconds = inSeconds % 60;
    return `${negative ? '-' : ''}${formatUnit(hours)}:${formatUnit(minutes)}:${formatUnit(seconds)}`;

    function formatUnit(unit) {
        return unit.toString().padStart(2, '0');
    }
}



const sessionState = ref(null);
const startingPrice = computed(() => auction.value.startBid);
const steppingPrice = computed(() => auction.value.pricePerStep);
const currentPrice = ref(null);
const isCurrentPriceYours = ref(false);
const yourPriceInput = ref('0');

const formattedSteppingPrice = computed(() => {
    return formatPrice(steppingPrice.value);
});

const formattedStartingBid = computed(() => {
    return formatPrice(startingPrice.value);
});

const formattedCurrentPrice = computed(() => {
    return formatPrice(currentPrice.value);
});
// const formattedYourPrice = computed(() => {
//     return formatPrice(yourPrice.value);
// });

const minimumPrice = computed(() => {
    return Math.max(startingPrice.value, 
        currentPrice.value ? currentPrice.value + steppingPrice.value : 0);
});

const biddable = computed(() => {
    const yourPrice = parsePrice(yourPriceInput.value);
    return sessionState.value === "IN_PROGRESS" && yourPrice >= minimumPrice.value;
});

function adjustYourPrice(event) {
    const newPrice = parsePrice(event.target.value);
    const oldCursorPos = event.target.selectionStart;
    const digitsToTheRight = event.target.value.substring(oldCursorPos).match(/\d/g)?.length || 0;

    const formatted = formatPrice(newPrice);
    let newCursorPos = formatted.length;
    let dgcount = 0;
    while (dgcount < digitsToTheRight && newCursorPos > 0) {
        if (formatted[newCursorPos - 1].match(/\d/)) {
            dgcount++;
        }
        newCursorPos--;
    }
    if (dgcount < digitsToTheRight) {
        newCursorPos = formatted.length;
    }
    yourPriceInput.value = formatted;
    setTimeout(() => {
        event.target.setSelectionRange(newCursorPos, newCursorPos);
    }, 0);
};

function increasePrice() {
    if (!auctionInfoRef.value) {
        return;
    }
    const yourPrice = parsePrice(yourPriceInput.value);
    const addedPrice = yourPrice + steppingPrice.value;

    const editedPrice = Math.max(minimumPrice.value, addedPrice);
    yourPriceInput.value = formatPrice(editedPrice);
}

function decreasePrice() {
    if (!auctionInfoRef.value) {
        return;
    }
    const yourPrice = parsePrice(yourPriceInput.value);
    const decreasedPrice = yourPrice - steppingPrice.value;
    
    const editedPrice = Math.max(minimumPrice.value, decreasedPrice);
    yourPriceInput.value = formatPrice(editedPrice);
}

function handlePlaceBid() {
    if (!biddable.value) {
      return;
    }
    const newPrice = parsePrice(yourPriceInput.value);
    sessionApi.bid(auction.value.id, newPrice);
};

function updateBid(bid) {
    currentPrice.value = bid.bid;
    isCurrentPriceYours.value = bid.userId === userId;
}

function parsePrice(priceStr) {
    return (parseInt(priceStr.replace(/\./g, '')) || 0);
};

function formatPrice(priceNum) {
    return priceNum == null ? "" :
        `${priceNum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')}`;
};



const comments = ref([]);

const myCommentInput = ref('');

function handleComment() {
    if (!myCommentInput.value) {
        return;
    }
    sessionApi.comment(auctionId, myCommentInput.value);
    myCommentInput.value = '';
};


const notifications = ref([]);


function handleUnload() {
    console.log('leaving room');
    sessionApi.leaveAuctionRoom(auctionId);
};

onMounted(() => {
    auctionApi.getAuctionById(auctionId)
    .then((res) => {
        console.log(res)
        auctionInfoRef.value = res;
        sessionState.value = 
            ["IN_PROGRESS", "FINISHED", "CANCELLED"].includes(res.status) ? res.status : "PENDING";
        if (sessionState.value === "IN_PROGRESS") {
            sessionApi.getCurrentPrice(auctionId).then((res) => {
                updateBid(res.data);
            });
        }
    })
    .catch((err) => {
        console.error(err);
    });


    const callbacks = {
        onStart: () => {
            sessionState.value = "IN_PROGRESS";
            console.log('auction started');
            sessionApi.getCurrentPrice(auctionId).then((res) => {
                updateBid(res.data);
            });
        },
        onEnd: () => {
            sessionState.value = "FINISHED";
            console.log('auction ended');
        },
        onBid: updateBid,
        onComment: (data) => {
            let { commentId, userId, content } = data;
            // TODO: get user name using api
            content = JSON.parse(content);
            comments.value.push({ content });
            const index = comments.value.length - 1;
            // const entry = comments.value[comments.value.length - 1];
            Promise.resolve(authApi.getAnotherInfo(userId)).then((user) => {
                comments.value[index] = { 
                    id: commentId, 
                    userId, 
                    name: user.fullName, 
                    content,
                    avatar: user.avatar
                };
            })
        },
        onNotification: (data) => {
            console.log('notification', data);
            notifications.value.push(data);
        },
    };

    const join = () => sessionApi.joinAuctionRoom(auctionId, callbacks)
    .catch((err) => {
        if (err.isAxiosError && err.response) {
            if (err.response.data?.message === 'da tham gia dau gia') {
                sessionApi.leaveAuctionRoom(auctionId).finally(() => {
                    setTimeout(join, 1000);
                });
            }
        }
        console.error(err);
    });
    join();

    countdownInterval = setInterval(updateCountdown, 100);
    window.addEventListener('beforeunload', handleUnload);
});

onUnmounted(() => {
    console.log('leaving room');
    clearInterval(countdownInterval);
    sessionApi.leaveAuctionRoom(auctionId);
    window.removeEventListener('beforeunload', handleUnload);
});




</script>

<style lang="scss" src="./style.scss" scoped />

<style lang="scss" src="./style.scss" scoped />
