<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10">
                <a-card hoverable class="h-40 bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Search</h1>
                    <div class="flex items-center justify-center mt-4">
                        <input type="text" class="w-3/4 p-2 border border-gray-300 rounded-md"
                            placeholder="Enter product keyword...">
                        <button class="ml-2 p-2 bg-blue-50 hover:bg-teal-200 rounded-full outline-gray-400 shadow-lg">
                            <img src="../../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
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
        <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    All Products
                </h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
            </div>
            <div class="product-list grid grid-cols-4 gap-4">
                <button @click="prevSlide"
                    class="absolute left-1/4 top-3/4 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                    <img src="../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
                </button>
                <button @click="nextSlide"
                    class="absolute right-12 top-3/4 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
                    <img src="../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
                </button>
                <div v-for="(product, index) in paginatedProducts" :key="index"
                    class="product-item bg-white shadow-lg rounded-lg">
                    <a-card hoverable @click="selectProduct(product)">
                        <template #cover>
                            <img src="../../../assets/images/product.jpg" alt="Product" />
                        </template>
                        <a-card-meta :title="product.title" :description="product.category">
                            <template #avatar>
                                <a-avatar :src="product.avatar" />
                            </template>
                        </a-card-meta>
                    </a-card>
                </div>
            </div>
            <div class="flex justify-center mt-4">
                <a-pagination v-model:current="currentPage" :total="totalProducts" :pageSize="pageSize * 2" />
            </div>
        </div>

        <ProductDetailModal :visible="viewModalVisible" :product="selectedProduct" @close="closeProductDetailModal" />

    </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import ProductDetailModal from '../../user/productManagement/productDetail/index.vue';

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

const products = ref([
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

const currentPage = ref(1);
const pageSize = 4;
const totalProducts = products.value.length;
const selectedProduct = ref(null);
const viewModalVisible = ref(false);
const editModalVisible = ref(false);

const selectProduct = (product) => {
    selectedProduct.value = product;
    viewModalVisible.value = true;
};

const editProduct = (product) => {
    selectedProduct.value = product;
    editModalVisible.value = true;
};

const closeProductDetailModal = () => {
    viewModalVisible.value = false;
};

const closeEditProductModal = () => {
    editModalVisible.value = false;
};

const paginatedProducts = computed(() => {
    const start = (currentPage.value - 1) * pageSize * 2;
    const end = start + pageSize * 2;
    return products.value.slice(start, end);
});

const prevSlide = () => {
    if (currentPage.value > 1) {
        currentPage.value--;
    }
};
const nextSlide = () => {
    if (currentPage.value < Math.ceil(totalProducts / (pageSize * 2))) {
        currentPage.value++;
    }
};

</script>
