import api from './axios.js';
import { message } from 'ant-design-vue';

const authApi = {
   async login(email, password) {
      try {
         const response = await api.post('/v1/auths/authenticate', { email, password });
         message.success(response.data.message);
         return response.data.data;
      } catch (error) {
         message.error(error.response.data.message);
         throw error;
      }
   },

   async logout() {
      try {
         const token = localStorage.getItem('token');
         const response = await api.post('/v1/auths/logout', {token});
         localStorage.removeItem('token');
         return response.data.message;
      } catch (error) {
         throw error;
      }
   },

   async registry(data) {
      try {
         const response = await api.post('/v1/users/', data);
         message.success(response.data.message);
         console.log("dang ky thanh cong");
         return response.data.data;
      } catch (error) {
         console.log("deo dang ky duoc");
         message.error(error.response.data.message);
         throw error;
      }
   },

   async verify(data) {
      try {
         const response = await api.post('/v1/users/verify-otp?email=' + data.email + '&otp=' + data.otp);
         message.success(response.data.message);
         return response.data.data;
      } catch (error) {
         message.error(error.response.data.message);
         throw error;
      }
   }
}
export default authApi;