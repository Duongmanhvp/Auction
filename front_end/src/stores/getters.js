export default {
   getLoginState(state) {
      return state.isLogin;
   },
   getUser(state) {
      return state.user;
   },
   getIsAdmin(state) {
      return state.isAdmin;
   },
   getLoading(state) {
      return state.isLoading;
   },
   getEmail(state) {
      return state.email;
   },
   getImages(state) {
      return state.images;
   }
} 