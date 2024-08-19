<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10">
                <a-card hoverable class="h-40 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Search</h1>
                    <div class="flex items-center justify-center mt-4">
                        <input type="text" v-model="searchKeyword" class="w-3/4 p-2 border border-gray-300 rounded-md"
                            placeholder="Enter session keyword..." />
                        <button class="ml-2 p-2 bg-blue-50 hover:bg-teal-200 rounded-full outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
                        </button>
                    </div>
                </a-card>
            </div>
            <div class="z-10">
                <a-card hoverable class="h-50 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Status</h1>
                    <div class="flex items-center justify-center flex-wrap mt-4">
                        <div v-for="tag in tags" :key="tag" class="mt-1 mx-1">
                            <button @click="filterByTag(tag)"
                                :class="['p-2 rounded-full shadow-lg', { 'bg-teal-200': selectedTags.includes(tag), 'bg-blue-50': !selectedTags.includes(tag) }]">
                                {{ tag }}
                            </button>
                        </div>
                    </div>
                </a-card>
            </div>
        </div>

        <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full">
                <h1 class="text-2xl font-bold text-center text-gray-800">Auction List</h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>

                <a-table :columns="filteredColumns" :data-source="filteredData" :row-key="record => record.id">
                    <template #bodyCell="{ column, record }">
                        <template v-if="column.key">
                            <span class="hover:cursor-pointer" @click="selectAuction(record)">
                                {{ record[column.dataIndex] }}
                            </span>
                        </template>
                    </template>
                </a-table>
            </div>
        </div>
    </div>

    <AuctionDetail :auction="selectedAuction" :isVisible="isModalVisible" @close="closeModal" />
</template>

<script setup>
import { ref, computed } from 'vue';
import AuctionDetail from '../auctionDetail/index.vue';

const isModalVisible = ref(false);
const selectedAuction = ref({});
const isDetailsVisible = ref(false);

const selectAuction = (auction) => {
    selectedAuction.value = auction || {};
    isModalVisible.value = true;
};

const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Title', dataIndex: 'title', key: 'title' },
    { title: 'Description', dataIndex: 'description', key: 'description' },
    { title: 'Start Time', dataIndex: 'startTime', key: 'startTime', visible: isDetailsVisible },
    { title: 'End Time', dataIndex: 'endTime', key: 'endTime', visible: isDetailsVisible },
    { title: 'Start Bid', dataIndex: 'startBid', key: 'startBid', visible: isDetailsVisible },
    { title: 'Price Per Step', dataIndex: 'pricePerStep', key: 'pricePerStep', visible: isDetailsVisible },
    { title: 'Status', dataIndex: 'status', key: 'status' },
    { title: 'Created At', dataIndex: 'createdAt', key: 'createdAt' },
    { title: 'Confirm Date', dataIndex: 'confirmDate', key: 'confirmDate', visible: isDetailsVisible },
    { title: 'End Registration', dataIndex: 'endRegistration', key: 'endRegistration', visible: isDetailsVisible },
    { title: 'Product Owner', dataIndex: ['product', 'owner'], key: 'productOwner', visible: isDetailsVisible },
    { title: 'Product Name', dataIndex: ['product', 'name'], key: 'productName', visible: isDetailsVisible },
    { title: 'Product Category', dataIndex: ['product', 'category'], key: 'productCategory', visible: isDetailsVisible },
    { title: 'Product Image', dataIndex: ['product', 'image'], key: 'productImage', visible: isDetailsVisible },
];

const data = [
    {
        id: 1,
        title: 'Auction of J5m',
        createdAt: '2024-08-01',
        confirmDate: '2024-08-03',
        endRegistration: '2024-08-10',
        description: 'J5m number one',
        startTime: '2024-08-15',
        endTime: '2024-08-21',
        startBid: '20000000VND',
        pricePerStep: '1000000VND',
        status: 'PENDING'
    },
    {
        id: 2,
        title: 'Auction of J5m',
        createdAt: '2024-08-01',
        confirmDate: '2024-08-03',
        endRegistration: '2024-08-10',
        description: 'J5m number two',
        startTime: '2024-08-15',
        endTime: '2024-08-21',
        startBid: '20000000VND',
        pricePerStep: '1000000VND',
        status: 'PENDING',
        product: {
            id: 1,
            owner: 'Jack',
            name: 'Fanart',
            category: 'Art',
            description: 'Ruoi phat sang',
            image: 'j5m.jpg',
        }
    },
];

const searchKeyword = ref('');
const tags = ['PENDING', 'OPENING', 'CLOSE', 'IN PROGRESS', 'FINISH', 'CANCELED'];
const selectedTags = ref([]);

const filteredData = computed(() => {
    return data.filter(auction => {
        const matchesKeyword = auction.title.toLowerCase().includes(searchKeyword.value.toLowerCase());
        const matchesTags = selectedTags.value.length === 0 || selectedTags.value.includes(auction.status);
        return matchesKeyword && matchesTags;
    });
});

const filteredColumns = computed(() => {
    return columns.filter(col => col.visible === undefined || col.visible === isDetailsVisible.value);
});

const filterByTag = (tag) => {
    if (selectedTags.value.includes(tag)) {
        selectedTags.value = selectedTags.value.filter(t => t !== tag);
    } else {
        selectedTags.value.push(tag);
    }
};

const closeModal = () => {
    isModalVisible.value = false;
};
</script>
