export default {
    // RESET_STATE(state) {
    //    Object.assign(state, getDefaultState());
    //  },
    setUser(state, matchUser) {
        state.user = matchUser;
    },
    setLoginState(state, isLogin) {
        state.isLogin = isLogin;
    },
    setAdmin(state, isAdmin) {
        state.isAdmin = isAdmin;
    },
    setLoading(state, isLoading) {
        state.isLoading = isLoading;
    },
    setEmail(state, email) {
        state.email = email;
    },
    setImages(state, images) {
        state.images = images;
    },
    removeImage(state, imageToRemove) {
        state.images = state.images.filter(image => image !== imageToRemove);
    }
}