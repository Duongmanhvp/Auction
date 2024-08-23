<template>
  <div class="flex mt-20 mx-5 space-x-5">
    <div class="w-1/5 ml-4 mr-4">
      <MenuSessionManagement />
    </div>
    <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
      <div class="relative w-full max-w-md mx-auto">
        <h1 class="text-2xl font-bold text-center text-gray-800">
          All Sessions
        </h1>
        <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
      </div>

      <div v-if="loading" class="flex items-center justify-center">
        <a-spin size="large" />
      </div>
      <div class="session-list grid grid-cols-4 gap-4">
        <div v-for="session in paginatedSessions" :key="session.id"
          class="session-item bg-white shadow-lg rounded-lg cursor-pointer  transform hover:scale-105 transition duration-300 ease-in-out"
          @click="openModal(session)">

          <a-card hoverable>
            <template #cover>
              <img src="../../../../assets/images/auction.jpg" alt="Session" />
            </template>
            <a-card-meta :title="session.title" :description="session.status">
              <template #avatar>
                <a-avatar :src="session.avatar" />
              </template>
            </a-card-meta>
          </a-card>
        </div>
      </div>
      <button @click="prevSlide"
        class="absolute left-1/4 top-3/4 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
        <img src="../../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
      </button>
      <button @click="nextSlide"
        class="absolute right-12 top-3/4 transform -translate-y-1/2 bg-slate-300 bg-opacity-50 p-2 rounded-full">
        <img src="../../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
      </button>
      <div class="flex justify-center mt-4">
        <a-pagination v-model:current="currentPage" :total="totalSessions" :pageSize="pageSize * 2" />
      </div>
      <SessionModal :isVisible="isModalVisible" :session="selectedSession" @close="closeModal" />
    </div>
  </div>
</template>

<script setup>
import MenuSessionManagement from '../../../../components/MenuSessionManagement/index.vue';
import SessionModal from '../sessionDetail/index.vue';
import { ref, computed, reactive, onBeforeMount, watch } from 'vue';
import { useStore } from 'vuex';

const loading = ref(true);

const store = useStore();

const isModalVisible = ref(false);
const selectedSession = ref(null);
const sessions = ref([]);
// sessions = store.getters.getSessions;
//sessions.push(...store.state.sessions);


const currentPage = ref(1);
const pageSize = 4;
let totalSessions = sessions.value.length;

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

watch(sessions, () => {
  totalSessions = sessions.value.length;
});

onBeforeMount(async () => {
  loading.value = true;
  try {
    const res = await store.dispatch('getMyAuction');
    sessions.value = store.getters.getSessions;
  } catch (error) {
    message.error('Fetch failed');
  } finally {
    loading.value = false;
  }
});

</script>

<style lang="scss" src="./style.scss" scoped />
