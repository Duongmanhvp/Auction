package com.ghtk.Auction.controller;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
	
	@PostMapping("/register")
	public Object register() {
		return null;
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
