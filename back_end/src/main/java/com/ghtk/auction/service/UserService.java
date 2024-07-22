package com.ghtk.auction.service;

import com.ghtk.auction.dto.request.user.UserChangePasswordRequest;
import com.ghtk.auction.dto.request.user.UserCreationRequest;
import com.ghtk.auction.dto.request.user.UserForgetPasswordRequest;
import com.ghtk.auction.dto.request.user.UserUpdateRequest;
import com.ghtk.auction.dto.response.user.PageResponse;
import com.ghtk.auction.dto.response.user.UserResponse;
import com.ghtk.auction.entity.User;

import java.util.Objects;

public interface UserService {
	
	UserResponse createUser(UserCreationRequest request);
	
	void reSendOTP(String email);
	
	boolean verifyOTP(String email, String otp);
	
	boolean forgetPassword(UserForgetPasswordRequest request);
	
	boolean updatePassword( UserChangePasswordRequest request);
	
	User getMyInfo() ;
	
	User updateMyInfo(UserUpdateRequest request);
	
	Objects getByPhoneorEmail();
	
	Objects updateStatus();
	
	PageResponse<UserResponse> getAllInfo(int pageNo, int pageSize, String sortBy, String sortDir);
	
	Objects getAnotherInfo(Object user);
}