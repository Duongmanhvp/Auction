<template>
    <div class="w-full h-full">
        <div class="flex items-center bg-[#b9efd3] h-20">
            <div class="w-1/12 flex sm:hidden items-center justify-center">
                <span @click="showDrawer"> <svg class="ml-5 h-6 w-6" fill="none" stroke="currentColor"
                        viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                            d="M4 6h16M4 12h16M4 18h16"></path>
                    </svg>
                </span>
            </div>
            <div class="w-10/12 flex">
                <img src="../assets/logo.png" alt="Logo" height="130" width="130">
                <nav class="flex space-x-20 ml-60 mt-10">
                    <a href="#" class="text-black hover:text-gray-200 font-bold">Tài sản đấu giá</a>
                    <a href="#" class="text-black hover:text-gray-200 font-bold">Phiên đấu giá</a>
                    <a href="#" class="text-black hover:text-gray-200 font-bold">Tin tức</a>
                    <a href="#" class="text-black hover:text-gray-200 font-bold">Giới thiệu</a>
                    <a href="#" class="text-black hover:text-gray-200 font-bold">Liên hệ</a>
                </nav>
            </div>

            <div class="flex items-center justify-center m-4">
                <button @click="handleLogin"
                    class=" bg-white hover:bg-slate-200 text-black font-bold py-1 px-4 rounded">
                    Login
                </button>
            </div>
        </div>
    </div>

    <div class="w-full flex items-center justify-center ">
        <img src="../assets/auction.jpg" alt="bg">
    </div>

    <div>
        <a-drawer v-model:open="open" title="Dashboard" placement="left">
            <p>Auction products ...</p>
            <p>Auction sessions ...</p>
            <p>News ...</p>
            <p>Introduction ...</p>
            <p>Contact ...</p>
        </a-drawer>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const open = ref(false);

const showDrawer = () => {
    open.value = true;
};
const onClose = () => {
    open.value = false;
};

const handleLogin = () => {
    router.push('/login');
};
</script>