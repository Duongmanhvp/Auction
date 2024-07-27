<template>
    <div class="container mx-auto max-w-sm p-10">
        <img src="../../../assets/logo.png" alt="Logo" class="h-130 h-130 -mt-10 flex items-center justify-center">
        <router-link to="/home/default"
            class="flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <img src="../../../assets/icon/auth-back.svg" alt="Back" class="w-6 h-6" />
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Login</h1>

        <form @submit.prevent="submit">
            <div class="mb-4">
                <label for="username" class="block text-gray-700">Username / Email</label>
                <input type="text" id="username" v-model="credentials.email"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="password" class="block text-gray-700">Password</label>
                <input type="password" id="password" v-model="credentials.password"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="flex items-center mb-6">
                <input type="checkbox" id="rememberMe" v-model="credentials.rememberMe" />
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
            Do not have an account? <router-link to="/register" class="text-blue-600 underline ml-1">Register
                now</router-link>
        </p>
    </div>
</template>

<script>
import baseService from '../../../services/baseService';

export default {
    data() {
        return {
            credentials: {
                email: '',
                password: '',
                rememberMe: false,
            },
        };
    },
    methods: {
        submit() {
            baseService.post('/auths/authenticate', this.credentials)
                .then(response => {
                    localStorage.setItem('authToken', response.data.token);
                    this.$router.push('/home/users');
                });
        },
    },
};
</script>
