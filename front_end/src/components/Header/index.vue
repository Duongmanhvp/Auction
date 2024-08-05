<template>
    <div class="w-full h-full">
        <div class="header fixed left-0 top-0 w-full z-50 bg-green-200 h-20 flex items-center">
            <div class="w-1/10 flex sm:hidden items-center justify-center ml-4">
                <span @click="showDrawer">
                    <img src="../../assets/icon/dashboard.svg" alt="Dashboard" class="w-6 h-6" />
                </span>
            </div>
            <div class="w-7/10 flex">
                <img src="../../assets/logo.png" alt="Logo" class="mt-2 mr-28 ml-6 h-20 w-30">
                <div class="hidden sm:flex items-center justify-center space-x-2 ml-6">
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/home/default" active-class="text-green-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">
                                <img src="../../assets/icon/home.svg" alt="Home" class="h-5 w-5 inline-block mr-2" />
                                Home
                            </div>
                        </router-link>
                    </ul>

                    <a-dropdown>
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-green-600">
                            <router-link to="/home/product" class="block w-full h-full hover:text-green-600 rounded"
                                active-class="text-green-600">
                                <div class="w-full h-full p-5 flex items-center justify-center">
                                    Auction Product
                                    <img src="../../assets/icon/chevron-down.svg" alt="Chevron Down" class="h-5 w-5" />
                                </div>
                            </router-link>
                        </a>
                        <template #overlay>
                            <a-menu>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">State property</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Real property</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Vehicle</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Collection - Art</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Brand stuff</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Exhibits were confiscated</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Other products</a>
                                </a-menu-item>
                            </a-menu>
                        </template>
                    </a-dropdown>
                    <a-dropdown>
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-green-600">
                            <router-link to="/home/session" class="block w-full h-full hover:text-green-600 rounded"
                                active-class="text-green-600">
                                <div class="w-full h-full p-5 flex items-center justify-center">
                                    Auction Session
                                    <img src="../../assets/icon/chevron-down.svg" alt="Chevron Down" class="h-5 w-5" />
                                </div>
                            </router-link>
                        </a>
                        <template #overlay>
                            <a-menu>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Upcoming auction</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">The auction is underway</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">The auction has ended</a>
                                </a-menu-item>
                            </a-menu>
                        </template>
                    </a-dropdown>
                    <a-dropdown>
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-green-600">
                            <router-link to="/home/news" class="block w-full h-full hover:text-green-600 rounded"
                                active-class="text-green-600">
                                <div class="w-full h-full p-5 flex items-center justify-center">
                                    News
                                    <img src="../../assets/icon/chevron-down.svg" alt="Chevron Down" class="h-5 w-5" />
                                </div>
                            </router-link>
                        </a>
                        <template #overlay>
                            <a-menu>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Notification</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Auction notice</a>
                                </a-menu-item>
                                <a-menu-item>
                                    <a class="font-bold" href="javascript:;">Other news</a>
                                </a-menu-item>
                            </a-menu>
                        </template>
                    </a-dropdown>

                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/home/history" class="block w-full h-full hover:text-green-600 rounded"
                            active-class="text-green-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">History</div>
                        </router-link>
                    </ul>
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/home/introduction"
                            class="block w-full h-full p-5 hover:text-green-600 rounded" active-class="text-green-600">
                            <div class="w-full h-full flex items-center justify-center">Introduction</div>
                        </router-link>
                    </ul>
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/home/contact" class="block w-full h-full hover:text-green-600 rounded"
                            active-class="text-green-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">Contact</div>
                        </router-link>
                    </ul>

                </div>
            </div>
            <div class="w-1/5 flex items-center justify-center space-x-4">
                <button @click="handleLogin"
                    class="bg-white hover:bg-green-600 text-black hover:text-white font-bold py-1 px-4 rounded shadow-lg outline outline-1 outline-green-600 focus:outline-none">
                    Login
                </button>
                <button @click="handleRegister"
                    class="hidden sm:block bg-white hover:bg-green-600 text-black hover:text-white font-bold py-1 px-4 rounded shadow-lg outline outline-1 outline-green-600 focus:outline-none">
                    Register
                </button>
            </div>
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
const handleLogin = () => {
    router.push('/login');
};
const handleRegister = () => {
    router.push('/register');
};

</script>
