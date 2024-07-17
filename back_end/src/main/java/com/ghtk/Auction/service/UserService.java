package com.ghtk.Auction.service;
import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.request.UserForgetPasswordRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import java.util.Objects;

public interface UserService {
	
	UserResponse createUser(UserCreationRequest request);
	

	Objects forgetPassword();

	void reSendOTP(String email);
	
	boolean verifyOTP(String email, String otp);
	
	boolean forgetPassword(UserForgetPasswordRequest request);
	
	Objects updatePassword();
	
	Objects updateMyInfo();
	
	Objects updateStatus();
	
	Objects getAllInfo();
	
	Objects getByPhone();
	
	Objects login(Object user) ;
	
	Objects getMyInfo(Object user) ;
	
	Objects getAnotherInfo(Object user);
}
