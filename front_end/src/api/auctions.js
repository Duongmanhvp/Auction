import api from './axios.js';
import { message } from 'ant-design-vue';

const auctionApi = {
   async addAuction(data) {
      // try {
      //    const token = localStorage.getItem('token');
      //    const response = await api.post('/v1/products', data, { headers: { Authorization: `Bearer ${token}` } });
      //    message.success(response.data.message);
      //    return response.data.data;
      // } catch (error) {
      //    message.error(error.response.data.message);
      //    throw error;
      // }
   },

   async getMyAuction() {
      try {
         const token = localStorage.getItem('token');
         const response = await api.get('/v1/auctions/get-my-created', { headers: { Authorization: `Bearer ${token}` } });
         message.success(response.data.message);
         return response.data.data;
      } catch (error) {
         message.error(error.response.data.message);
         throw error;
      }
   },

}
export default auctionApi;