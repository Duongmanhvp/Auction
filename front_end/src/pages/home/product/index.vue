<template>
    <div class="relative mt-20">
        <TheCard />
        <TheCard />
        <TheCard />
        <TheCard />
    </div>
</template>

<script>
import Home from '../../../layouts/home.vue';
import TheCard from '../../../components/Card/index.vue';

export default {
    components: {
        Home,
        TheCard
    }
}
</script>
