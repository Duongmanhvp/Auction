<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">All Auctions</h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
            </div>
            <div class="auction-list grid grid-cols-4 gap-4">
                <div v-for="auction in paginatedAuctions" :key="auction.id"
                    class="auction-item bg-white shadow-lg rounded-lg relative">
                    <a-card hoverable>
                        <template #cover>
                            <img src="../../../../assets/images/product.jpg" alt="Auction" />
                        </template>
                        <a-card-meta :title="auction.title" :description="auction.status">
                            <template #avatar>
                                <a-avatar :src="auction.avatar" />
                            </template>
                        </a-card-meta>
                    </a-card>
                    <button @click="goToAuction(auction.id)"
                        class="absolute top-1/2 left-1/2 transform -translate-x-1/2 bg-slate-200 text-black py-2 px-4 rounded-md outline-dashed outline-1 hover:bg-slate-300">
                        Join
                    </button>
                </div>
            </div>
            <button @click="prevSlide"
                class="absolute top-1/2 left-8 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                <img src="../../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
            </button>
            <button @click="nextSlide"
                class="absolute top-1/2 right-8 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                <img src="../../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
            </button>
            <div class="flex justify-center mt-4">
                <a-pagination v-model:current="currentPage" :total="totalAuctions" :pageSize="pageSize * 2" />
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const auctions = ref([
    { id: 1, title: 'Demo Auction', avatar: 'https://joeschmoe.io/api/v1/random', status: 'Pending', image: 'https://joeschmoe.io/api/v1/random', description: 'Description here', startBid: '20.000.000VND', pricePerStep: '10.000.000VND', startTime: '2024-01-01', endTime: '2024-01-02' },
    { id: 2, title: 'Auction 2', avatar: 'https://joeschmoe.io/api/v1/random', status: 'Pending' },
    { id: 3, title: 'Auction 3', avatar: 'https://joeschmoe.io/api/v1/random', status: 'Pending' }
]);

const currentPage = ref(1);
const pageSize = 4;
const totalAuctions = ref(auctions.value.length);

const paginatedAuctions = computed(() => {
    const start = (currentPage.value - 1) * pageSize * 2;
    const end = start + pageSize * 2;
    return auctions.value.slice(start, end);
});

const prevSlide = () => {
    if (currentPage.value > 1) {
        currentPage.value--;
    }
};

const nextSlide = () => {
    if (currentPage.value < Math.ceil(totalAuctions.value / (pageSize * 2))) {
        currentPage.value++;
    }
};

const goToAuction = (auctionId) => {
    router.push({ name: 'joinAuction', params: { id: auctionId } });
};
</script>

<style scoped>
.auction-list {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 1rem;
}

.auction-item {
    position: relative;
}
</style>
