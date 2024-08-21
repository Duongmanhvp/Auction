<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <MenuSessionManagement />
        </div>
        <div class="w-4/5 container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">Add Auction Session</h1>
                <div class="border-b-2 border-white mt-2 mb-8"></div>
            </div>
            <form @submit.prevent="submitAuction" class="flex space-x-8 ml-20">
                <div class="w-1/3">
                    <h1 class="text-xl font-bold text-gray-800 mb-4">Product Infor</h1>
                    <div class="flex mb-1 ">
                        <label for="productId" class="block text-gray-700 text-lg font-semibold mr-2">Product
                            ID:</label>
                        <p id="productId" class="text-gray-800 text-lg font-thin "> {{ product.id }}</p>
                    </div>
                    <div class="flex mb-1">
                        <label for="category" class="block text-gray-700 text-lg font-semibold mr-2">Product
                            Name:</label>
                        <p id="category" class="text-gray-800">{{ product.name }}</p>
                    </div>
                    <div class="flex mb-1">
                        <label for="category" class="block text-gray-700 text-lg font-semibold mr-2">Category:</label>
                        <p id="category" class="text-gray-800">{{ product.category }}</p>
                    </div>
                    <div class="flex mb-1">
                        <label for="productDescription"
                            class="block text-gray-700 text-lg font-semibold mr-2">Description:</label>
                        <p id="productDescription" class="text-gray-800">{{ product.description }}</p>
                    </div>
                    <!-- <div v-if="imagePreview" class="mb-4">
                        <img :src="imagePreview" alt="Product Image"
                            class="w-full h-auto border border-gray-300 rounded-md" />
                    </div> -->
                </div>

                <div class="w-2/3">
                    <div class="mb-5">
                        <label for="title" class="block text-gray-700 mb-3 font-semibold">Title</label>
                        <input type="text" id="title" v-model="auction.title"
                            class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                    </div>

                    <div class="mb-5">
                        <label for="description" class="block text-gray-700 mb-3 font-semibold">Description</label>
                        <textarea id="description" v-model="auction.description"
                            class="form-textarea w-full border border-gray-300 rounded-md px-2 py-2"></textarea>
                    </div>

                    <div class="mb-5">
                        <label for="startBid" class="block text-gray-700 mb-3 font-semibold">Start Bid</label>
                        <input type="number" id="startBid" v-model="auction.startBid"
                            class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                    </div>

                    <div class="mb-5">
                        <label for="pricePerStep" class="block text-gray-700 mb-3 font-semibold">Price per Step</label>
                        <input type="number" id="pricePerStep" v-model="auction.pricePerStep"
                            class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                    </div>

                    <button type="submit"
                        class="w-full bg-teal-400 hover:bg-teal-500 outline-gray-400 shadow-lg text-white font-bold py-2 px-4 rounded">
                        Confirm
                    </button>
                </div>
            </form>
        </div>
    </div>

    <TheChevron />
</template>

<script setup>
import TheChevron from '../../../../components/Chevron/index.vue';
import MenuSessionManagement from '../../../../components/MenuSessionManagement/index.vue';
import { ref } from 'vue';
import { useStore } from 'vuex'

const store = useStore();

const product = store.getters.getProductDetail;

const auction = ref({
    title: '',
    description: '',
    startBid: '',
    pricePerStep: '',
    startTime: '',
    endTime: '',
    product: {
        name: '',
        category: '',
        image: ''
    }
});

const imagePreview = ref(null);

const categories = ['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other'];

// const submitAuction = () => {
//     baseService.post('/auctions', auction.value)
//         .then(() => {
//             alert('Auction session added successfully!');
//             resetForm();
//         })
//         .catch(error => {
//             console.error('Error adding auction session:', error);
//         });
// };

// const handleImageUpload = (event) => {
//     const file = event.target.files[0];
//     if (file) {
//         const formData = new FormData();
//         formData.append('file', file);

//         baseService.post('/products/upload-image', formData)
//             .then(response => {
//                 imagePreview.value = URL.createObjectURL(file);
//                 auction.value.product.image = response.data;
//             })
//             .catch(error => {
//                 console.error('Error uploading image:', error);
//             });
//     }
// };

const resetForm = () => {
    auction.value = {
        title: '',
        description: '',
        startBid: '',
        pricePerStep: '',
        startTime: '',
        endTime: '',
        product: {
            name: '',
            category: '',
            image: ''
        }
    };
    imagePreview.value = null;
};
</script>
