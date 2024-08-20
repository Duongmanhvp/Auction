<template>
    <div v-if="auction" class=" fixed left-0 top-24 container flex p-1">
        <button @click="goBack" class="absolute -top-16 right-96 z-50 text-gray-500 hover:text-gray-700 mt-20">
            <img src="../../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
        </button>
        <div class="w-1/2 bg-black">
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
        <div class="flex w-1/2 p-4 bg-white max-h-screen">
            <div class="w-1/2">
                <h1 class="text-2xl font-bold text-gray-800 mb-4">{{ auction.title }}</h1>
                <div class="border-b-2 border-gray-300 mb-4"></div>
                <div class="mb-4">
                    <h2 class="text-md font-semibold text-green-600 mb-2">Auction start {{ formattedTimeUntilStart }}
                    </h2>
                </div>
                <div class="mb-4">
                    <h2 class="text-md font-semibold text-green-600 mb-2">Time remaining: {{ formattedTimeLeft }}</h2>
                </div>
                <div class="border-b-2 border-gray-300 mb-4"></div>
                <div class="mb-4">
                    <h2 class="text-xl font-semibold text-gray-700 mb-6">Session Details</h2>
                    <!-- <p class="text-gray-700 mb-2"><strong>Description:</strong> {{ auction.sessionDetail.description }}</p> -->
                    <p class="text-gray-700 mb-2"><strong>Start Bid:</strong> {{ formattedStartingBid }}</p>
                    <p class="text-gray-700 mb-2"><strong>Start Time:</strong> {{ auction.startTime ?? '?' }}</p>
                    <p class="text-gray-700 mb-2"><strong>End Time:</strong> {{ auction.endTime ?? '?' }}</p>
                    <div class="flex items-center">
                        <span class="text-gray-700 mr-2"><strong>Stepping Price:</strong></span>
                        <span class="text-gray-700 mr-2">{{ formattedSteppingPrice }}</span>
                    </div>
                    <div class="border-b-2 border-gray-300 my-8"></div>
                    <p :class="{'text-orange-500': isCurrentPriceYours, 'text-gray-700': !isCurrentPriceYours}" 
                            class="text-gray-700 mb-2 text-xl">
                        <strong>Current Price:</strong> {{ formattedCurrentPrice }}
                    </p>
                    <div class="flex items-center mb-4 text-xl">
                        <span class="text-gray-700 mr-2"><strong>Your Price:</strong></span>
                        <input v-model="yourPriceInput" @input="adjustYourPrice" @keydown.enter="handlePlaceBid" type="text"
                            class="border p-2 rounded w-44 mr-2" step="pricePerStep" />
                        VND
                    </div>
                    <button @click="handlePlaceBid" :disabled="!biddable" 
                            :class="[biddable ? 'bg-green-500' : 'bg-gray-500']" 
                            class="text-white p-2 rounded mt-4 w-full" >
                        Place Bid
                    </button>
                </div>
            </div>
            <div class="h-full w-px bg-gray-300 ml-4"></div>
            <div class="w-1/2">
                <div class="p-4">
                    <a-card hoverable class="h-auto bg-white shadow-lg rounded-lg mb-2">
                        <template #actions>
                        </template>
                        <a-card-meta title="Notification 1" description="This is the description"></a-card-meta>
                    </a-card>
                    <a-card hoverable class="h-auto bg-white shadow-lg rounded-lg mb-2">
                        <template #actions>
                        </template>
                        <a-card-meta title="Notification 2" description="This is the description"></a-card-meta>
                    </a-card>
                </div>
                <div></div>
                <a-list item-layout="horizontal" :data-source="data"
                    class="p-5 overflow-y-scroll max-h-96 custom-scrollbar">
                    <template #renderItem="{ item }">
                        <a-list-item>
                            <a-list-item-meta
                                description="Ant Design, a design language for background applications, is refined by Ant UED Team">
                                <template #title>
                                    <a class="font-bold" href="https://www.antdv.com/">{{ item.title }}</a>
                                </template>
                                <template #avatar>
                                    <a-avatar src="https://joeschmoe.io/api/v1/random" />
                                </template>
                            </a-list-item-meta>
                        </a-list-item>
                    </template>
                </a-list>
            </div>
        </div>
    </div>
    <div class="w-2/5 p-4 bg-gray-100 overflow-y-auto">
      <h1 class="text-2xl font-bold text-gray-800 mb-4">{{ auction.title }}</h1>
      <div class="border-b-2 border-gray-300 mb-4"></div>

      <div class="mb-4">
        <h2 class="text-md font-semibold text-gray-700 mb-2">
          Auction start {{ formattedTimeUntilStart }}
        </h2>
      </div>
      <div class="mb-4">
        <h2 class="text-md font-semibold text-gray-700 mb-2">
          Time remaining: {{ formattedTimeLeft }}
        </h2>
      </div>

      <div class="mb-4">
        <h2 class="text-xl font-semibold text-gray-700 mb-2">Details</h2>
        <p class="text-gray-600">{{ auction.description }}</p>
      </div>
      <div class="mb-4">
        <h2 class="text-xl font-semibold text-gray-700 mb-2">
          Session Details
        </h2>
        <p class="text-gray-700 mb-2">
          <strong>Description:</strong> {{ auction.description }}
        </p>
        <p class="text-gray-700 mb-2">
          <strong>Start Bid:</strong> {{ auction.startBid }}
        </p>
        <p class="text-gray-700 mb-2">
          <strong>Start Time:</strong> {{ auction.startTime }}
        </p>
        <p class="text-gray-700 mb-2">
          <strong>End Time:</strong> {{ auction.endTime }}
        </p>
        <div class="flex items-center mb-6">
          <span class="text-gray-700 mr-2"><strong>Price per Step:</strong></span>
          <span class="text-gray-700 mr-2">{{ auction.pricePerStep }}</span>
        </div>
        <p class="text-gray-700 mb-2">
          <strong>Current Price:</strong> {{ currentPrice }}
        </p>
        <div class="flex items-center mb-4">
          <span class="text-gray-700 mr-2"><strong> Increase Current Price:</strong></span>
          <button @click="increaseCurrentPrice" class="bg-gray-200 p-2 rounded w-10">
            +
          </button>
        </div>
        <button @click="bid" class="bg-green-500 text-white p-2 rounded mt-4 w-full">
          {{ auction.status }}
        </button>
      </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted, watchEffect, defineProps } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { formatDistance, parse, parseISO } from 'date-fns';
