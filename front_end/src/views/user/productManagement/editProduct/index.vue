<template>
    <div v-if="visible" class="fixed inset-0 flex items-center justify-center bg-gray-800 bg-opacity-50">
        <div class="bg-white rounded-lg shadow-lg p-6 w-full h-auto max-w-5xl relative">
            <button @click="$emit('close')" class="absolute top-2 right-2 text-gray-500 hover:text-gray-700">
                <img src="../../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
            </button>
            <h1 class="text-2xl font-bold mb-4">Edit Product</h1>
            <form @submit.prevent="submitProduct" class="flex space-x-8 mb-8">
                <div class="flex-1">
                    <div class="mb-4">
                        <label for="imageList" class="block text-gray-700 mb-3">Image List</label>
                        <ul>
                            <li v-for="(image, index) in images" :key="index" class="flex items-center space-x-2 mb-2">
                                <span>{{ image.file.name }}</span>
                                <button @click="viewImage(image.url)" class="bg-slate-300 text-white px-2 py-1 rounded">
                                    <img src="../../../../assets/icon/eye.svg" alt="Image view" class="w-6 h-6" />
                                </button>
                                <button @click="removeImage(index)" class="bg-slate-300 text-white px-2 py-1 rounded">
                                    <img src="../../../../assets/icon/delete2.svg" alt="Image delete" class="w-6 h-6" />
                                </button>
                            </li>
                        </ul>
                    </div>
                    <input type="file" @change="handleImageUpload" class="mt-4" multiple />
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
                </div>
            </form>
            <button type="submit"
                class="flex bg-teal-300 text-black font-bold hover:bg-green-400 py-2 px-4 rounded absolute right-10 bottom-6 ">
                <img src="../../../../assets/icon/send.svg" alt="Confirm" class="w-6 h-6 mr-2" />
                Save
            </button>
        </div>
    </div>

    <div v-if="currentImage" @click="closeImageModal"
        class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-70 z-50 cursor-pointer">
        <img :src="currentImage" alt="Image" class="max-w-full max-h-full">
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';

const props = defineProps({
    visible: Boolean,
    product: Object
});

const emit = defineEmits(['update:visible', 'product-updated']);

const categories = ['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other'];

const images = ref([]);
const currentImage = ref(null);

const handleImageUpload = (event) => {
    const files = event.target.files;
    for (const file of files) {
        const reader = new FileReader();
        reader.onload = (e) => {
            images.value.push({
                file: file,
                url: e.target.result
            });
        };
        reader.readAsDataURL(file);
    }
};

const viewImage = (url) => {
    currentImage.value = url;
};

const removeImage = (index) => {
    images.value.splice(index, 1);
};

const close = () => {
    emit('update:visible', false);
};

const closeImageModal = () => {
    currentImage.value = null;
};
</script>
