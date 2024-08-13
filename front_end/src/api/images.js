import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080/auction",
    headers: {
        'Content-Type': 'multipart/form-data',
    },
    timeout: 5000,
});


const imageApi = {
    async uploadImage(images) {
        try {
            const token = localStorage.getItem("token");
            const response = await api.post('/v1/images', images, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            return response.data.data;
        } catch (error) {
            throw error;
        }
    },
};
export default imageApi;