<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10">
                <a-card hoverable class="h-auto bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Product Management</h1>
                    <div class="flex flex-col items-center justify-center mt-4 space-y-4">
                        <button class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md
                    hover:bg-teal-200 outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/accept.svg" alt="Add Session" class="w-6 h-6 mr-3" />
                            Accept Product
                        </button>
                        <button class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md
                    hover:bg-teal-200 outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/delete.svg" alt="Add Session" class="w-6 h-6 mr-3" />
                            Delete Product
                        </button>
                    </div>
                </a-card>
            </div>
            <div class="z-10">
                <a-card hoverable class="h-40 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Search</h1>
                    <div class="flex items-center justify-center mt-4">
                        <input type="text" v-model="searchKeyword" class="w-3/4 p-2 border border-gray-300 rounded-md"
                            placeholder="Enter product keyword...">
                        <button @click="searchProducts"
                            class="ml-2 p-2 bg-blue-50 hover:bg-teal-200 rounded-full outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
                        </button>
                    </div>
                </a-card>
            </div>
            <div class="z-10">
                <a-card hoverable class="h-50 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Category</h1>
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
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    Product Requests
                </h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                <a-table :columns="columns" :data-source="data" :row-key="record => record.id">
                    <template #bodyCell="{ column, record }">
                        <template v-if="column.key === 'actions'">
                            <span>
                                <a @click="editProduct(record.id)">Edit</a>
                                <a-divider type="vertical" />
                                <a @click="deleteProduct(record.id)">Delete</a>
                            </span>
                        </template>
                    </template>
                </a-table>
            </div>
        </div>
    </div>
    <TheChevron />
</template>

<script setup>
import { ref, reactive } from 'vue';
import TheChevron from '../../../components/Chevron/index.vue';

const columns = reactive([
    {
        title: 'Owner',
        dataIndex: 'owner',
        key: 'owner',
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Category',
        dataIndex: 'category',
        key: 'category',
    },
    {
        title: 'Description',
        dataIndex: 'description',
        key: 'description',
    },
    {
        title: 'Image',
        dataIndex: 'image',
        key: 'image',
        scopedSlots: { customRender: 'image' }
    },
    {
        title: 'Actions',
        key: 'actions',
    },
]);

const searchKeyword = ref('');
const tags = ref(['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other']);
const selectedTags = ref([]);

const filterByTag = (tag) => {
    if (selectedTags.value.includes(tag)) {
        selectedTags.value = selectedTags.value.filter(t => t !== tag);
    } else {
        selectedTags.value.push(tag);
    }
    console.log('Filtering products by tags:', selectedTags.value);
};

const searchProducts = () => {
    console.log('Searching for products with keyword:', searchKeyword.value);
};

const editProduct = (id) => {
    console.log('Editing product with ID:', id);
};

const deleteProduct = (id) => {
    console.log('Deleting product with ID:', id);
};

const data = ref([
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
    { owner: 'Jack', name: 'Fanart', category: 'Art', description: 'Ruoi phat sang', image: 'j5m.jpg', id: 1 },
]);

</script>