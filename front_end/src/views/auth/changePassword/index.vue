<template>
    <div class="container mx-auto max-w-sm p-8">
        <img src="../../../assets/logo.png" alt="Logo" class="h-130 h-130 -mt-10 flex items-center justify-center">
        <router-link to="/home/default"
            class="flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <img src="../../../assets/icon/auth-back.svg" alt="Back" class="w-6 h-6" />
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Change Password</h1>

        <form @submit.prevent="handleChangePassword">
            <div class="mb-4 relative">
                <label for="currentPassword" class="block text-gray-700">Current Password</label>
                <div class="relative">
                    <input :type="currentPasswordType" id="currentPassword" v-model="currentPassword"
                        class="form-input w-full border border-gray-300 rounded-md px-2 py-2 pr-10 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                    <button type="button" @click="toggleCurrentPasswordVisibility"
                        class="absolute inset-y-0 right-2 flex items-center">
                        <img src="../../../assets/icon/eye-hide.svg" alt="Toggle password visibility"
                            class="w-4 h-4 cursor-pointer" />
                    </button>
                </div>
                <span v-if="validation.currentPassword" class="text-red-500">{{ validation.currentPassword }}</span>
            </div>

            <div class="mb-4 relative">
                <label for="newPassword" class="block text-gray-700">New Password</label>
                <div class="relative">
                    <input :type="newPasswordType" id="newPassword" v-model="newPassword"
                        class="form-input w-full border border-gray-300 rounded-md px-2 py-2 pr-10 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                    <button type="button" @click="toggleNewPasswordVisibility"
                        class="absolute inset-y-0 right-2 flex items-center">
                        <img src="../../../assets/icon/eye-hide.svg" alt="Toggle password visibility"
                            class="w-4 h-4 cursor-pointer" />
                    </button>
                </div>
                <span v-if="validation.newPassword" class="text-red-500">{{ validation.newPassword }}</span>
            </div>

            <button type="submit" :disabled="loading"
                class="btn btn-primary w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Change Password
            </button>
            <div v-if="loading">Loading...</div>
        </form>
    </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '../../../stores/auth/auth-store'

const currentPassword = ref('');
const newPassword = ref('');
const validation = reactive({ currentPassword: null, newPassword: null });
const router = useRouter();
const authStore = useAuthStore();

const loading = computed(() => authStore.loading);
const isCurrentPasswordVisible = ref(false);
const isNewPasswordVisible = ref(false);
const currentPasswordType = computed(() => isCurrentPasswordVisible.value ? 'text' : 'password');
const newPasswordType = computed(() => isNewPasswordVisible.value ? 'text' : 'password');

function validatePassword(password) {
    return password.length >= 4;
}

function handleChangePassword() {
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


    authStore.changePassword(currentPassword.value, newPassword.value)
        .then(() => {
            router.push('/home/default');
        })
        .catch(error => {
            console.error('Error changing password:', error);
        });
}

function toggleCurrentPasswordVisibility() {
    isCurrentPasswordVisible.value = !isCurrentPasswordVisible.value;
}

function toggleNewPasswordVisibility() {
    isNewPasswordVisible.value = !isNewPasswordVisible.value;
}
</script>
