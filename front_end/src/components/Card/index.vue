<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 mr-4">
            <div class="fixed top-1/4 w-1/5">
                <div class="z-10">
                    <a-card hoverable class="h-40 bg-white shadow-lg rounded-md">
                        <h1 class="text-lg font-bold">Search</h1>
                        <div class="flex items-center justify-center mt-4">
                            <input type="text" class="w-3/4 p-2 border border-gray-300 rounded-md"
                                placeholder="Enter product keyword...">
                            <button
                                class="ml-2 p-2 bg-blue-50 hover:bg-teal-200 rounded-full outline-gray-400 shadow-lg">
                                <img src="../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
                            </button>
                        </div>
                    </a-card>
                </div>
                <div class="z-10">
                    <a-card hoverable class="h-50 bg-white shadow-lg rounded-md mt-6">
                        <h1 class="text-lg font-bold">Filter</h1>
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
        </div>
        <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="mb-8">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        ART
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="product-list grid grid-cols-4 gap-4">
                    <div v-for="(product1, index1) in paginatedProducts1" :key="index1"
                        class="product-item bg-white shadow-lg rounded-lg">
                        <a-card hoverable @click="selectProduct1(product1)" class="h-72">
                            <span
                                class="absolute top-4 left-4 flex justify-center items-center w-auto bg-red-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                                <img src="../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                                100
                            </span>
                            <template #cover>
                                <img src="../../assets/images/product.jpg" alt="Product"
                                    class="w-44 h-44 object-cover" />
                            </template>
                            <a-card-meta :title="product1.name" :description="product1.category">
                                <template #avatar>
                                    <a-avatar :src="product1.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </div>
                <div class="flex justify-center mt-4">
                    <a-pagination v-model="currentPage1" :total="totalProducts1" :pageSize="pageSize1"
                        @change="handlePageChange1" />
                </div>
                <CardDetailModal :visible="viewModalVisible1" :product="selectedProduct1"
                    @close="closeProductDetailModal1" />
            </div>

            <div class="mb-8">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        LICENSE PLATE
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="product-list grid grid-cols-4 gap-4">
                    <div v-for="(product2, index2) in paginatedProducts2" :key="index2"
                        class="product-item bg-white shadow-lg rounded-lg">
                        <a-card hoverable @click="selectProduct2(product2)" class="h-72">
                            <span
                                class="absolute top-4 left-4 flex justify-center items-center w-auto bg-red-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                                <img src="../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                                100
                            </span>
                            <template #cover>
                                <img src="../../assets/images/product.jpg" alt="Product"
                                    class="w-44 h-44 object-cover" />
                            </template>
                            <a-card-meta :title="product2.title" :description="product2.category">
                                <template #avatar>
                                    <a-avatar :src="product2.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </div>
                <div class="flex justify-center mt-4">
                    <a-pagination v-model="currentPage2" :total="totalProducts2" :pageSize="pageSize2"
                        @change="handlePageChange2" />
                </div>
                <CardDetailModal :visible="viewModalVisible2" :product="selectedProduct2"
                    @close="closeProductDetailModal2" />
            </div>

            <div class="mb-8">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        VEHICLES
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="product-list grid grid-cols-4 gap-4">
                    <div v-for="(product3, index3) in paginatedProducts3" :key="index3"
                        class="product-item bg-white shadow-lg rounded-lg">
                        <a-card hoverable @click="selectProduct3(product3)" class="h-72">
                            <span
                                class="absolute top-4 left-4 flex justify-center items-center w-auto bg-red-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                                <img src="../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                                100
                            </span>
                            <template #cover>
                                <img src="../../assets/images/product.jpg" alt="Product"
                                    class="w-44 h-44 object-cover" />
                            </template>
                            <a-card-meta :title="product3.title" :description="product3.category">
                                <template #avatar>
                                    <a-avatar :src="product3.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </div>
                <div class="flex justify-center mt-4">
                    <a-pagination v-model="currentPage3" :total="totalProducts3" :pageSize="pageSize3"
                        @change="handlePageChange3" />
                </div>
                <CardDetailModal :visible="viewModalVisible3" :product="selectedProduct3"
                    @close="closeProductDetailModal3" />
            </div>

            <div class="mb-8">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        ANTIQUES
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="product-list grid grid-cols-4 gap-4">
                    <div v-for="(product4, index4) in paginatedProducts4" :key="index4"
                        class="product-item bg-white shadow-lg rounded-lg">
                        <a-card hoverable @click="selectProduct4(product4)" class="h-72">
                            <span
                                class="absolute top-4 left-4 flex justify-center items-center w-auto bg-red-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                                <img src="../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                                100
                            </span>
                            <template #cover>
                                <img src="../../assets/images/product.jpg" alt="Product"
                                    class="w-44 h-44 object-cover" />
                            </template>
                            <a-card-meta :title="product4.title" :description="product4.category">
                                <template #avatar>
                                    <a-avatar :src="product4.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </div>
                <div class="flex justify-center mt-4">
                    <a-pagination v-model="currentPage4" :total="totalProducts4" :pageSize="pageSize4"
                        @change="handlePageChange4" />
                </div>
                <CardDetailModal :visible="viewModalVisible4" :product="selectedProduct4"
                    @close="closeProductDetailModal4" />
            </div>

            <div class="mb-8">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        OTHER
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="product-list grid grid-cols-4 gap-4">
                    <div v-for="(product5, index5) in paginatedProducts5" :key="index5"
                        class="product-item bg-white shadow-lg rounded-lg">
                        <a-card hoverable @click="selectProduct5(product5)" class="h-72">
                            <span
                                class="absolute top-4 left-4 flex justify-center items-center w-auto bg-red-200 text-black outline-gray-600 shadow-lg font-bold py-1 px-1 rounded">
                                <img src="../../assets/icon/heart.svg" alt="Interested" class="w-4 h-4 mr-1" />
                                100
                            </span>
                            <template #cover>
                                <img src="../../assets/images/product.jpg" alt="Product"
                                    class="w-44 h-44 object-cover" />
                            </template>
                            <a-card-meta :title="product5.title" :description="product5.category">
                                <template #avatar>
                                    <a-avatar :src="product5.avatar" />
                                </template>
                            </a-card-meta>
                        </a-card>
                    </div>
                </div>
                <div class="flex justify-center mt-4">
                    <a-pagination v-model="currentPage5" :total="totalProducts5" :pageSize="pageSize5"
                        @change="handlePageChange5" />
                </div>
                <CardDetailModal :visible="viewModalVisible5" :product="selectedProduct5"
                    @close="closeProductDetailModal5" />
            </div>
        </div>


    </div>
