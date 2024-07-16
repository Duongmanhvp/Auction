package com.ghtk.Auction.controller;


import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody UserCreationRequest request) {
		
		return ResponseEntity.ok(userService.createUser(request));
		
	}
	
	@PostMapping("/verify-otp")
	public Object verify() {
		return null;
	}
	
	@PostMapping("/resend-otp")
	public Object reSendOtp() {
		return null;
	}
	
	@PostMapping("/login")
	public Object login() {
		return null;
	}
	
	@PostMapping("/forget-password")
	public Object forgetPassword() {
		return null;
	}
	
	@GetMapping("/getMyInfo")
	public Object getMyInfo() {
		return null;
	}
	
	@GetMapping("/getAnotherInfo")
	public Object getAnother() {
		return null;
	}
}
