import api from './axios.js';
import { message } from 'ant-design-vue';

const productApi = {
    async addProduct(data) {
        try {
            const token = localStorage.getItem('token');
            const response = await api.post('/v1/products', data, { headers: { Authorization: `Bearer ${token}` } });
            message.success(response.data.message);
            return response.data.data;
        } catch (error) {
            message.error(error.response.data.message);
            throw error;
        }
    },


}
export default productApi;