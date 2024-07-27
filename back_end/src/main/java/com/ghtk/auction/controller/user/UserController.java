package com.ghtk.auction.controller.user;


import com.ghtk.auction.dto.request.user.UserChangePasswordRequest;
import com.ghtk.auction.dto.request.user.UserCreationRequest;
import com.ghtk.auction.dto.request.user.UserForgetPasswordRequest;
import com.ghtk.auction.dto.request.user.UserUpdateRequest;
import com.ghtk.auction.dto.response.ApiResponse;
import com.ghtk.auction.dto.response.user.PageResponse;
import com.ghtk.auction.dto.response.user.UserResponse;
import com.ghtk.auction.entity.User;
import com.ghtk.auction.exception.EmailException;
import com.ghtk.auction.service.UserService;
import com.ghtk.auction.service.impl.EmailServiceImpl;
import com.ghtk.auction.utils.AppConstants;
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
	public ResponseEntity<ApiResponse<Object>> test(@RequestParam String email, @RequestParam String otp) {
		emailService.sendOtpEmail(email, otp);
		return ResponseEntity.ok(ApiResponse.success("Sent!"));
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserCreationRequest request) {
		return ResponseEntity.ok(ApiResponse.success(userService.createUser(request)));
		
	}

	@PostMapping("/resend-otp")
	public ResponseEntity<ApiResponse<Object>> reSendOtp(@RequestParam String email) {
		userService.reSendOTP(email);
		return ResponseEntity.ok(ApiResponse.success("OTP sent!"));

	}
	
	@PostMapping("/verify-otp")
	public ResponseEntity<ApiResponse<Object>> verifyOtp(@RequestParam String email,@RequestParam  String otp ) {
		if (!userService.verifyOTP(email,otp)){
			throw new EmailException("Invalid OTP or OTP expired.");
		}
		return ResponseEntity.ok(ApiResponse.success("Account verified successfully."));
	}

	@PutMapping("/forget-password")
	public ResponseEntity<ApiResponse<Object>> forgetPassword(@RequestBody UserForgetPasswordRequest request) {
		boolean result = userService.forgetPassword(request);
		return result ? ResponseEntity.ok(ApiResponse.success("Password reset successfully. Please check your email for the new password."))
				: ResponseEntity.badRequest().body(ApiResponse.error("Forget password failed. Email not found."));
	}
	
	@PutMapping("/change-password")
	public ResponseEntity<ApiResponse<Object>> changePassword(@RequestBody UserChangePasswordRequest request) {
		boolean result = userService.updatePassword(request);
		return result ? ResponseEntity.ok(ApiResponse.success("Password changed successfully."))
				: ResponseEntity.badRequest().body(ApiResponse.error("Password change failed."));
	}

	@GetMapping("/getMyInfo")
	public ResponseEntity<ApiResponse<User>> getMyInfo() {
		return ResponseEntity.ok(ApiResponse.success(userService.getMyInfo()));
	}
	
	@PutMapping("/updateMyInfo")
	public ResponseEntity<ApiResponse<User>> updateMyInfo(@RequestBody  UserUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(userService.updateMyInfo(request)));
	}
	
	@GetMapping("/getAnotherInfo")
	public Object getAnother() {
		return null;
	}

	@GetMapping("/getAllInfo")
	public ResponseEntity<ApiResponse<PageResponse>> getAllInfo(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
	){
		return ResponseEntity.ok(ApiResponse.success(userService.getAllInfo(pageNo, pageSize, sortBy, sortDir)));
	}
}
