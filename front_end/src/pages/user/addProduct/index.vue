<template>
    <div class="container bg-green-50 mx-auto p-8 mt-20">
        <div class="relative w-full max-w-md mx-auto mt-6">
            <h1 class="text-2xl font-bold text-center text-gray-800">
                Add Product
            </h1>
            <div class="border-b-2 border-blue-200 my-8"></div>
        </div>
        <form @submit.prevent="submitProduct" class="flex space-x-8">
            <div class="flex-1">
                <div class="mb-4">
                    <label class="block text-gray-700 mb-3">Upload Image</label>
                    <input type="file" @change="handleImageUpload"
                        class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                </div>
                <div v-if="imagePreview" class="mb-4">
                    <img :src="imagePreview" alt="Product Image"
                        class="w-full h-auto border border-gray-300 rounded-md" />
                </div>
            </div>

            <div class="flex-1">
                <div class="mb-5">
                    <label for="name" class="block text-gray-700 mb-3">Product Name</label>
                    <input type="text" id="name" v-model="product.name"
                        class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                </div>

                <div class="mb-5">
                    <label for="category" class="block text-gray-700 mb-3">Classification</label>
                    <select id="category" v-model="product.category"
                        class="form-select w-full border border-gray-300 rounded-md px-2 py-2">
                        <option value="" disabled>Select product type</option>
                        <option v-for="cat in categories" :key="cat" :value="cat">{{ cat }}</option>
                    </select>
                </div>

                <div class="mb-5">
                    <label for="description" class="block text-gray-700 mb-3">Description</label>
                    <textarea id="description" v-model="product.description"
                        class="form-textarea w-full border border-gray-300 rounded-md px-2 py-2"></textarea>
                </div>

                <button type="submit"
                    class="w-full bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded">
                    Confirm
                </button>
            </div>
        </form>
    </div>

    <TheChevron />
</template>

<script setup>
import TheChevron from '../../../components/Chevron/index.vue';
import { ref } from 'vue';
import baseService from '../../../services/baseService';

const product = ref({
    name: '',
    category: '',
    description: '',
    image: ''
});

const imagePreview = ref(null);

const categories = ['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other'];

const submitProduct = () => {
    baseService.post('/products', product.value)
        .then(() => {
            alert('Product added successfully!');
            product.value = {
                name: '',
                category: '',
                description: '',
                image: ''
            };
            imagePreview.value = null;
        });
};

const handleImageUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        baseService.post('/products/upload-image', formData)
            .then(response => {
                imagePreview.value = URL.createObjectURL(file);
                product.value.image = response.data;
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            });
    }
};
</script>

<style scoped>
/* Thêm các kiểu dáng tùy chỉnh nếu cần */
</style>
