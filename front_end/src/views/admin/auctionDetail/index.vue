<template>
    <div v-show="isVisible" title="Auction Details" @click.self="closeModal"
        class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
        <div class="modal-content relative bg-white p-6 rounded-lg w-full max-w-screen-lg mx-4 mt-28">
            <button @click="closeModal" class="absolute top-4 right-4">
                <img src="../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
            </button>
            <div class="flex">
                <div class="w-1/2 space-y-2">
                    <p><strong>ID:</strong> {{ auction.auction_id }}</p>
                    <p><strong>Created At:</strong> {{ auction.created_at }}</p>
                    <p><strong>Confirm Date:</strong> {{ auction.confirm_date }}</p>
                    <p><strong>End Registration:</strong> {{ auction.end_registration }}</p>
                    <p><strong>Status:</strong> {{ auction.status }}</p>
                </div>
                <div class="w-1/2 space-y-2">
                    <p><strong>Title:</strong> {{ auction.title }}</p>
                    <p><strong>Description:</strong> {{ auction.description }}</p>
                    <p><strong>Start Time:</strong> {{ auction.start_time }}</p>
                    <p><strong>End Time:</strong> {{ auction.end_time }}</p>
                    <p><strong>Start Bid:</strong> {{ auction.start_bid }}</p>
                    <p><strong>Price Per Step:</strong> {{ auction.price_per_step }}</p>
                </div>
            </div>
            <!-- <div class="flex flex-col items-center justify-center mt-4 space-y-4">
                <button
                    class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                    <img src="../../../assets/icon/accept.svg" alt="Accept Auction" class="w-6 h-6 mr-3" />
                    Accept Auction
                </button>
                <button
                    class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                    <img src="../../../assets/icon/delete.svg" alt="Delete Auction" class="w-6 h-6 mr-3" />
                    Delete Auction
                </button>
            </div> -->
            <div class="flex flex-col items-center justify-center mt-4 space-y-4">
                <button @click="acceptAuction"
                    class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                    <img src="../../../assets/icon/accept.svg" alt="Accept Auction" class="w-6 h-6 mr-3" />
                    Accept Auction
                </button>
                <button
                    class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                    <img src="../../../assets/icon/delete.svg" alt="Delete Auction" class="w-6 h-6 mr-3" />
                    Delete Auction
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
    auction: Object,
    isVisible: Boolean
});

const emit = defineEmits(['close']);

const acceptAuction = () => {
    props.auction.status === 'OPENING';
};

const closeModal = () => {
    emit('close');
};

</script>

<style scoped>
.modal-content {
    max-height: 90vh;
    overflow-y: auto;
}
</style>
