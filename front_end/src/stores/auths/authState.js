import { ref } from 'vue';

export default function useAuthState() {
    const user = ref([]);
    const isLogin = ref(false);
    const isAdmin = ref(false);
    const isLoading = ref(false);
    const email = ref('');

    return {
        user,
        isLogin,
        isAdmin,
        isLoading,
        email,
    };
}
