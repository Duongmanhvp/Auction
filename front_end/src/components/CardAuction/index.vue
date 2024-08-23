<template>
  <div class="flex  mx-5 space-x-5">

    <div class=" container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
      <div class="mb-8">
        <div class="relative w-full max-w-md mx-auto">
          <h1 class="text-2xl font-bold text-center text-gray-800">
            {{ props.statusAuction }}
          </h1>
          <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
        </div>

        <div class="product-list grid grid-cols-4 gap-4">
          <div v-if="loading" class="flex items-center justify-center">
            <a-spin size="large" />
          </div>
          <div v-for="(auction, index1) in auctions.content" :key="index1"
            class="product-item bg-white shadow-lg rounded-lg">
            <a-card hoverable @click="selectAuction(auction)"
              class="h-96 transform hover:scale-105 transition duration-300 ease-in-out">

              <template #cover>
                <img :src="auction.productImage" alt="Auction" class="w-60 h-60 object-cover" />
              </template>
              <a-card-meta :title="auction.title" :description="auction.description">
                <template #avatar>
                  <a-avatar :src="auction.name" />
                </template>
              </a-card-meta>
            </a-card>
          </div>
        </div>
        <div class="flex justify-center mt-4">
          <a-pagination v-model="currentPage1" :total="auctions.totalElements" :pageSize="pageSize1"
            @change="handlePageChange1" />
        </div>
        <CardDetailModal :visible="viewModalVisible1" :auction="selectedAuction" @close="closeProductDetailModal1" />
      </div>


    </div>


  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch, reactive } from 'vue';
import CardDetailModal from '../CardAuctionDetail/index.vue';
import auctionApi from '../../api/auctions';
import productApi from '../../api/products';

const loading = ref(true);

const props = defineProps({
  statusAuction: String,
  require: true,
});


//art
let auctions = reactive(
  {
    totalElements: 0,
    content: [],
  }
)

const currentPage1 = ref(1);
const pageSize1 = 4;
const selectedAuction = ref(null);
const viewModalVisible1 = ref(false);

const selectAuction = (auction) => {
  selectedAuction.value = auction;
  viewModalVisible1.value = true;
  console.log(selectedAuction.value)
};

const closeProductDetailModal1 = () => {
  viewModalVisible1.value = false;
};

const handlePageChange1 = async (page1) => {
  currentPage1.value = page1;
  const pageCurrent = page1 - 1;
  renderAuction(pageCurrent);
};


const renderAuction = async (pageCurrent) => {
  loading.value = true;
  try {
    const response = await auctionApi.getAllAuctionByStatus(props.statusAuction, pageCurrent);
    auctions.content = response.content;
    auctions.totalElements = response.totalElements;
    console.log(response)
    for (let auction of auctions.content) {
      const product = await productApi.getProductById(auction.product_id);
      auction.product = product;
      auction.productImage = `https://res.cloudinary.com/dorl0yxpe/image/upload/` + product.image.split(', ')[0];
    }
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }

}
onMounted(() => renderAuction(0))

</script>

<style lang="scss" src="./style.scss" scoped />
