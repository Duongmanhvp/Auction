export default {
   setUser(state, matchUser){
      state.user = matchUser;
   },
   setLoginState(state, isLogin){
      state.isLogin = isLogin;
   },
   setAdmin(state, isAdmin){
      state.isAdmin = isAdmin;
   },
   setLoading(state, isLoading){
      state.isLoading = isLoading;
   },
   setEmail(state, email) {
      state.email = email;
   }
}