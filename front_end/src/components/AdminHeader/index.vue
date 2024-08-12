<template>
    <div class="w-full h-full">
        <div class="header fixed left-0 top-0 w-full z-50 bg-teal-200 h-20 flex items-center" @click="onCloseModal">
            <div class="w-1/10 flex sm:hidden items-center justify-center ml-4">
                <span @click="showDrawer">
                    <img src="../../assets/icon/dashboard.svg" alt="Dashboard" class="w-6 h-6" />
                </span>
            </div>
            <div class="w-8/10 flex">
                <img src="../../assets/images/logo.png" alt="Logo" class="mt-2 mr-40 ml-6 h-20 w-30">
                <div class="hidden sm:flex items-center justify-center space-x-2 ml-6">
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/admin/default" active-class="text-green-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">
                                <img src="../../assets/icon/home.svg" alt="Home" class="h-5 w-5 inline-block mr-2" />
                                Home
                            </div>
                        </router-link>
                    </ul>

                    <a-dropdown>
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-teal-600">
                            <router-link to="/admin/product" class="block w-full h-full hover:text-teal-600 rounded"
                                active-class="text-teal-600">
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
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-teal-600">
                            <router-link to="/admin/session" class="block w-full h-full hover:text-teal-600 rounded"
                                active-class="text-teal-600">
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
                        <a class="ant-dropdown-link font-bold cursor-pointer hover:text-teal-600">
                            <router-link to="/admin/news" class="block w-full h-full hover:text-teal-600 rounded"
                                active-class="text-teal-600">
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
                        <router-link to="/admin/history" class="block w-full h-full hover:text-teal-600 rounded"
                            active-class="text-teal-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">History</div>
                        </router-link>
                    </ul>
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/admin/introduction"
                            class="block w-full h-full p-5 hover:text-teal-600 rounded" active-class="text-teal-600">
                            <div class="w-full h-full flex items-center justify-center">Introduction</div>
                        </router-link>
                    </ul>
                    <ul class="navbar-item font-bold cursor-pointer">
                        <router-link to="/admin/contact" class="block w-full h-full hover:text-teal-600 rounded"
                            active-class="text-teal-600">
                            <div class="w-full h-full p-5 flex items-center justify-center">Contact</div>
                        </router-link>
                    </ul>
                </div>
            </div>

            <!-- avatar and dropdown-->
            <div class="w-1/10 flex items-center m-auto">
                <div ref="dropdownTrigger" @click="toggleDropdown"
                    class="relative flex items-center hover:cursor-pointer">
                    <a-avatar class="bg-teal-400">
                        <img src="../../assets/icon/user.svg" alt="User" class="w-6 h-6" />
                    </a-avatar>
                    <img src="../../assets/icon/chevron-down.svg" alt="Chevron Down" class="h-5 w-5" />
                </div>
                <div v-if="showDropdown"
                    class="absolute right-0 w-54 mt-36 mr-8 bg-white border border-gray-200 rounded shadow-lg z-20">
                    <a-menu>
                        <a-menu-item>
                            <a class="font-bold flex items-center" href="javascript:;">
                                <img src="../../assets/icon/profile.svg" alt="Profile"
                                    class="h-5 w-5 inline-block mr-2" />
                                Profile
                            </a>
                        </a-menu-item>
                        <a-menu-item @click="navigateToAllProduct">
                            <a class="font-bold flex items-center">
                                <img src="../../assets/icon/asset-management.svg" alt="Asset Management"
                                    class="h-5 w-5 inline-block mr-2" />
                                Auction Session Management
                            </a>
                        </a-menu-item>
                        <a-menu-item @click="navigateToAllProduct">
                            <a class="font-bold flex items-center">
                                <img src="../../assets/icon/asset-management.svg" alt="Asset Management"
                                    class="h-5 w-5 inline-block mr-2" />
                                Asset Management
                            </a>
                        </a-menu-item>
                        <a-menu-item>
                            <a class="font-bold" href="javascript:;">
                                <img src="../../assets/icon/setting.svg" alt="Setting"
                                    class="h-5 w-5 inline-block mr-2" />
                                Settings
                            </a>
                        </a-menu-item>
                        <a-menu-item>
                            <button @click="handleLogout" class="font-bold flex items-center">
                                <img src="../../assets/icon/logout.svg" alt="Logout"
                                    class="h-5 w-5 inline-block mr-2" />
                                Logout
                            </button>
                        </a-menu-item>
                    </a-menu>
                </div>
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
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../../stores/auths/useAuthStore';
import { message } from 'ant-design-vue';


const authStore = useAuthStore();
const router = useRouter();
const open = ref(false);
const showDropdown = ref(false);
const dropdownTrigger = ref(null);

const toggleDropdown = () => {
    showDropdown.value = !showDropdown.value;
};

const hideDropdown = () => {
    showDropdown.value = false;
};

const navigateToAllProduct = () => {
    router.push('/user/allProduct');
    hideDropdown();
};

const handleLogout = async () => {
    try {
        await authStore.logout('logout');
        message.success('You have successfully logout');
        router.push('/');
    } catch (error) {
        message.error('Logout. Please try again.');
    }
};

const handleClickOutside = (event) => {
    if (dropdownTrigger.value && !dropdownTrigger.value.contains(event.target)) {
        hideDropdown();
    }
};

onMounted(() => {
    document.addEventListener('click', handleClickOutside);
});

onBeforeUnmount(() => {
    document.removeEventListener('click', handleClickOutside);
});
</script>
