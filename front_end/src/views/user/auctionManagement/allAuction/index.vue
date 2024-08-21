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
              <img src="../../../../assets/icon/search.svg" alt="Search" class="w-5 h-5" />
            </button>
          </div>
        </a-card>
      </div>
      <div class="z-10">
        <a-card hoverable class="bg-white shadow-lg rounded-md mt-6">
          <h1 class="text-lg font-bold">Filter by Date</h1>
          <div class="flex flex-col items-center mt-2 space-y-4">
            <input type="date" v-model="startDate" class="w-full p-2 border border-gray-300 rounded-md"
              placeholder="Start Date" />
            <span>TO</span>
            <input type="date" v-model="endDate" class="w-full p-2 border border-gray-300 rounded-md"
              placeholder="End Date" />
            <button @click="filterByDate"
              class="flex items-center justify-center p-2 w-full bg-blue-50 font-bold hover:bg-teal-200 rounded-md outline-gray-400 shadow-lg">
              <img src="../../../../assets/icon/search.svg" alt="Search" class="w-5 h-5 mr-3" />
              Search
            </button>
          </div>
        </a-card>
      </div>
    </div>
    <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
      <div class="relative w-full max-w-md mx-auto">
        <h1 class="text-2xl font-bold text-center text-gray-800">
          All Auctions
        </h1>
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
        <div v-for="auction in paginatedAuctions" :key="auction.id"
          class="bg-white shadow-lg rounded-lg relative hover:cursor-pointer" @click="goToAuction(auction.id)">
          <a-card hoverable>
            <template #cover>
              <img class="h-56" :src="getUrlImage(auction.product.image)" alt="Auction_Image" />
            </template>
            <a-card-meta :title="auction.title" :description="auction.status">
              <template #avatar>
                <a-avatar :src=auction.product.avatarUrl alt="Auction" />
              </template>
            </a-card-meta>
          </a-card>
        </div>
      </div>
      <div class="flex justify-center mt-4">
        <a-pagination v-model:current="currentPage" :total="totalAuctions" :pageSize="pageSize * 2" />
      </div>
    </div>
  </div>
</template>

<script setup>
import MenuSessionManagement from '../../../../components/MenuSessionManagement/index.vue';
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const router = useRouter();
const store = useStore();

const auctions = store.getters.getAuction;

const currentPage = ref(1);
const pageSize = 4;
const totalAuctions = ref(auctions.length);


const paginatedAuctions = computed(() => {
  const start = (currentPage.value - 1) * pageSize * 2;
  const end = start + pageSize * 2;
  return auctions.slice(start, end);
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

const filterByDate = () => {
  if (!startDate.value || !endDate.value) {
    alert("Please select both start and end dates");
    return;
  }
};

const getUrlImage = (image) => {
  return `https://res.cloudinary.com/dorl0yxpe/image/upload/` + image.split(', ')[0];
}

const getUrlAvatar = async (ownerId) => {
  const response = await store.dispatch('getAnotherInfo', ownerId)
  console.log(response.avatar)
  return response.avatar;
}

const goToAuction = (auctionId) => {
  router.push({ name: 'joinAuction', params: { id: auctionId } });
};

const renderAuction = async () => {
  for (let auction of auctions) {
    const avatarUrl = await getUrlAvatar(auction.product.ownerId);
    auction.product.avatarUrl = avatarUrl;
  }
  console.log(auctions)
}
 onMounted(() => renderAuction())
</script>

<style scoped>
.auction-list {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 1rem;
}

.auction-item {
  position: relative;
}
</style>
