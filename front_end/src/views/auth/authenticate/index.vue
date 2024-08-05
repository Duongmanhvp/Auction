<template>
    <div class="min-h-screen flex">
        <div class="w-1/2 bg-cover bg-center bg-fixed">
            <img src="../../../assets/image1.jpg" alt="Background" class="w-full h-full object-cover" />
        </div>
        <div class="w-1/2 flex items-center justify-center">
            <router-view />
        </div>
    </div>
</template>

<style lang="scss" scoped>
@import './style.scss';
</style>