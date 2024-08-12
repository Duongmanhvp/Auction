<template>
    <div class="flex mt-8 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <Profile />
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
                        <div class="ml-20 space-y-5">
                            <a-card-meta title="Full Name" description="This is the Full Name">
                            </a-card-meta>
                            <a-card-meta title="Birthday" description="This is the Birthday">
                            </a-card-meta>
                            <a-card-meta title="Gender" description="This is the Gender">
                            </a-card-meta>
                        </div>
                    </a-tab-pane>
                    <a-tab-pane key="2" tab="Contact Informations" force-render>
                        <div class="ml-20 space-y-5">
                            <a-card-meta title="Email" description="This is the Email">
                            </a-card-meta>
                            <a-card-meta title="Address" description="This is the Address">
                            </a-card-meta>
                            <a-card-meta title="Phone Number" description="This is the Phone number">
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
import Profile from '../../../../components/Profile/index.vue';
import { ref } from 'vue';

const activeKey = ref('1');
const showUploadModal = ref(false);
const avatarUrl = ref('');
const imagePreview = ref('');

const changeAvatar = () => {
    showUploadModal.value = true;
};

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
