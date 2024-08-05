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


<style scoped>
.min-h-screen {
    min-height: 100vh;
}

.bg-cover {
    background-size: cover;
}

.bg-center {
    background-position: center;
}

.bg-fixed {
    background-attachment: fixed;
}
</style>
