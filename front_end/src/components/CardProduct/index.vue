<template>
  <div class="flex mx-5 space-x-5">
    <div
      class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6"
    >
      <div class="mb-8">
        <div class="relative w-full max-w-md mx-auto">
          <h1 class="text-2xl font-bold text-center text-gray-800">
            {{ props.category }}
          </h1>
          <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
        </div>
        <div class="product-list grid grid-cols-4 gap-4">
          <div
            v-for="(product, index) in products.content"
            :key="index"
            class="product-item bg-white shadow-lg rounded-lg"
          >
            <a-card hoverable @click="selectProduct(product)" class="h-96">
              <span
                class="absolute top-4 left-4 flex justify-center items-center w-auto text-black font-bold py-1 px-1 rounded"
              >
                <img
                  :src="
                    product.isFavorite
                      ? HeartFilled
                      : Heart
                  "
                  alt="Interested"
                  class="w-4 h-4 mr-1"
                />
                {{ product.quantity }}
              </span>
              <template #cover>
                <img
                  :src="
                    `https://res.cloudinary.com/dorl0yxpe/image/upload/` +
                    product.image.split(', ')[0]
                  "
                  alt="Product"
                  class="w-56 h-56 object-cover"
                />
              </template>
              <a-card-meta
                :title="product.name"
                :description="product.description"
              >
                <template #avatar>
                  <a-avatar :src="product.name" />
                </template>
              </a-card-meta>
              <button
                @click.stop="toggleFavorite(product)"
                class="bg-pink-200 flex items-center justify-center p-2 rounded mt-4 w-full hover:bg-pink-300"
              >
                <img
                  src="../../assets/icon/like.svg"
                  alt="Interested"
                  class="w-6 h-6 mr-1"
                />
                {{
                  product.isFavorite
                    ? "Remove from Favorites"
                    : "Add to Favorites"
                }}
              </button>
            </a-card>
          </div>
        </div>
        <div class="flex justify-center mt-4">
          <a-pagination
            v-model="currentPage"
            :total="products.totalElements"
            :pageSize="pageSize"
            @change="handlePageChange"
          />
        </div>
        <CardDetailModal
          :visible="viewModalVisible"
          :product="selectedProduct"
          @close="closeProductDetailModal"
        />
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

const icons = ref([
  {src: Heart},
  {src: HeartFilled}
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
  const response = await productApi.getAllProductByCategory(
    props.category,
    pageCurrent
  );
  products.content = response.content;
  products.totalElements = response.totalElements;
  console.log(products);
};
onMounted(() => renderProduct(0));

const toggleFavorite = async(product) => {
  product.isFavorite = !product.isFavorite;
  if (product.isFavorite) {
      product.quantity += 1; 
      await productApi.interestProduct(product.productId);
      
    } else {
      product.quantity -= 1; 
      // await productApi.UnInterestProduct(product.productId);
    }
  
  

};
</script>

<style scoped>
:deep(.ant-card-meta-title) {
  white-space: normal;
  overflow-wrap: break-word;
  word-break: break-word;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

:deep(.ant-card-meta-description) {
  white-space: normal;
  overflow-wrap: break-word;
  word-break: break-word;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
