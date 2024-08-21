<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <MenuAuctionManagement />
        </div>
        <div class=" w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">All Auctions</h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
            </div>
            <div class="grid grid-cols-4 gap-4 p-4">
                <button @click="prevSlide"
                    class="absolute top-3/4 left-1/4 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                    <img src="../../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
                </button>
                <button @click="nextSlide"
                    class="absolute top-3/4 right-12 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                    <img src="../../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
                </button>
                <div v-for="session in paginatedSessions" :key="session.id"
                    class=" bg-white shadow-lg rounded-lg relative hover:cursor-pointer" @click="openModal(session)">
                    <a-card hoverable class="h-80">
                        <span
                            class="absolute top-4 left-4 flex justify-center items-center w-auto bg-sky-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                            <img src="../../../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                            100
                        </span>
                        <template #cover>
                            <img src="../../../../assets/images/product.jpg" alt="Auction" class="w-44 object-cover" />
                        </template>
                        <a-card-meta :title="session.title" :description="session.status">
                            <template #avatar>
                                <a-avatar :src="session.avatar" />
                            </template>
                            <template #title>
                                <h2 class="text-lg font-semibold">
                                    {{ auction.title }}
                                </h2>
                            </template>
                        </a-card-meta>
                    </a-card>
                </div>
            </div>
            <div class="flex justify-center mt-4">
                <a-pagination v-model="currentPage" :total="totalSessions" :pageSize="pageSize * 2" />
            </div>
        </div>
        <SessionModal :isVisible="isModalVisible" :session="selectedSession" @close="closeModal" />
    </div>
</template>

<script setup>
import MenuAuctionManagement from '../../../../components/MenuAuctionManagement/index.vue';
import SessionModal from '../auctionDetail/index.vue';
import { ref, computed } from 'vue';

const selectedSession = ref(null);
const isModalVisible = ref(false);

const sessions = ref([
    { id: 1, title: 'Demo SessionDemo SessionDemo SessionDemo SessionDemo Auction', avatar: 'https://joeschmoe.io/api/v1/random', status: 'Pending', image: 'https://joeschmoe.io/api/v1/random', description: 'Description here', startBid: '20.000.000VND', pricePerStep: '10.000.000VND', startTime: '2024-01-01', endTime: '2024-01-02' },
    { id: 2, title: 'Session 2', avatar: 'https://joeschmoe.io/api/v1/random', status: 'Pending' }
]);

const currentPage = ref(1);
const pageSize = 4;
const totalSessions = sessions.value.length;

const paginatedSessions = computed(() => {
    const start = (currentPage.value - 1) * pageSize * 2;
    const end = start + pageSize * 2;
    return sessions.value.slice(start, end);
});

const prevSlide = () => {
    if (currentPage.value > 1) {
        currentPage.value--;
    }
};

const nextSlide = () => {
    if (currentPage.value < Math.ceil(totalSessions / (pageSize * 2))) {
        currentPage.value++;
    }
};

const openModal = (session) => {
    selectedSession.value = session;
    isModalVisible.value = true;
};

const closeModal = () => {
    isModalVisible.value = false;
};
</script>

<style lang="scss" src="./style.scss" scoped />