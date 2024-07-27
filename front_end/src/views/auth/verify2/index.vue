<template>
    <div class="container mx-auto max-w-sm p-0">
        <img src="../../../assets/logo.png" alt="Logo" class="h-56 h-56 ml-16 flex items-center justify-center">
        <router-link to="/login" class=" flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <img src="../../../assets/icon/auth-back.svg" alt="Back" class="w-6 h-6" />
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Verification</h1>
        <h2>
            Verification code is sent to your email. Please check and enter the verification code:
        </h2>

        <form @submit.prevent="onSubmit">
            <div class="flex justify-between mt-10 mb-10">
                <input type="text" id="code1" v-model="codes[0]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code2" v-model="codes[1]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code3" v-model="codes[2]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code4" v-model="codes[3]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code5" v-model="codes[4]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code6" v-model="codes[5]"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <router-link to="/login/verifyNotice" type="submit"
                class="flex items-center justify-center w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Next
            </router-link>
        </form>
    </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import baseService from '../../../services/baseService';

const codes = reactive(['', '', '', '', '', '']);
const router = useRouter();

const onSubmit = async () => {
    const otp = codes.join('');
    await baseService.post('/users/verify-otp', { otp });
    router.push('/reset-password');
};
</script>