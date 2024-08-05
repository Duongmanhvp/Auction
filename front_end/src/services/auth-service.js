import baseService from './base-service';

// const apiBaseUrl = import.meta.env.VITE_API_BASE_URL; ${apiBaseUrl}

const authService = {
  async login(email, password) {
    try {
      const response = await baseService.post('/auths/authenticate', { email, password });
      console.log('Login response:', response.data);
      return response.data;
    } catch (error) {
      console.error('Login error:', error);
      throw error;
    }
  },

  async register(data) {
    try {
      const response = await baseService.post('/users/', data);
      return response.data;
    } catch (error) {

      throw error;
    }
  },

  async verifyOTP(email, otp) {
    try {
      console.log('verify response:', email, otp);
      const response = await baseService.post(`/users/verify-otp`, { email, otp });
      console.log('verify response:', response.data);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async resendOTP(email) {
    try {
      const response = await baseService.post('/users/resend-otp', undefined,
        {
          email: encodeURIComponent(email)
        });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  // async logout(token) {
  //   try {
  //     console.log('verify response:', token);
  //     await baseService.post('/auths/logout', { token });
  //   } catch (error) {
  //     throw error;
  //   }
  // },

  async logout(token) {
    try {
      console.log('Logout token:', token);
      const response = await baseService.post('/auths/logout', { token });
      return response.data;
    } catch (error) {
      console.error('Logout error:', error);
      throw error;
    }
  },

  async introspect(token) {
    try {
      const response = await baseService.post('/auths/introspect', { token });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async refreshToken(refreshToken) {
    try {
      const response = await baseService.post('/auths/refresh', { refreshToken });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async forgetPassword(email) {
    try {
      const response = await baseService.put('/users/forget-password', { email });
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async changePassword(currentPassword, newPassword) {
    try {
      const response = await baseService.put('/users/change-password', {
        currentPassword,
        newPassword
      });
      return response.data;
    } catch (error) {
      throw error;
    }
  }

  // async getMyInfo() {
  //   try {
  //     const response = await baseService.get('/users/get-my-info');
  //     return response.data;
  //   } catch (error) {
  //     throw error;
  //   }
  // },

  // async updateMyInfo(data) {
  //   try {
  //     const response = await baseService.put('/users/update-my-info', data);
  //     return response.data;
  //   } catch (error) {
  //     throw error;
  //   }
  // },

};

export default authService;
