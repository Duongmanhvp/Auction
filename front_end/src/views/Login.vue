<template>
    <div class="container mx-auto max-w-sm p-10">
        <img src="../assets/logo.png" alt="Logo" class="h-130 h-130 -mt-10 flex items-center justify-center">
        <router-link to="/" class="flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
            </svg>
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Login</h1>

        <form @submit.prevent="submit">
            <div class="mb-4">
                <label for="username" class="block text-gray-700">Username / Email</label>
                <input type="text" id="username" v-model="data.username"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="password" class="block text-gray-700">Password</label>
                <input type="password" id="password" v-model="data.password"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="flex items-center mb-6">
                <input type="checkbox" id="rememberMe" v-model="data.rememberMe" />
                <label for="rememberMe" class="text-gray-700 ml-2">Remember me?</label>
                <router-link to="/login/forgotPassword" class="text-sm text-blue-600 underline ml-14">Forgot
                    password?</router-link>
            </div>

            <button type="submit"
                class="btn btn-primary w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Login
            </button>
        </form>

        <p class="text-sm text-gray-600 mt-6">
            Do not have an account? <router-link to="/login/register" class="text-blue-600 underline ml-1">Register
                now</router-link>
        </p>
    </div>
</template>

<script setup>
import { reactive } from "vue";
import axios from 'axios';
import { useRouter } from "vue-router";

const data = reactive({
    username: '',
    password: '',
    rememberMe: false
});

const router = useRouter();

const submit = async () => {
    try {
        const response = await axios.post('http://localhost:8080/v1/users/login', data, { withCredentials: true });
        const token = response.data.token;
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
        await router.push('/');
    } catch (error) {
        console.error('Đăng nhập thất bại:', error);
    }
};
</script>