import { useStore } from 'vuex';
import { jwtDecode } from 'jwt-decode';
import stompApi from '../../../../api/stomp';
import sessionApi from '../../../../api/auctionSession';
import auctionApi from '../../../../api/auctions';
import productApi from '../../../../api/products';

const store = useStore();

const route = useRoute();
const router = useRouter();

const parsePrice = (priceStr) => {
  return parseInt(priceStr.replace("VND", "").trim());
};

const formatPrice = (priceNum) => {
  return `${priceNum} VND`;
};

const auctionId = route.params.id;
const userId = jwtDecode(localStorage.getItem('token')).id;
console.log(userId);

const auctionInfoRef = ref(null);
const auction = computed(() => auctionInfoRef.value || {});
const product = computed(() => auction.value.product || {});

const goBack = () => {
  router.back();
};


const currentImageIndex = ref(0);

const prevImage = () => {
  if (currentImageIndex.value > 0) {
    currentImageIndex.value--;
  } else {
    currentImageIndex.value = images.length - 1;
  }
};

const nextImage = () => {
  if (currentImageIndex.value < images.length - 1) {
    currentImageIndex.value++;
  } else {
    currentImageIndex.value = 0;
  }
};

const updateCountdown = () => {
  const now = new Date();
  const startTime = auction.startTime;
  const endTime = auction.endTime;

  if (now < startTime) {
    timeUntilStart.value = startTime - now;
    timeLeft.value = 0;
  } else if (now < endTime) {
    timeUntilStart.value = 0;
    timeLeft.value = endTime - now;
  } else {
    clearInterval(countdownInterval);
    timeUntilStart.value = 0;
    timeLeft.value = 0;
  }
};

const formattedTimeUntilStart = computed(() => {
  return timeUntilStart.value > 0
    ? formatDistance(new Date(timeUntilStart.value), new Date(0), {
      addSuffix: true,
    })
    : "now";
});

const formattedTimeLeft = computed(() => {
  return timeLeft.value > 0
    ? formatDistance(new Date(timeLeft.value), new Date(0), { addSuffix: true })
    : `Auction has not started`;
});




const ongoing = ref(false);
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

const biddable = computed(() => {
    console.log('biddable', ongoing.value, parsePrice(yourPriceInput.value), currentPrice.value + auction.value.pricePerStep);
    return ongoing.value && parsePrice(yourPriceInput.value) >= currentPrice.value + auction.value.pricePerStep;
});

function parsePrice(priceStr) {
    return parseInt(priceStr.replace(/\D/g, '')) || 0;
};

function formatPrice(priceNum) {
    return priceNum == null ? "" :
        `${priceNum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.')}`;
};

function adjustYourPrice() {
    const newPrice = parsePrice(yourPriceInput.value);
    yourPriceInput.value = formatPrice(newPrice);
};

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



const comments = ref([]);
const notifications = ref([]);

const data = [];

function handleUnload() {
    console.log('leaving room');
    sessionApi.leaveAuctionRoom(auctionId);
};

onMounted(() => {
  const auctionId = parseInt(route.params.id, 10);

  const auctions = store.getters.getAuction;

    sessionApi.joinAuctionRoom(auctionId, {
        onStart: () => {
            ongoing.value = true;
            console.log('auction started');
        },
        onEnd: () => {
            ongoing.value = false;
        },
        onBid: updateBid,
        onComment: (data) => {
            comments.value.push(data);
        },
        onNotification: (data) => {
            notifications.value.push(data);
        },
    }).catch((err) => {
        console.error(err);
    });


  updateCountdown();
  countdownInterval = setInterval(updateCountdown, 1000);
});

onUnmounted(() => {
  if (countdownInterval) {
    clearInterval(countdownInterval);
  }
});


</script>

<style lang="scss" src="./style.scss" scoped />
