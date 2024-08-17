<template>
    <div v-if="auction" class=" fixed left-0 top-24 container max-w-fit flex p-1">
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
                    <h2 class="text-md font-semibold text-gray-700 mb-2">Auction start {{ formattedTimeUntilStart }}
                    </h2>
                </div>
                <div class="mb-4">
                    <h2 class="text-md font-semibold text-gray-700 mb-2">Time remaining: {{ formattedTimeLeft }}</h2>
                </div>

                <div class="mb-4">
                    <h2 class="text-xl font-semibold text-gray-700 mb-2">Details</h2>
                    <p class="text-gray-600">{{ auction.description }}</p>
                </div>
                <div class="mb-4">
                    <h2 class="text-xl font-semibold text-gray-700 mb-2">Session Details</h2>
                    <p class="text-gray-700 mb-2"><strong>Description:</strong> {{ auction.sessionDetail.description }}
                    </p>
                    <p class="text-gray-700 mb-2"><strong>Start Bid:</strong> {{ auction.sessionDetail.startBid }}</p>
                    <p class="text-gray-700 mb-2"><strong>Start Time:</strong> {{ auction.sessionDetail.startTime }}</p>
                    <p class="text-gray-700 mb-2"><strong>End Time:</strong> {{ auction.sessionDetail.endTime }}</p>
                    <div class="flex items-center mb-6">
                        <span class="text-gray-700 mr-2"><strong>Price per Step:</strong></span>
                        <span class="text-gray-700 mr-2">{{ auction.sessionDetail.pricePerStep }}</span>
                    </div>
                    <p class="text-gray-700 mb-2"><strong>Current Price:</strong> {{ currentPrice }}</p>
                    <div class="flex items-center mb-4">
                        <span class="text-gray-700 mr-2"><strong>Increase Price:</strong></span>
                        <input v-model.number="increasePrice" @input="adjustIncreasePrice" type="number"
                            class="border p-2 rounded w-32 mr-2" step="pricePerStep" />
                        VND
                    </div>
                    <button @click="handleAction" class="bg-green-500 text-white p-2 rounded mt-4 w-full">
                        {{ auctionIsOpen ? 'Join Auction' : 'Bid' }}
                    </button>
                </div>
            </div>
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
                    class="p-5 overflow-y-scroll max-h-72 custom-scrollbar">
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
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { formatDistance, parseISO } from 'date-fns';
import image1 from '../../../../assets/images/image1.jpg';
import image2 from '../../../../assets/images/image2.jpg';
import image3 from '../../../../assets/images/image3.jpg';

const data = [
    {
        title: 'Elon Muc',
    },
    {
        title: 'Bin Ghet',
    },
    {
        title: 'Mac Zubot',
    },
    {
        title: 'Rach Mar',
    },
    {
        title: 'Donan Chum',
    },
];

const route = useRoute();
const router = useRouter();
const auction = ref(null);
const currentPrice = ref('');
const increasePrice = ref(0);
const timeUntilStart = ref(0);
const timeLeft = ref(0);
let countdownInterval = null;

const parsePrice = (priceStr) => {
    return parseInt(priceStr.replace('VND', '').trim());
};

const formatPrice = (priceNum) => {
    return `${priceNum} VND`;
};

const adjustIncreasePrice = () => {
    const pricePerStep = parsePrice(auction.value.sessionDetail.pricePerStep);
    increasePrice.value = Math.ceil(increasePrice.value / pricePerStep) * pricePerStep;
};

const goBack = () => {
    router.back();
};

const images = ref([
    { src: image1 },
    { src: image2 },
    { src: image3 },
]);

const currentImageIndex = ref(0);

const prevImage = () => {
    if (currentImageIndex.value > 0) {
        currentImageIndex.value--;
    } else {
        currentImageIndex.value = images.value.length - 1;
    }
};

const nextImage = () => {
    if (currentImageIndex.value < images.value.length - 1) {
        currentImageIndex.value++;
    } else {
        currentImageIndex.value = 0;
    }
};

const updateCountdown = () => {
    const now = new Date();
    const startTime = parseISO(auction.value.sessionDetail.startTime);
    const endTime = parseISO(auction.value.sessionDetail.endTime);

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
        ? formatDistance(new Date(timeUntilStart.value), new Date(0), { addSuffix: true })
        : 'now';
});

const formattedTimeLeft = computed(() => {
    return timeLeft.value > 0
        ? formatDistance(new Date(timeLeft.value), new Date(0), { addSuffix: true })
        : `Auction has not started`;
});

onMounted(() => {
    const auctionId = parseInt(route.params.id, 10);

    const auctions = [
        {
            id: 1,
            title: 'Demo Auction',
            description: 'Details about the auction here.',
            sessionDetail: {
                description: 'Session details description.',
                startBid: '20000000 VND',
                pricePerStep: '1000000 VND',
                startTime: '2024-08-15',
                endTime: '2024-08-21'
            },
        }
    ];

    auction.value = auctions.find(a => a.id === auctionId) || {};
    currentPrice.value = auction.value.sessionDetail.startBid;
    increasePrice.value = 0;

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
