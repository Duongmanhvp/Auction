import axios from 'axios';
import { notification } from 'ant-design-vue';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

axiosInstance.interceptors.response.use(
    response => response,
    error => {
        const message = error.response?.data?.message || 'Something went wrong!';
        notification.error({
            message: 'Error',
            description: message,
        });
        return Promise.reject(error);
    }
);

export default {
    // post(url, data) {
    //     return axiosInstance.post(url, data);
    // },

    get(url, params = {}) {
        return axiosInstance.get(url, { params });
    },
    post(url, data) {
        return axiosInstance.post(url, data);
    },
    put(url, data) {
        return axiosInstance.put(url, data);
    },
    delete(url, params = {}) {
        return axiosInstance.delete(url, { params });
    },

};
