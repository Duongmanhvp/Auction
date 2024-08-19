<template>
    <div v-show="isVisible" title="Auction Details" @click.self="closeModal"
        class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
        <div class="modal-content relative bg-white p-6 rounded-lg w-full max-w-screen-lg mx-4 mt-28">
            <button @click="closeModal" class="absolute top-4 right-4">
                <img src="../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
            </button>
            <div class="flex">
                <div class="w-1/2 flex items-center justify-center space-x-8 h-64 overflow-hidden rounded-md">
                    <img v-for="(image, index) in images" :key="index" :src="image.src" alt="Session"
                        v-show="index === currentImageIndex" class="h-max w-96" />
                    <button @click="prevImage"
                        class="absolute -left-2 top-1/4 transform -translate-y-1/2 bg-slate-400 bg-opacity-50 p-2 rounded-full">
                        <img src="../../../assets/icon/prev-arrow-slide.svg" alt="Previous" class="w-6 h-6" />
                    </button>
                    <button @click="nextImage"
                        class="absolute right-1/2 top-1/4 transform -translate-y-1/2 bg-slate-400 bg-opacity-50 p-2 mr-4 rounded-full">
                        <img src="../../../assets/icon/next-arrow-slide.svg" alt="Next" class="w-6 h-6" />
                    </button>
                </div>
                <div class="w-1/2 space-y-1 ml-8">
                    <p><strong>Owner:</strong> {{ auction.product?.owner }}</p>
                    <p><strong>Product Name:</strong> {{ auction.product?.name }}</p>
                    <p><strong>Category:</strong> {{ auction.product?.category }}</p>
                    <p><strong>Description:</strong> {{ auction.product?.description }}</p>
                    <p><strong>Start Bid:</strong> {{ auction.startBid }}</p>
                    <p><strong>Price Per Step:</strong> {{ auction.pricePerStep }}</p>
                    <br>
                    <p><strong>ID Auction:</strong> {{ auction.id }}</p>
                    <p><strong>Created At:</strong> {{ auction.createdAt }}</p>
                    <p><strong>Confirm Date:</strong> {{ auction.confirmDate }}</p>
                    <p><strong>End Registration:</strong> {{ auction.endRegistration }}</p>
                    <p><strong>Auction Name:</strong> {{ auction.title }}</p>
                    <p><strong>Description:</strong> {{ auction.description }}</p>
                    <p><strong>Start Time:</strong> {{ auction.startTime }}</p>
                    <p><strong>End Time:</strong> {{ auction.endTime }}</p>
                    <p><strong>Status: </strong>
                        <span v-if="auction.status !== 'OPENING'">{{ auction.status }}</span>
                        <select v-else v-model="auction.status" class="border border-gray-300 p-1 rounded-md">
                            <option value="OPENING">OPENING</option>
                            <option value="CLOSE">CLOSE</option>
                            <option value="IN PROGRESS">IN PROGRESS</option>
                            <option value="FINISH">FINISH</option>
                            <option value="CANCELED">CANCELED</option>
                        </select>
                    </p>
                </div>
            </div>
            <button v-if="auction.status === 'PENDING'" @click="acceptAuction"
                class="my-4 w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                <img src="../../../assets/icon/accept.svg" alt="Accept Auction" class="w-6 h-6 mr-3" />
                Accept Auction
            </button>
            <button v-if="auction.status !== 'PENDING'" @click="changeStatus"
                class="my-4 w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                <img src="../../../assets/icon/change-status.svg" alt="Change Status" class="w-6 h-6 mr-3" />
                Change Status
            </button>
            <button
                class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                <img src="../../../assets/icon/delete.svg" alt="Delete Auction" class="w-6 h-6 mr-3" />
                Delete Auction
            </button>
        </div>
    </div>
</template>

<script setup>
import { defineProps, defineEmits, ref } from 'vue';
import image1 from '../../../assets/images/image1.jpg';
import image2 from '../../../assets/images/image2.jpg';
import image3 from '../../../assets/images/image3.jpg';

const images = ref([
    { src: image1 },
    { src: image2 },
    { src: image3 },
]);

const props = defineProps({
    auction: Object,
    isVisible: Boolean
});

const emit = defineEmits(['close']);

const closeModal = () => {
    emit('close');
};

const acceptAuction = () => {
    props.auction.status = 'OPENING';
};

const changeStatus = () => {

};

const currentImageIndex = ref(0);

const prevImage = () => {
    if (currentImageIndex.value > 0) {
        currentImageIndex.value--;
    } else {
        currentImageIndex.value = images.value.length - 1;
    }
};

const nextImage = () => {
    if (currentImageIndex.value < images.value.length - 1) {
        currentImageIndex.value++;
    } else {
        currentImageIndex.value = 0;
    }
};

</script>

<style lang="scss" src="./style.scss" scoped />