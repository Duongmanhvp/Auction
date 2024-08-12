import authApi from '../../api/auths.js';
import { jwtDecode } from 'jwt-decode';

export default function useAuthActions(state) {
   const login = async ({ email, password }) => {
      try {
         state.isLoading.value = true;
         const response = await authApi.login(email, password);

         localStorage.setItem("token", response.token);
         const decodedToken = jwtDecode(response.token);
         state.user.value = decodedToken.sub;
         state.isLogin.value = true;

         if (decodedToken.scope === 'ROLE_ADMIN') {
            state.isAdmin.value = true;
         }
      } catch (error) {
         throw error;
      } finally {
         state.isLoading.value = false;
      }
   };

   const logout = async () => {
      try {
         await authApi.logout();
         state.user.value = [];
         state.isLogin.value = false;
         state.isAdmin.value = false;
      } catch (error) {
         throw error;
      }
   };

   const registry = async (data) => {
      try {
         state.isLoading.value = true;
         state.email.value = data.email;
         await authApi.registry(data);
      } catch (error) {
         throw error;
      } finally {
         state.isLoading.value = false;
      }
   };

   const verify = async (data) => {
      try {
         await authApi.verify(data);
      } catch (error) {
         throw error;
      }
   };

   const resendOtp = async ({ email }) => {
      try {
         await authApi.resendOtp(email);
      } catch (error) {
         throw error;
      }
   };

   return {
      login,
      logout,
      registry,
      verify,
      resendOtp,
   };
}
