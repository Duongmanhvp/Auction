<template>
  <router-view></router-view>
</template>

<script setup>
import { onMounted } from 'vue';
import stompApi from '../api/stompApi';

onMounted(() => {
    // TODO: change the way to check if user is logged in
    if (sessionStorage.getItem('token')) {
        stompApi.setup();
    }
})
</script>