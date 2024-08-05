<template>
    <TheUserHeader />
    <div class="container mx-auto bg-blue-50">
        <div class="flex">
            <div class="w-1/5 p-4">
                <TheMenu />
            </div>
            <div class="w-4/5 p-4">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
import TheUserHeader from '../components/UserHeader/index.vue';
import TheMenu from '../components/Menu/index.vue';

export default {
    components: {
        TheUserHeader,
        TheMenu
    }
}
</script>
