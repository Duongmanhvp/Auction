<template>
   <div class="flex mt-20 mx-5 space-x-5">
       <div class=" container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
           <div class="absolute top-32 right-12 m-4">
               <button @click="goBack"
                   class="flex justify-center items-center w-12 bg-red-300 text-black hover:bg-red-400 outline-gray-600 shadow-lg font-bold py-2 rounded">
                   <img src="../../../../assets/icon/cancel.svg" alt="Cancel" class="w-6 h-6" />
               </button>
           </div>
           <div class="relative w-full max-w-md mx-auto">
               <h1 class="text-2xl font-bold text-center text-gray-800">Demo Session</h1>
               <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
           </div>

           <form @submit.prevent="submitAuction" class="space-x-8">
               <a-tabs v-model:activeKey="activeKey" centered>
                   <a-tab-pane key="1" tab="Introduction Session" class="flex">
                       <div class="flex-1">
                           <img src="../../../../assets/product.jpg" alt="Session" class="h-100 w-100" />
                       </div>
                       <div class="flex-1">
                           <div class="mb-5">
                               <label for="description"
                                   class="block text-gray-700 mb-3 text-lg font-bold">Title</label>
                               <p id="description" class="text-gray-800" v-html="auction.title"></p>
                           </div>
                           <div class="mb-5">
                               <label for="description"
                                   class="block text-gray-700 mb-3 text-lg font-bold">Description</label>
                               <p id="description" class="text-gray-800" v-html="auction.description"></p>
                           </div>
                       </div>
                   </a-tab-pane>
                   <a-tab-pane key="2" tab="Auction Informations" class="flex">
                       <div class="flex-1">
                           <img src="../../../../assets/product.jpg" alt="Session" class="h-100 w-100" />
                       </div>
                       <div class="flex-1">
                           <div class="mb-5">
                               <label for="description"
                                   class="block text-gray-700 mb-3 text-lg font-bold">Title</label>
                               <p id="description" class="text-gray-800" v-html="auction.title"></p>
                           </div>
                           <div class="mb-5">
                               <label for="startBid" class="block text-gray-700 mb-3 text-lg font-bold">Start
                                   Bid</label>
                               <p id="startBid" class="text-gray-800" v-html="auction.startBid"></p>
                           </div>
                           <div class="mb-5">
                               <label for="pricePerStep" class="block text-gray-700 mb-3 text-lg font-bold">Price
                                   per
                                   Step</label>
                               <p id="pricePerStep" class="text-gray-800" v-html="auction.pricePerStep"></p>
                           </div>
                           <div class="mb-5">
                               <label for="startTime" class="block text-gray-700 mb-3 text-lg font-bold">Start
                                   Time</label>
                               <p id="startTime" class="text-gray-800" v-html="auction.startTime"></p>
                           </div>
                           <div class="mb-5">
                               <label for="endTime" class="block text-gray-700 mb-3 text-lg font-bold">End
                                   Time</label>
                               <p id="endTime" class="text-gray-800" v-html="auction.endTime"></p>
                               <div />
                           </div>
                       </div>
                   </a-tab-pane>
               </a-tabs>
           </form>
       </div>
   </div>
   <TheChevron />
</template>

<script setup>
import TheChevron from '../../../../components/Chevron/index.vue';
import { ref } from 'vue';

const activeKey = ref('1');

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

// const submitAuction = () => {
//    baseService.post('/auctions', auction.value)
//        .then(() => {
//            alert('Auction session added successfully!');
//            resetForm();
//        })
//        .catch(error => {
//            console.error('Error adding auction session:', error);
//        });
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

const goBack = () => {
   window.history.back();
};
</script>

