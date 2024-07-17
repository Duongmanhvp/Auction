package com.ghtk.Auction.controller;


import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.request.UserForgetPasswordRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.exception.EmailException;
import com.ghtk.Auction.service.UserService;
import com.ghtk.Auction.service.impl.EmailServiceImpl;
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
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@PostMapping("/test")
	public String test(@RequestParam String email,@RequestParam String otp) throws EmailException {
		emailService.sendOtpEmail(email, otp);
		return "Sent!";
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreationRequest request) {
		
		return ResponseEntity.ok(userService.createUser(request));
		
	}
	
	@PostMapping("/resend-otp")
	public ResponseEntity<String> reSendOtp(@RequestParam String email) {
		userService.reSendOTP(email);
		return ResponseEntity.ok("OTP sent!");

	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam String email,@RequestParam  String otp ) {
		
		
		if (!userService.verifyOTP(email,otp)){
			return ResponseEntity.badRequest().body("Invalid OTP or OTP expired.");
		}
		return ResponseEntity.ok("Account verified successfully.");
	}
	
	
	
	@PostMapping("/login")
	public Object login() {
		return null;
	}
	
	@PutMapping("/forget-password")
	public ResponseEntity<String> forgetPassword(@RequestBody UserForgetPasswordRequest request) {
		boolean result = userService.forgetPassword(request);
		return result ? ResponseEntity.ok("Password reset successfully. Please check your email for the new password.")
				: ResponseEntity.badRequest().body("Forget password failed. Email not found.");
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
