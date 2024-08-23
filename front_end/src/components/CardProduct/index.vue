<template>
  <div class="flex mx-5 space-x-5">
    <div class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
      <div class="mb-8">
        <div class="relative w-full max-w-md mx-auto">
          <h1 class="text-2xl font-bold text-center text-gray-800">
            {{ props.category }}
          </h1>
          <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
        </div>

        <div v-if="loading" class="flex items-center justify-center">
          <a-spin size="large" />
        </div>
        <div class="product-list grid grid-cols-4 gap-4">
          <div v-for="(product, index) in products.content" :key="index"
            class="product-item bg-white shadow-lg rounded-lg h-96">
            <a-card hoverable @click="selectProduct(product)"
              class="h-full transform hover:scale-105 transition duration-300 ease-in-out">
              <span
                class="absolute top-4 left-4 flex justify-center items-center w-auto text-black font-bold py-1 px-1 rounded">
                <img :src="product.isFavorite
                  ? HeartFilled
                  : Heart
                  " alt="Interested" class="w-4 h-4 mr-1" />
                {{ product.quantity }}
              </span>
              <template #cover>
                <img :src="`https://res.cloudinary.com/dorl0yxpe/image/upload/` +
                  product.image.split(', ')[0]
                  " alt="Product" class="w-56 h-56 object-cover" />
              </template>
              <a-card-meta :title="product.name" :description="product.description" class="h-20">
                <template #avatar>
                  <a-avatar :src="product.name" />
                </template>
              </a-card-meta>
              <button @click.stop="toggleFavorite(product)" :disabled="isFavorited(product)"
                class="flex items-center justify-center p-2 rounded mt-4 w-full "
                :class="{ 'bg-pink-200 hover:bg-pink-400': !isFavorited(product), 'bg-gray-200 cursor-not-allowed': isFavorited(product) }">
                <img src="../../assets/icon/like.svg" alt="Interested" class="w-6 h-6 mr-1" />
                Add to Favorites
              </button>
            </a-card>
          </div>
        </div>
        <div class="flex justify-center mt-4">
          <a-pagination v-model="currentPage" :total="products.totalElements" :pageSize="pageSize"
            @change="handlePageChange" />
        </div>
        <CardDetailModal :visible="viewModalVisible" :product="selectedProduct" @close="closeProductDetailModal" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch, reactive } from "vue";
import CardDetailModal from "../CardProductDetail/index.vue";
import productApi from "../../api/products";
import Heart from '../../assets/icon/heart.svg';
import HeartFilled from '../../assets/icon/heart-filled.svg';

const loading = ref(true);

const icons = ref([
  { src: Heart },
  { src: HeartFilled }
])

const props = defineProps({
  category: String,
  require: true,
});

//art
let products = reactive({
  totalElements: 0,
  content: [],
});

const currentPage = ref(1);
const pageSize = 4;
const selectedProduct = ref(null);
const viewModalVisible = ref(false);

const selectProduct = (product) => {
  selectedProduct.value = product;
  viewModalVisible.value = true;
};

const closeProductDetailModal = () => {
  viewModalVisible.value = false;
};

const handlePageChange = async (page) => {
  currentPage.value = page;
  const pageCurrent = page - 1;
  renderProduct(pageCurrent);
};

const renderProduct = async (pageCurrent) => {
  loading.value = true;
  try {
    const response = await productApi.getAllProductByCategory(
      props.category,
      pageCurrent
    );
    products.content = response.content;
    products.totalElements = response.totalElements;
    console.log(products);
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
};
onMounted(() => renderProduct(0));

const checkFavorite = async (product) => {
  try {
    const response = await productApi.checkFavorite(product.productId);
    product.isFavorite = response;
  } catch (error) {
    console.log(error.response.data.message);
  }
}

const isFavorited = (product) => {
  return product.isFavorite;
}

const toggleFavorite = async (product) => {
  //  product.isFavorite = !product.isFavorite;
  //  if (product.isFavorite) {
  //      product.quantity += 1;
  //      await productApi.interestProduct(product.productId);

  //    } else {
  //      product.quantity -= 1;
  //      // await productApi.UnInterestProduct(product.productId);
  //    }

  try {
    const response = await productApi.interestProduct(product.productId);
    product.isFavorite = !product.isFavorite;
    product.quantity += 1;
  } catch (error) {
    product.isFavorite = !product.isFavorite;
    console.log(error.response.data.message);
  }

};
</script>

<style lang="scss" src="./style.scss" scoped />
