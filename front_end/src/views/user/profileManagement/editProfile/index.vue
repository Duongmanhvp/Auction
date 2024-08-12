<template>
    <div class="flex mt-8 mx-5 space-x-5">
        <div class="w-1/5 ml-4 mr-4">
            <Profile />
        </div>
        <div class="w-4/5 container border-l mx-auto p-10 mt-6">
            <div class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        Edit Profile
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <form @submit.prevent="submitProfile" class=" flex px-8 space-x-5">
                    <div class="flex-1">
                        <div class="mb-5">
                            <label class="block text-gray-700">Full Name</label>
                            <input type="text" v-model="profile.fullName"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Birthday</label>
                            <input type="date" v-model="profile.dateOfBirth"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Gender</label>
                            <select v-model="profile.gender"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2">
                                <option value="" disabled>Select gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                            </select>
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Email</label>
                            <input type="email" v-model="profile.email"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                    </div>
                    <div class="flex-1">
                        <div class="mb-5">
                            <label class="block text-gray-700">Address</label>
                            <input type="text" v-model="profile.address"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="flex-1">
                            <div class="mb-4">
                                <label class="block text-gray-700 mb-3">Avatar</label>
                                <input type="file" @change="handleAvatarUpload"
                                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                            </div>
                            <div v-if="imagePreview" class="mb-4">
                                <img :src="imagePreview" alt="Product Image"
                                    class="w-60 h-60 border border-gray-300 rounded-md" />
                            </div>
                        </div>
                        <div class="mb-5">
                            <label class="block text-gray-700">Phone</label>
                            <input type="text" v-model="profile.phone"
                                class="form-input w-full border border-gray-300 rounded-md px-2 py-2" />
                        </div>
                        <div class="flex justify-center items-center space-x-6">
                            <button type="submit"
                                class=" flex w-40 bg-teal-300 hover:bg-teal-400 outline-gray-400 shadow-lg text-black font-bold py-2 px-4 rounded">
                                <img src="../../../../assets/icon/send.svg" alt="Confirm" class="w-6 h-6 mr-4 ml-1" />
                                Confirm
                            </button>
                            <button type="submit"
                                class=" flex w-36 bg-slate-200 text-black hover:bg-slate-300 outline-gray-600 shadow-lg font-bold py-2 px-4 rounded">
                                <img src="../../../../assets/icon/cancel.svg" alt="Cancel" class="w-6 h-6 mr-4" />
                                Cancel
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <TheChevron />
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import TheChevron from '../../../../components/Chevron/index.vue';
import Profile from '../../../../components/Profile/index.vue';
import { useAuthStore } from '../../../../stores/auths/useAuthStore';

const profile = ref({
    fullName: '',
    dateOfBirth: '',
    gender: '',
    email: '',
    address: '',
    avatar: '',
    phone: ''
});

const authStore = useAuthStore();

const submitProfile = () => {
    console.log(profile.value);
};

const handleAvatarUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
        const formData = new FormData();
        formData.append('file', file);

        authStore.post('/products/upload-avatar', formData)
            .then(response => {
                imagePreview.value = URL.createObjectURL(file);
                product.value.image = response.data;
            })
            .catch(error => {
                console.error('Error uploading image:', error);
            });
    }
};
</script>