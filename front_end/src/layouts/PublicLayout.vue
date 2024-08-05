<template>
    <TheHeader />
    <div class="w-full h-full bg-blue-50">
        <div class="p-4">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import TheHeader from '../components/Header/index.vue';

export default {
    components: {
        TheHeader
    }
}
</script>
