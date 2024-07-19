package com.ghtk.Auction.controller;


import com.ghtk.Auction.dto.request.UserChangePasswordRequest;
import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.request.UserForgetPasswordRequest;
import com.ghtk.Auction.dto.request.UserUpdateRequest;
import com.ghtk.Auction.dto.response.PageResponse;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.entity.User;
import com.ghtk.Auction.service.UserService;
import com.ghtk.Auction.service.impl.EmailServiceImpl;
import com.ghtk.Auction.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public String test(@RequestParam String email,@RequestParam String otp) {
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

	@PutMapping("/forget-password")
	public ResponseEntity<String> forgetPassword(@RequestBody UserForgetPasswordRequest request) {
		boolean result = userService.forgetPassword(request);
		return result ? ResponseEntity.ok("Password reset successfully. Please check your email for the new password.")
				: ResponseEntity.badRequest().body("Forget password failed. Email not found.");
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequest request) {
		boolean result = userService.updatePassword(request);
		return result ? ResponseEntity.ok("Password changed successfully.")
				: ResponseEntity.badRequest().body("Password change failed. ");
	}

	@GetMapping("/getMyInfo")
	public ResponseEntity<User> getMyInfo() {
		return ResponseEntity.ok(userService.getMyInfo());
	}
	
	@PutMapping("/updateMyInfo")
	public ResponseEntity<User> updateMyInfo(@RequestBody  UserUpdateRequest request) {
		return ResponseEntity.ok(userService.updateMyInfo(request));
	}
	
	@GetMapping("/getAnotherInfo")
	public Object getAnother() {
		return null;
	}

	@GetMapping("/getAllInfo")
	public ResponseEntity<PageResponse> getAllInfo(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
	){
		return ResponseEntity.status(HttpStatus.OK)
				.body(userService.getAllInfo(pageNo, pageSize, sortBy, sortDir));
	}
}
