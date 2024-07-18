package com.ghtk.Auction.service;

import com.ghtk.Auction.dto.request.UserChangePasswordRequest;
import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.request.UserForgetPasswordRequest;
import com.ghtk.Auction.dto.request.UserUpdateRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.entity.User;

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
	
	Objects getAllInfo();
	
	Objects getAnotherInfo(Object user);
}