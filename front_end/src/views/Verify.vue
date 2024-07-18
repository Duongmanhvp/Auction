<template>
    <div class="container mx-auto max-w-sm p-0">
        <img src="../assets/logo.png" alt="Logo" class="h-56 h-56 ml-16 flex items-center justify-center">
        <router-link to="/login" class=" flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
            </svg>
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Forgot your password?</h1>
        <h2>
            Verification code is sent to your email. Please check and enter the verification code:
        </h2>

        <form @submit.prevent="onSubmit">
            <div class="flex justify-between mt-10 mb-10">
                <input type="text" id="code1" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code2" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code3" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code4" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code5" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
                <input type="text" id="code" v-model="code"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600" />
            </div>

            <button type="submit"
                class="btn btn-primary w-full bg-green-600 hover:bg-green-700 text-white font-bold py-2 px-4 rounded">
                Next
            </button>
        </form>
    </div>
</template>

<script>
export default {
    data() {
        return {
            username: '',
            password: '',
            rememberMe: false,
        };
    },
    methods: {
        onSubmit() {
            console.log('Xác minh thất bại');
        },
    },
};
</script>