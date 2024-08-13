import { commonInputProps } from 'ant-design-vue/es/vc-input/inputProps.js';
import authApi from '../api/auths.js';
import imageApi from '../api/images.js';
import productApi from '../api/products.js';
import { jwtDecode } from 'jwt-decode';
import { useStore } from 'vuex';


export default {


   // clearAllState({ commit }) {
   //    commit('RESET_STATE');
   //  },

   async login({ commit }, { email, password }) {
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

   async logout({ commit }) {
      try {
         await authApi.logout();
         commit('setUser', []);
         commit('setLoginState', false);
         commit('setAdmin', false);
      } catch (error) {
         throw error;
      }
   },

   async registry({ commit }, data) {
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

   async verify({ commit }, data) {
      try {
         await authApi.verify(data);
      } catch (error) {
         throw error;
      }
   },

   async resendOtp({ commit }, { email }) {
      try {
         await authApi.resendOtp(email);
      } catch (error) {
         throw error;
      }
   },

   async getMyProfile({ commit }) {
      try {

         const response = await authApi.getMyInfo();
         console.log(response);
         commit('setUser',
            [response.fullName, response.dateOfBirth, response.email, response.phone, response.address, response.gender]);
      } catch (error) {
         throw error;
      }
   },

   async changePassword({ commit }, { oldPassword, newPassword }) {
      try {
         await authApi.changePassword(oldPassword, newPassword);
      } catch (error) {
         throw error;
      }
   },

   async updateMyInfo({ commit }, data) {
      try {
         await authApi.updateMyInfo(data);
      } catch (error) {
         throw error;
      }
   },

   async uploadImage({ commit, state }, images) {
      try {
         const response = await imageApi.uploadImage(images);
         // const updateImages = [...state.images, response];
         // commit('setImages', updateImages);
         return response;
      } catch (error) {
         throw error;
      } finally {
         //commit('setImages', []);
      }
   },

   deleteImage({ commit }, image) {
      commit('removeImage', image);
   },

   async addProduct({ commit }, data) {
      try {
         await productApi.addProduct(data);
         commit('setImages', []);
      } catch (error) {
         throw error;
      }
   }
}