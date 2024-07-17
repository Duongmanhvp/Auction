package com.ghtk.Auction.service.impl;

import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.request.UserForgetPasswordRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.entity.User;
import com.ghtk.Auction.exception.EmailException;
import com.ghtk.Auction.repository.UserRepository;
import com.ghtk.Auction.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	EmailServiceImpl emailService;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	final static String DEFAULT_PASSWORD = "jack97deptrai" ;
	
	@Override
	public UserResponse createUser(UserCreationRequest request) {
		
		//check username
		if (userRepository.existsByEmail(request.getEmail())) {
			try {
				throw new Exception("Email has been used!");
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setDateOfBirth(request.getDateOfBirth());
		user.setFullName(request.getFullName());
		user.setCreatedAt(LocalDateTime.now());
		user.setIsVerified(false);
		user.setPhone(request.getPhone());
		user.setStatusAccount("Active");
		user.setRole("USER");
		userRepository.save(user);
		
		
//		// gen OTP and save to Redis
//		String otp = generateOTP(user.getEmail());
//		redisTemplate.opsForValue().set(user.getEmail(),otp , Duration.ofMinutes(3));
//
//		//send OTP
//		emailService.sendOtpEmail(request.getEmail(), otp);
		
		sendOtp(user.getEmail());
		
		return UserResponse.builder()
				.email(request.getEmail())
				.password(request.getPassword())
				.isVerified(false)
				.build();
	}
	
	@Override
	public void reSendOTP(String email) {
		sendOtp(email);
	}
	
	
	@Override
	public boolean verifyOTP(String email, String otp) {
//		User user = userRepository.findByEmail(email);
//		if(user == null) {
//			throw new UsernameNotFoundException("User not found!");
//		} else if (user.getIsVerified()) {
//				throw new RuntimeException("Account is verified!");
//		}
		
		// Retrieve OTP from Redis
			String redisOtp = (String) redisTemplate.opsForValue().get(email);
			if (otp.equals(redisOtp)) {
			// Verify the user account if OTP is correct
				
				User user = userRepository.findByEmail(email);
				user.setIsVerified(true);
			
			//update on db
				userRepository.save(user);
			
			//remove OTP
				redisTemplate.delete(email);
			
				return true;
			}
			
		return false;
	}

	@Override
	public boolean forgetPassword(UserForgetPasswordRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			User user = userRepository.findByEmail(request.getEmail());
			String newPassword = generateRandomPassword(8);
			user.setPassword(passwordEncoder.encode(newPassword));
			//update on db
			userRepository.save(user);
			
			//send email
			emailService.sendDefaultPassword(user.getEmail(), newPassword);
			return true;
		}
		else{
			return false;
		}
	
	}
		
	
	@Override
	public Objects updatePassword() {
		return null;
	}
	
	@Override
	public Objects updateMyInfo() {
		return null;
	}
	
	@Override
	public Objects updateStatus() {
		return null;
	}
	
	@Override
	public Objects getAllInfo() {
		return null;
	}
	
	@Override
	public Objects getByPhone() {
		return null;
	}
	
	public Objects login(Object user) {
		return null;
	}
	
	public Objects getMyInfo(Object user) {
		return null;
	}
	
	public Objects getAnotherInfo(Object user) {
		return null;
	}
	
	public Objects updateMyInfo(Object user) {
		return null;
	}
	
	private String generateOTP() {
		//return String.valueOf((int) (Math.random() * 900000) + 100000);
		return  String.format("%06d", new Random().nextInt(999999));
	}
	private String generateRandomPassword(int num) {
		return RandomStringUtils.randomAlphanumeric(num);
	}
	
	private void sendOtp(String email) {
		// gen OTP and save to Redis
		String otpSent = generateOTP();
		redisTemplate.opsForValue().set(email,otpSent , Duration.ofMinutes(1));
		
		//send OTP
		emailService.sendOtpEmail(email, otpSent);
	}
	
}
