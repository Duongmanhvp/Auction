import axios from 'axios';
import { notification } from 'ant-design-vue';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/auction/v1',
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

axiosInstance.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, error => {
    return Promise.reject(error);
});

export default {
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