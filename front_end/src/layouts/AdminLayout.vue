<template>
    <TheAdminHeader />
    <div class="w-full h-full ">
        <div class="p-4">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
import TheAdminHeader from '../components/AdminHeader/index.vue';

export default {
    components: {
        TheAdminHeader
    }
}
</script>