</template>

<script setup>
import { ref, computed, reactive, onMounted } from 'vue';
import productApi from '../../api/products.js';
import CardDetailModal from '../CardDetail/index.vue';

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

//art
const products1 = ref([
    { title: 'Demo Product', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 2', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 3', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 4', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 5', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 6', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 7', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 8', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
    { title: 'Product 9', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Art' },
]);

const artProducts = reactive([]);

const fetchArtProducts = async () => {
    try {
        const response = await productApi.getProductsByCategory('ART');
        artProducts.length = 0;
        artProducts.push(...response.content);
        console.log(artProducts);
    } catch (error) {
        console.log('Failed to fetch art products:', error);
    }
    
};
onMounted(() => {
    fetchArtProducts();
});
const currentPage1 = ref(1);
const pageSize1 = 4;
const totalProducts1 = computed(() => artProducts.length);
const selectedProduct1 = ref(null);
const viewModalVisible1 = ref(false);

const selectProduct1 = (product1) => {
    selectedProduct1.value = product1;
    viewModalVisible1.value = true;
};

const closeProductDetailModal1 = () => {
    viewModalVisible1.value = false;
};

const paginatedProducts1 = computed(() => {
    const start1 = (currentPage1.value - 1) * pageSize1;
    const end1 = start1 + pageSize1;
    return artProducts.slice(start1, end1);
});

const handlePageChange1 = (page1) => {
    currentPage1.value = page1;
};


//license plate
const products2 = ref([
    { title: 'Demo Product', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 2', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 3', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 4', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 5', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 6', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 7', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 8', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
    { title: 'Product 9', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Licence Plate' },
]);

const currentPage2 = ref(1);
const pageSize2 = 4;
const totalProducts2 = computed(() => products2.value.length);
const selectedProduct2 = ref(null);
const viewModalVisible2 = ref(false);

const selectProduct2 = (product2) => {
    selectedProduct2.value = product2;
    viewModalVisible2.value = true;
};

const closeProductDetailModal2 = () => {
    viewModalVisible2.value = false;
};

const paginatedProducts2 = computed(() => {
    const start2 = (currentPage2.value - 1) * pageSize2;
    const end2 = start2 + pageSize2;
    return products2.value.slice(start2, end2);
});

const handlePageChange2 = (page2) => {
    currentPage2.value = page2;
};

//vehicle
const products3 = ref([
    { title: 'Demo Product', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 2', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 3', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 4', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 5', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 6', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 7', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 8', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
    { title: 'Product 9', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Vehicles' },
]);

const currentPage3 = ref(1);
const pageSize3 = 4;
const totalProducts3 = computed(() => products3.value.length);
const selectedProduct3 = ref(null);
const viewModalVisible3 = ref(false);

const selectProduct3 = (product3) => {
    selectedProduct3.value = product3;
    viewModalVisible3.value = true;
};

const closeProductDetailModal3 = () => {
    viewModalVisible3.value = false;
};

const paginatedProducts3 = computed(() => {
    const start3 = (currentPage3.value - 1) * pageSize3;
    const end3 = start3 + pageSize3;
    return products3.value.slice(start3, end3);
});

const handlePageChange3 = (page3) => {
    currentPage3.value = page3;
};

//antiques
const products4 = ref([
    { title: 'Demo Product', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 2', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 3', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 4', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 5', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 6', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 7', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 8', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
    { title: 'Product 9', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Antiques' },
]);

const currentPage4 = ref(1);
const pageSize4 = 4;
const totalProducts4 = computed(() => products4.value.length);
const selectedProduct4 = ref(null);
const viewModalVisible4 = ref(false);

const selectProduct4 = (product4) => {
    selectedProduct4.value = product4;
    viewModalVisible4.value = true;
};

const closeProductDetailModal4 = () => {
    viewModalVisible4.value = false;
};

const paginatedProducts4 = computed(() => {
    const start4 = (currentPage4.value - 1) * pageSize4;
    const end4 = start4 + pageSize4;
    return products4.value.slice(start4, end4);
});

const handlePageChange4 = (page4) => {
    currentPage4.value = page4;
};

//other
const products5 = ref([
    { title: 'Demo Product', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 2', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 3', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 4', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 5', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 6', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 7', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 8', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
    { title: 'Product 9', avatar: 'https://joeschmoe.io/api/v1/random', category: 'Other' },
]);

const currentPage5 = ref(1);
const pageSize5 = 4;
const totalProducts5 = computed(() => products5.value.length);
const selectedProduct5 = ref(null);
const viewModalVisible5 = ref(false);

const selectProduct5 = (product5) => {
    selectedProduct5.value = product5;
    viewModalVisible5.value = true;
};

const closeProductDetailModal5 = () => {
    viewModalVisible5.value = false;
};

const paginatedProducts5 = computed(() => {
    const start5 = (currentPage5.value - 1) * pageSize5;
    const end5 = start5 + pageSize5;
    return products5.value.slice(start5, end5);
});

const handlePageChange5 = (page5) => {
    currentPage5.value = page5;
};

</script>
