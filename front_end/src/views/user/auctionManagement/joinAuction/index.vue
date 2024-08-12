<template>
    <div v-if="auction" class="container flex h-screen mt-20">
        <div class="w-2/3 bg-black">
            <img src="../../../../assets/images/image1.jpg" class="w-full h-full" alt="Auction Image" />
        </div>
        <div class="w-1/3 p-4 bg-gray-100 overflow-y-auto">
            <h1 class="text-2xl font-bold text-gray-800 mb-4">{{ auction.title }}</h1>
            <div class="border-b-2 border-gray-300 mb-4"></div>
            <div class="mb-4">
                <h2 class="text-xl font-semibold text-gray-700 mb-2">Details</h2>
                <p class="text-gray-600">{{ auction.description }}</p>
            </div>
            <div class="mb-4">
                <h2 class="text-xl font-semibold text-gray-700 mb-2">Participants</h2>
                <ul>
                    <li v-for="participant in auction.participants" :key="participant.id"
                        class="py-2 border-b border-gray-300">
                        {{ participant.name }}
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const auction = ref(null);

onMounted(() => {
    const auctionId = parseInt(route.params.id, 10);

    const auctions = [
        {
            id: 1,
            title: 'Demo Auction',
            description: 'Details about the auction here.',

            participants: [
                { id: 1, name: 'Participant 1' },
                { id: 2, name: 'Participant 2' },
                { id: 3, name: 'Participant 3' }
            ]
        },
    ];

    auction.value = auctions.find(a => a.id === auctionId) || {};
});
</script>

<style scoped>
.container {
    display: flex;
    margin-top: 5rem;
}
</style>
