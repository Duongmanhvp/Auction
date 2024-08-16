<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10">
                <a-card hoverable class="h-40 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Search</h1>
                    <div class="flex items-center justify-center mt-4">
                        <input type="text" v-model="searchKeyword" class="w-3/4 p-2 border border-gray-300 rounded-md"
                            placeholder="Enter product keyword..." />
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
                <h1 class="text-2xl font-bold text-center text-gray-800">Product List</h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>

                <a-table :columns="filteredColumns" :data-source="filteredData" :row-key="record => record.id">
                    <template #bodyCell="{ column, record }">
                        <template v-if="column.key">
                            <span class="hover:cursor-pointer" @click="selectProduct(record)">
                                {{ record[column.dataIndex] }}
                            </span>
                        </template>
                    </template>
                </a-table>
            </div>
        </div>
    </div>

    <ProductDetail :product="selectedProduct" :isVisible="isModalVisible" @close="closeModal" />
</template>

<script setup>
import { ref, computed } from 'vue';
import ProductDetail from '../productDetail/index.vue';

const isModalVisible = ref(false);
const selectedProduct = ref({});

const selectProduct = (product) => {
    selectedProduct.value = product || {};
    isModalVisible.value = true;
};

const columns = [
    { title: 'ID', dataIndex: 'id', key: 'id' },
    { title: 'Owner', dataIndex: 'owner', key: 'owner' },
    { title: 'Name', dataIndex: 'name', key: 'name' },
    { title: 'Category', dataIndex: 'category', key: 'category' },
    { title: 'Description', dataIndex: 'description', key: 'description' },
    { title: 'Image', dataIndex: 'image', key: 'image', scopedSlots: { customRender: 'image' } }
];

const data = [
    { id: 1, owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg' },
    { id: 2, owner: 'Jack2', name: 'Fanart2', category: 'Art2', description: 'Ruoi phat sang', image: 'j5m.jpg' },
];

const searchKeyword = ref('');
const tags = ref(['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other']);
const selectedTags = ref([]);

const filteredData = computed(() => {
    return data.filter(product => {
        const matchesKeyword = product.name.toLowerCase().includes(searchKeyword.value.toLowerCase());
        const matchesTags = selectedTags.value.length === 0 || selectedTags.value.includes(product.category);
        return matchesKeyword && matchesTags;
    });
});

const filteredColumns = computed(() => {
    return columns.filter(col => col.visible === undefined);
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
