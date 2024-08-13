import axios from "axios";

const api = axios.create({
   baseURL: "http://localhost:8080/auction",
   headers: {
      "Content-Type": "application/json",
   },
   timeout: 5000,
});

api.interceptors.request.use((config) => {
   const token = localStorage.getItem("token");
   if (token) {
      config.headers.Authorization = `Bearer ${token}`;
   }
   return config;
});

api.interceptors.response.use(
   (response) => response,
   (error) => {
      console.error("An error occurred:", error);
      return Promise.reject(error);
   }
);

export default api;