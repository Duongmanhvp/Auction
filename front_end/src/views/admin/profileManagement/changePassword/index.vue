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
        <div class="w-4/5 container border-l mx-auto p-10 mt-6">
            <div class="container border-l bg-white mx-auto p-10 rounded-md shadow-lg mt-6">
                <div class="relative w-full max-w-md mx-auto">
                    <h1 class="text-2xl font-bold text-center text-gray-800">
                        Change Password
                    </h1>
                    <div class="border-b-2 border-zinc-400 mt-2 mb-8"></div>
                </div>
                <div class="flex items-center justify-center text-center">
                    <form @submit.prevent="handleChangePassword">
                        <div class="mb-5 relative">
                            <label for="currentPassword" class="block text-gray-700 mb-2">Current Password</label>
                            <div class="relative">
                                <input :type="currentPasswordType" id="currentPassword" v-model="currentPassword"
                                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 pr-10 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                                <button type="button" @click="toggleCurrentPasswordVisibility"
                                    class="absolute inset-y-0 right-2 flex items-center">
                                    <img src="../../../../assets/icon/eye-hide.svg" alt="Toggle password visibility"
                                        class="w-4 h-4 cursor-pointer" />
                                </button>
                            </div>
                            <span v-if="validation.currentPassword" class="text-red-500">{{ validation.currentPassword
                                }}</span>
                        </div>

                        <div class="mb-4 relative">
                            <label for="newPassword" class="block text-gray-700 mb-2">New Password</label>
                            <div class="relative">
                                <input :type="newPasswordType" id="newPassword" v-model="newPassword"
                                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 pr-10 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                                <button type="button" @click="toggleNewPasswordVisibility"
                                    class="absolute inset-y-0 right-2 flex items-center">
                                    <img src="../../../../assets/icon/eye-hide.svg" alt="Toggle password visibility"
                                        class="w-4 h-4 cursor-pointer" />
                                </button>
                            </div>
                            <span v-if="validation.newPassword" class="text-red-500">{{ validation.newPassword }}</span>
                        </div>

                        <button type="submit"
                            class="btn btn-primary w-full bg-teal-400 hover:bg-green-500 text-white font-bold py-2 px-4 rounded">
                            Change Password
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const currentPassword = ref('');
const newPassword = ref('');
const validation = reactive({ currentPassword: null, newPassword: null });
const router = useRouter();
const store = useStore();

const isCurrentPasswordVisible = ref(false);
const isNewPasswordVisible = ref(false);
const currentPasswordType = computed(() => isCurrentPasswordVisible.value ? 'text' : 'password');
const newPasswordType = computed(() => isNewPasswordVisible.value ? 'text' : 'password');

function validatePassword(password) {
    return password.length >= 4;
}

const handleChangePassword = async () => {
    let isValid = true;

    if (!currentPassword.value) {
        validation.currentPassword = 'Current password is required.';
        isValid = false;
    } else {
        validation.currentPassword = null;
    }

    if (!newPassword.value) {
        validation.newPassword = 'New password is required.';
        isValid = false;
    } else if (!validatePassword(newPassword.value)) {
        validation.newPassword = 'New password must be at least 4 characters long.';
        isValid = false;
    } else {
        validation.newPassword = null;
    }

    if (!isValid) return;

    try {
        const response = await store.dispatch('changePassword',
            { oldPassword: currentPassword.value, newPassword: newPassword.value });
        router.push('/admin/profile');
    } catch (error) {
        console.log(error);
    }


}

function toggleCurrentPasswordVisibility() {
    isCurrentPasswordVisible.value = !isCurrentPasswordVisible.value;
}

function toggleNewPasswordVisibility() {
    isNewPasswordVisible.value = !isNewPasswordVisible.value;
}

</script>