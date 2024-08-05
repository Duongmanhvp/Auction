import { message } from 'ant-design-vue';
import { defineStore } from 'pinia';
import { ref } from 'vue';
import authService from '../../services/auth-service';
// import { useRouter } from 'vue-router';
import { jwtDecode } from 'jwt-decode';

// const router = useRouter();

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null);
    const token = ref(localStorage.getItem('token') || null);
    const isAuth = ref(!!token.value);
    const email = ref(localStorage.getItem('email') || null);
    const isAdmin = ref(localStorage.getItem('isAdmin') === 'true');
    const scope = ref('');
    // const role = ref(localStorage.getItem('role') || null);
    const loading = ref(false);

    // const user = ref(JSON.parse(localStorage.getItem('user')) || null);
    // const token = ref(localStorage.getItem('token') || null);
    // const isAuth = ref(!!user.value && !!token.value);
    // const email = ref(localStorage.getItem('email') || null);
    // const loading = ref(false);

    // function decodeToken(token) {
    //     try {
    //         const decoded = jwtDecode(token);
    //         console.log('Decoded token:', decoded);
    //         role.value = decoded.scope;
    //         email.value = decoded.sub;
    //         localStorage.setItem('email', decoded.sub);
    //         localStorage.setItem('role', decoded.scope);
    //     } catch (error) {
    //         console.error('Invalid token:', error);
    //     }
    // }

    function setAuth(data) {
        console.log('Setting auth data:', data);
        user.value = data.user;
        token.value = data.token;
        isAuth.value = true;
        email.value = data.email;
        isAdmin.value = jwtDecode(data.token).scope === 'ROLE_ADMIN';
        localStorage.setItem('user', JSON.stringify(data.user));
        localStorage.setItem('token', JSON.stringify(data.token));
        localStorage.setItem('isAuth', 'true');
        localStorage.setItem('email', data.email);
        localStorage.setItem('isAdmin', isAdmin.value.toString());
        // localStorage.setItem('isAdmin', isAdmin.value);
        localStorage.setItem('scope', scope.value);
    }

    function clearAuth() {
        user.value = null;
        token.value = null;
        isAuth.value = false;
        email.value = null;
        isAdmin.value = false;
        scope.value = '';
        localStorage.removeItem('user');
        localStorage.removeItem('token');
        localStorage.removeItem('isAuth');
        localStorage.removeItem('email');
        localStorage.removeItem('isAdmin');
        localStorage.removeItem('scope');
    }

    async function login(email, password) {
        try {
            loading.value = true;
            const response = await authService.login(email, password);
            setAuth(response.data);
            // if (isAdmin.value) {
            //     router.push('/admin/dashboard');
            // } else {
            //     router.push('/user/default');
            // }
            message.success('Logged in successfully');
        } catch (error) {
            message.error('Login failed. Please check your credentials.');
            console.error('Login error:', error);
        } finally {
            loading.value = false;
        }
    }

    // async function login(email, password) {
    //     try {
    //         loading.value = true;
    //         const response = await authService.login(email, password);
    //         setAuth(response.data);
    //         message.success('Logged in successfully');
    //     } catch (error) {
    //         message.error('Login failed. Please check your credentials.');
    //         console.error('Login error:', error);
    //     } finally {
    //         loading.value = false;
    //     }
    // }

    async function register(data) {
        try {
            loading.value = true;
            const response = await authService.register(data);
            setAuth(response.data);
            message.success('Registered successfully');
            return response;
        } catch (error) {
            console.error('Registration error:', error);
            message.error('Registration failed. Please try again.');
            throw error;
        } finally {
            loading.value = false;
        }
    }

    async function resendOTP() {
        try {
            await authService.resendOTP(email.value);
            message.success('Verification code has been resent');
            startCountdown();
        } catch (error) {
            console.error('Resend error:', error);
            message.error('Failed to resend verification code. Please try again.');
        }
    }

    async function verifyOTP(email, otp) {
        try {
            const response = await authService.verifyOTP(email, otp);
            console.log('Verification response:', response);
            message.success('OTP verified successfully');
            // router.push('/user/default');
        } catch (error) {
            console.error('Verification error:', error);
            if (error.response && error.response.data && error.response.data.message) {
                message.error(error.response.data.message);
            } else {
                message.error('Verification failed. Please try again.');
            }
        }
    }

    async function logout() {
        try {
            await authService.logout(token.value);
            message.success('Logged out successfully');
        } catch (error) {
            console.error('Logout error:', error);
            message.error('Logout failed. Please try again.');
        } finally {
            clearAuth();
        }
    }

    async function introspect() {
        try {
            const response = await authService.introspect(token.value);
            user.value = response.user;
            isAuth.value = true;
        } catch (error) {
            console.error('Introspect error:', error);
            clearAuth();
        }
    }

    async function refreshToken() {
        try {
            const response = await authService.refreshToken(token.value);
            setAuth(response);
        } catch (error) {
            console.error('Refresh token error:', error);
            clearAuth();
        }
    }

    async function forgetPassword(email) {
        try {
            await authService.forgetPassword(email);
            message.success('Please check your email for the new password.');
        } catch (error) {
            message.error('Failed to reset password.');
        }
    }

    async function changePassword(currentPassword, newPassword) {
        try {
            await authService.changePassword(currentPassword, newPassword);
            message.success('Password changed successfully!');
        } catch (error) {
            message.error('Failed to change password.');
        }
    }

    // async function getMyInfo() {
    //     try {
    //         const response = await authService.getMyInfo();
    //         user.value = response.data;
    //     } catch (error) {
    //         message.error('Failed to retrieve user info.');
    //     }
    // }

    // async function updateMyInfo(data) {
    //     try {
    //         await authService.updateMyInfo(data);
    //         message.success('User info updated successfully!');
    //     } catch (error) {
    //         message.error('Failed to update user info.');
    //     }
    // }

    // if (localStorage.getItem('isAuth') === 'true') {
    //     user.value = JSON.parse(localStorage.getItem('user'));
    //     token.value = localStorage.getItem('token');
    //     isAuth.value = true;
    //     email.value = localStorage.getItem('email');
    //     introspect();
    // }


    return {
        user,
        token,
        isAuth,
        email,
        isAdmin,
        scope,
        loading,
        login,
        logout,
        introspect,
        refreshToken,
        register,
        resendOTP,
        verifyOTP,
        clearAuth,
        forgetPassword,
        changePassword,
        // getMyInfo,
        // updateMyInfo,
    };
});
