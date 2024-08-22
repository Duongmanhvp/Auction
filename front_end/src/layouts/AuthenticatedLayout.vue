<template>
  <div>
    <TheUserHeader />
    <div class="w-full h-full">
      <div class="p-4">
        <router-view></router-view>
      </div>
    </div>
    <Notification ref="notificationRef" />
    <!-- <button @click="showTestNotification" class="fixed bottom-4 right-4 px-4 py-2 bg-blue-500 text-white rounded">Show
      Notification</button> -->
  </div>
</template>

<script setup>
import { ref } from 'vue';
import TheUserHeader from '../components/UserHeader/index.vue';
import Notification from '../components/Notification/index.vue';

const notificationRef = ref(null);

// function showTestNotification() {
//   if (notificationRef.value) {
//     notificationRef.value.showNotification('This is a test notification!', 'success');
//   }
// }
</script>
