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
            <div class="session-list grid grid-cols-4 gap-4">
                <div v-for="session in paginatedSessions" :key="session.id"
                    class="session-item bg-white shadow-lg rounded-lg cursor-pointer"
                    @click="goToSessionDetail(session.id)">

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
        </div>
    </div>
</template>

<script setup>
import MenuSessionManagement from '../../../../components/MenuSessionManagement/index.vue';
import { ref, computed,reactive } from 'vue';
import { useStore } from 'vuex';


const store = useStore();
// const sessions = ref([
//     { id: 1, title: 'Demo Session', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 2', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 3', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 4', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 5', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 6', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 7', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 8', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
//     { title: 'Session 9', avatar: 'https://joeschmoe.io/api/v1/random', status: "Pending" },
// ]);


let sessions = reactive([]);
sessions = store.getters.getSessions;
//sessions.push(...store.state.sessions);


const currentPage = ref(1);
const pageSize = 4;
const totalSessions = sessions.length;

const paginatedSessions = computed(() => {
    const start = (currentPage.value - 1) * pageSize * 2;
    const end = start + pageSize * 2;
    return sessions.slice(start, end);
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

const goToSessionDetail = (id) => {
    router.push({ name: 'SessionDetail', params: { id } });
};

</script>

<style lang="scss" src="./style.scss" scoped />
