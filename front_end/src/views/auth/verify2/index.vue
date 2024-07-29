<template>
    <div class="container mx-auto max-w-sm p-0">
        <img src="../../../assets/logo.png" alt="Logo" class="h-56 w-56 ml-16 flex items-center justify-center">
        <router-link to="/login" class="flex items-center space-x-2 -ml-16 mb-4 text-gray-600 hover:text-gray-900">
            <img src="../../../assets/icon/auth-back.svg" alt="Back" class="w-6 h-6" />
            <span>Back</span>
        </router-link>
        <h1 class="flex items-center justify-center text-2xl font-bold mb-4">Verification</h1>
        <h2>
            Verification code is sent to your email. Please check and enter the verification code:
        </h2>

        <form @submit.prevent="onSubmit">
            <div class="flex justify-between mt-10 mb-10">
                <input type="text" id="code1" v-model="codes[0]" @input="moveFocus(0)"
                    @keydown="checkBackspace($event, 0)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
                <input type="text" id="code2" v-model="codes[1]" @input="moveFocus(1)"
                    @keydown="checkBackspace($event, 1)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
                <input type="text" id="code3" v-model="codes[2]" @input="moveFocus(2)"
                    @keydown="checkBackspace($event, 2)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
                <input type="text" id="code4" v-model="codes[3]" @input="moveFocus(3)"
                    @keydown="checkBackspace($event, 3)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
                <input type="text" id="code5" v-model="codes[4]" @input="moveFocus(4)"
                    @keydown="checkBackspace($event, 4)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
                <input type="text" id="code6" v-model="codes[5]" @input="moveFocus(5)"
                    @keydown="checkBackspace($event, 5)" @keypress="isNumber"
                    class="form-input w-14 h-14 border border-gray-300 rounded-md px-2 py-2 focus:outline-none focus:ring-2 focus:ring-blue-600"
                    maxlength="1" inputmode="numeric" pattern="[0-9]*" />
            </div>

            <!-- resend btn -->
            <div class="flex flex-col items-center justify-center mt-6 mb-6">
                <button :disabled="isResendDisabled" @click="resendCode"
                    class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded disabled:bg-gray-400">
                    Resend Code
                </button>
                <p v-if="!isResendDisabled" class="mt-2 text-sm text-gray-600">Resend available in {{ countdown }}
                    seconds
                </p>
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


// delete code
const checkBackspace = (event, index) => {
    if (event.key === 'Backspace') {
        if (codes[index] === '' && index > 0) {
            const previousIndex = index - 1;
            const previousInput = document.getElementById(`code${previousIndex + 1}`);
            previousInput.focus();
        }
    }
};
const moveFocus = (index) => {
    if (codes[index] && index < codes.length - 1) {
        const nextInput = document.getElementById(`code${index + 2}`);
        if (nextInput) {
            nextInput.focus();
        }
    }
};

//check number
const isNumber = (event) => {
    const charCode = event.which ? event.which : event.keyCode;
    if (charCode < 48 || charCode > 57) {
        event.preventDefault();
    }
};

//cooldown resend code
const startCountdown = () => {
    countdown.value = 30;
    isResendDisabled.value = true;
    const timer = setInterval(() => {
        countdown.value -= 1;
        if (countdown.value <= 0) {
            clearInterval(timer);
            isResendDisabled.value = false;
        }
    }, 1000);
};

const resendCode = async () => {
    await baseService.post('/users/resend-otp');
    startCountdown();
};

const onSubmit = async () => {
    const otp = codes.join('');
    await baseService.post('/users/verify-otp', { otp });
    router.push('/login');
};

// startCountdown();
</script>

<style scoped>
.min-h-screen {
    min-height: 100vh;
}

.bg-cover {
    background-size: cover;
}

.bg-center {
    background-position: center;
}

.bg-fixed {
    background-attachment: fixed;
}

.form-input {
    text-align: center;
}
</style>
