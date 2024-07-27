<template>
    <div class="container mx-auto max-w-sm p-auto">
        <div class="flex">
            <router-link to="/login" class="flex items-center space-x-2 text-gray-600 hover:text-gray-900">
                <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
                </svg>
                <span>Back</span>
            </router-link>
            <div class="flex justify-center ml-24">
                <h1 class="flex items-center justify-center text-2xl font-bold -ml-5 mb-4">Register</h1>
                <img src="../../../assets/logo.png" alt="Logo" class="h-24 h-24 flex items-center justify-center">
            </div>
        </div>


        <form @submit.prevent="submit">
            <div class="mb-4">
                <label for="email" class="block text-gray-700">Email</label>
                <input type="email" id="email" v-model="data.email"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="password" class="block text-gray-700">Password</label>
                <input type="password" id="password" v-model="data.password"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="fullName" class="block text-gray-700">Full Name</label>
                <input type="text" id="fullName" v-model="data.fullName"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="dateOfBirth" class="block text-gray-700">Date of Birth</label>
                <input type="date" id="dateOfBirth" v-model="data.dateOfBirth"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="gender" class="block text-gray-700">Gender</label>
                <select id="gender" v-model="data.gender"
                    class="form-select w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600">
                    <option value="true">Male</option>
                    <option value="false">Female</option>
                </select>
            </div>

            <div class="mb-4">
                <label for="phone" class="block text-gray-700">Phone</label>
                <input type="text" id="phone" v-model="data.phone"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <div class="mb-4">
                <label for="address" class="block text-gray-700">Address</label>
                <input type="text" id="address" v-model="data.address"
                    class="form-input w-full border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <router-link to="/login/verify2" type="submit"
                class="flex items-center justify-center w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Register
            </router-link>
        </form>

    </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import baseService from '../../../services/baseService';

const data = reactive({
    email: '',
    password: '',
    fullName: '',
    dateOfBirth: '',
    gender: true,
    phone: '',
    address: '',
});

const router = useRouter();

const submit = async () => {
    await baseService.post('/users/register', data);
    router.push('/login');
};
</script>