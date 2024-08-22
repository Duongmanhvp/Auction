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

  async getMyJoined() {
    const token = localStorage.getItem('token');
    const response = await api.get('/v1/auctions/get-my-registered', { headers: { Authorization: `Bearer ${token}` } });
    return response.data.data;
  },

  async getAuctionById(id) {
    try {
      const response = await api.get('/v1/auctions/' + id);
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
    }
  },
  async getAllAuctionByStatus(status, pageNo) {
    const response = await api.get('/v1/auctions/get-all-auction-by-status?statusAuction=' + status + '&pageSize=4' + '&pageNo=' + pageNo)
    return response.data.data;
  },

  async registerAuction(id) {
    const response = await api.post('/v1/auctions/' + id + '/regis-join');
  }

}
export default auctionApi;
