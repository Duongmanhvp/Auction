<template>
    <div class="min-h-screen flex">
        <div class="flex items-center justify-center">
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
