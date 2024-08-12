<template>
    <div class="flex mt-20 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <MenuProductManagement />
        </div>
        <div class="w-4/5 container border-l bg-white mx-auto p-10  rounded-md shadow-lg mt-6">
            <div class="relative w-full max-w-md mx-auto">
                <h1 class="text-2xl font-bold text-center text-gray-800">
                    Add Product
                </h1>
                <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
            </div>
            <form @submit.prevent="submitProduct" class="flex space-x-8">
                <div class="flex-1">
                    <div class="grid grid-cols-6 gap-2">
                        <div v-for="(image, index) in images" :key="index" class="relative group">
                            <img :src="image.url" alt="Product" class="w-full h-15 object-cover rounded-md" />
                            <button @click="removeImage(index)"
                                class="absolute top-0 right-0 bg-red-500 text-white rounded-md p-1 opacity-0 group-hover:opacity-100 transition-opacity">
                                &times;
                            </button>
                        </div>
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
                        <label for="category" class="block text-gray-700 mb-3">Category</label>
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
import MenuProductManagement from '../../../../components/MenuProductManagement/index.vue';
import { ref } from 'vue';

const product = ref({
    name: '',
    category: '',
    description: '',
    image: ''
});

const imagePreview = ref(null);

const categories = ['Art', 'License Plate', 'Vehicles', 'Antiques', 'Other'];

// const submitProduct = () => {
//     baseService.post('/products', product.value)
//         .then(() => {
//             alert('Product added successfully!');
//             product.value = {
//                 name: '',
//                 category: '',
//                 description: '',
//                 image: ''
//             };
//             imagePreview.value = null;
//         });
// };

const images = ref([]);

const handleImageUpload = (event) => {
    const files = event.target.files;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
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

const removeImage = (index) => {
    images.value.splice(index, 1);
};

</script>
