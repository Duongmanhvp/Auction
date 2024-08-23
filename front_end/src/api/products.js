import api from "./axios.js";
import { message } from "ant-design-vue";

const productApi = {
  async addProduct(data) {
    try {
      const token = localStorage.getItem("token");
      const response = await api.post("/v1/products", data, {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async getProducts() {
    try {
      const token = localStorage.getItem("token");
      const response = await api.get("/v1/products/get-my-all", {
        headers: { Authorization: `Bearer ${token}` },
      });
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async getProductById(id) {
    try {
      const response = await api.get("/v1/products/" + id);
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },
  async getTopProducts() {
    try {
      const response = await api.get("/v1/products/top-popular?limit=4");
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async getNewProducts() {
    try {
      const response = await api.get(
        "/v1/products/get-all-product?sortDir=desc&sortBy=id"
      );
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async getAllProductByCategory(category, pageNo) {
    try {
      const response = await api.get(
        "/v1/products/get-all-product-by-category?category=" +
        category +
        "&pageSize=4" +
        "&pageNo=" +
        pageNo
      );
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async getProductById(id) {
    try {
      const response = await api.get("/v1/products/" + id);
      return response.data.data;
    } catch (error) {
      message.error(error.response.data.message);
      throw error;
    }
  },

  async interestProduct(id) {
    try {
      const response = await api.post("/v1/products/interest/" + id);
      console.log(response);
      return response.data;
    } catch (error) {
      message.error(error.response.data.message);
      // return error.response.data.message;
      throw error;
    }
  },
};
export default productApi;
