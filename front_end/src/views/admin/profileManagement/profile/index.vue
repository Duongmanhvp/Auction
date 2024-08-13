<template>
    <div class="flex mt-8 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <div class="z-10 mt-24">
                <a-card hoverable class="h-auto bg-white shadow-lg rounded-md mt-6">
                    <h1 class="text-lg font-bold">Dashboard</h1>
                    <div class="flex flex-col items-center justify-center mt-4 space-y-4">
                        <router-link to="/admin/profile" class="w-full">
                            <button
                                class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                                <img src="../../../../assets/icon/overview.svg" alt="All Product" class="w-8 h-8 " />
                                Overview Profile
                            </button>
                        </router-link>
                        <router-link to="/admin/editProfile" class="w-full">
                            <button
                                class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md hover:bg-teal-200 outline-gray-400 shadow-lg">
                                <img src="../../../../assets/icon/pencil.svg" alt="All Product"
                                    class="w-5 h-5 mr-3 -ml-6" />
                                Edit Profile
                            </button>
                        </router-link>
                        <router-link to="/admin/changePassword" class="w-full">
                            <button class="w-full flex items-center justify-center p-2 bg-blue-50 text-black font-bold rounded-md
                    hover:bg-teal-200 outline-gray-400 shadow-lg">
                                <img src="../../../../assets/icon/change-password.svg" alt="Delete Product"
                                    class="w-6 h-6 mr-3" />
                                Change Password
                            </button>
                        </router-link>
                    </div>
                </a-card>
            </div>
        </div>

        <div class="w-4/5 container border-l mx-auto p-10 mt-12">
            <a-card hoverable class="w-full h-auto bg-white shadow-lg rounded-lg p-4">
                <div class="flex items-center justify-center">
                    <div class="relative inline-block group">
                        <img src="../../../../assets/images/j5m.jpg" alt="Avatar"
                            class="w-48 h-48 mr-4 ml-1 shadow-lg rounded-lg" />
                        <button @click="changeAvatar"
                            class="absolute -bottom-2 right-12 transform translate-x-1/2 -translate-y-1/2 bg-slate-400 bg-opacity-50 p-2 rounded-full opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                            <img src="../../../../assets/icon/change-avatar.svg" alt="Change Avatar" class="w-6 h-6" />
                        </button>
                    </div>
                </div>

                <div v-if="showUploadModal"
                    class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
                    <div class="bg-white p-6 rounded-lg z-50">
                        <h2 class="text-lg font-bold mb-4">Upload New Avatar</h2>
                        <input type="file" @change="handleFileChange" />
                        <div v-if="imagePreview" class="mt-4">
                            <img :src="imagePreview" alt="Image Preview" class="w-48 h-48 rounded-lg" />
                        </div>
                        <div class="mt-4 flex justify-end space-x-2">
                            <button @click="showUploadModal = false"
                                class="bg-gray-300 px-4 py-2 rounded">Cancel</button>
                            <button @click="confirmUpload"
                                class="bg-teal-500 text-white px-4 py-2 rounded">Confirm</button>
                        </div>
                    </div>
                </div>

                <a-tabs v-model:activeKey="activeKey" centered>
                    <a-tab-pane key="1" tab="Personal Informations">
                        <div class="text-xl ml-20 space-y-5">
                            <a-card-meta title="Full Name" :description="fullName">
                            </a-card-meta>
                            <a-card-meta title="Birthday" :description="birthday">
                            </a-card-meta>
                            <a-card-meta title="Gender" :description="gender">
                            </a-card-meta>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="Contact Informations" force-render>
                        <div class="text-xl ml-20 space-y-5">
                            <a-card-meta title="Email" :description="email">
                            </a-card-meta>
                            <a-card-meta title="Address" :description="address">
                            </a-card-meta>
                            <a-card-meta title="Phone Number" :description="phone">
                            </a-card-meta>
                        </div>
                    </a-tab-pane>
                </a-tabs>
            </a-card>

            <TheChevron />
        </div>
    </div>
</template>

<script setup>
import TheChevron from '../../../../components/Chevron/index.vue';
import { ref } from 'vue';
import { message } from 'ant-design-vue';

import { useStore } from "vuex";
const activeKey = ref('1');
const fullName = ref('');
const birthday = ref('');
const phone = ref('');
const email = ref('');
const address = ref('');
const gender = ref('');
const showUploadModal = ref(false);
const avatarUrl = ref('');
const imagePreview = ref('');

const store = useStore();

const user = store.getters.getUser;

const changeAvatar = () => {
    showUploadModal.value = true;
};

if (user) {
    fullName.value = user[0];
    birthday.value = user[1];
    email.value = user[2];
    phone.value = user[3];
    address.value = user[4];
    gender.value = user[5];
}

const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            imagePreview.value = e.target.result;
        };
        reader.readAsDataURL(file);
    }
};

const confirmUpload = () => {
    avatarUrl.value = imagePreview.value;
    showUploadModal.value = false;
};
</script>

<style scoped>
.fixed {
    z-index: 50;
}
</style>
