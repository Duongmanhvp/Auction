export default {
   // RESET_STATE(state) {
   //    Object.assign(state, getDefaultState());
   //  },
   setUser(state, user) {
      state.user.fullName = user.fullName || '';
      state.user.dateOfBirth = user.dateOfBirth || '';
      state.user.email = user.email || '';
      state.user.phone = user.phone || '';
      state.user.address = user.address || '';
      state.user.gender = user.gender || '';
      state.user.avatarUrl = user.avatarUrl || '';
    },
   // setFullName(state, fullName){
   //    state.user.fullName = fullName;
   // },
   // setDateOfBirth(state, dateOfBirth){
   //    state.user.dateOfBirth = dateOfBirth;
   // },
   // setEmail(state, email){
   //    state.user.email = email;
   // },
   // setPhone(state, phone){
   //    state.user.phone = phone;
   // },
   // setAddress(state, address){
   //    state.user.address = address;
   // },
   // setGender(state, gender){
   //    state.user.gender = gender;
   // },
   // setAvatarUrl(state, avatarUrl){
   //    state.user.avatarUrl = avatarUrl;
   // },
   setLoginState(state, isLogin){
      state.isLogin = isLogin;
   },
   setAdmin(state, isAdmin){
      state.isAdmin = isAdmin;
   },
   setLoading(state, isLoading){
      state.isLoading = isLoading;
   },
   // setEmail(state, email) {
   //    state.email = email;
   // },
   setImages(state, images) {
      state.images = images;
   },
   removeImage(state, imageToRemove) {
      state.images = state.images.filter(image => image !== imageToRemove);
   }
}