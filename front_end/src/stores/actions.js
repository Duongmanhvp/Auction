import authApi from '../api/auths.js';
import {jwtDecode} from 'jwt-decode';
import { useStore } from 'vuex';


export default {


   async login({commit}, {email, password}) {
      try {
         commit('setLoading', true);
         const response = await authApi.login(email, password);
         
         localStorage.setItem("token", response.token);
         const decodedToken = jwtDecode(response.token);
         commit('setUser', decodedToken.sub);
         commit('setLoginState', true);
         const scope = decodedToken.scope;
         if (scope === 'ROLE_ADMIN') {
            commit('setAdmin', true);
         }
      } catch (error) {
         throw error;
      }
      finally {
         commit('setLoading', false);
      }
   },

   async logout({commit}) {
      try {
         await authApi.logout();
         commit('setUser', []);
         commit('setLoginState', false);
         commit('setAdmin', false);
      } catch (error) {
         throw error;
      }
   },

   async registry({commit}, data) {
      try {
         commit('setLoading', true);
         commit('setEmail', data.email);
         await authApi.registry(data);
         // commit('setUser', data.email);
      } catch (error) {
         throw error;
      }
      finally {
         commit('setLoading', false);
      }
   },

   async verify({commit}, data) {
      try {
         await authApi.verify(data);
      } catch (error) {
         throw error;
      }
   },

   async resendOtp({commit}, {email}) {
      try {
         await authApi.resendOtp(email);
      } catch (error) {
         throw error;
      }
   }
}