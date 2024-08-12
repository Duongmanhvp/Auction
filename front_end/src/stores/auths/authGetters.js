import { computed } from 'vue';

export default function useAuthGetters(state) {
   const getLoginState = computed(() => state.isLogin.value);
   const getUser = computed(() => state.user.value);
   const getIsAdmin = computed(() => state.isAdmin.value);
   const getLoading = computed(() => state.isLoading.value);
   const getEmail = computed(() => state.email.value);

   return {
      getLoginState,
      getUser,
      getIsAdmin,
      getLoading,
      getEmail,
   };
}
