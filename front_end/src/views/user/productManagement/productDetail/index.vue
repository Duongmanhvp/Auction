<template>
   <div v-if="visible" class="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center">
       <div class="bg-white rounded-lg shadow-lg p-6 w-1/2 relative">
           <button @click="$emit('close')" class="absolute top-2 right-2 text-gray-500 hover:text-gray-700">
               <img src="../../../../assets/icon/cancel.svg" alt="Close" class="w-6 h-6" />
           </button>
           <h1 class="text-2xl font-bold text-center text-gray-800">{{ product.title }}</h1>
           <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
           <div class="flex space-x-8">
               <div class="flex-1">
                   <img src="../../../../assets/images/product.jpg" alt="Session" class="h-100 w-100" />
               </div>
               <div class="flex-1">
                   <div class="mb-5">
                       <label for="category" class="block text-gray-700 mb-3 text-lg font-bold">Category</label>
                       <p id="category" class="text-gray-800">{{ product.category }}</p>
                   </div>
                   <div class="mb-5">
                       <label for="description" class="block text-gray-700 mb-3 text-lg font-bold">Description</label>
                       <p id="description" class="text-gray-800">{{ product.description }}</p>
                   </div>
               </div>
           </div>
       </div>
   </div>
</template>

<script setup>
import { defineProps } from 'vue';

const props = defineProps({
   visible: {
       type: Boolean,
       required: true
   },
   product: {
       type: Object,
       required: true
   }
});
</script>
