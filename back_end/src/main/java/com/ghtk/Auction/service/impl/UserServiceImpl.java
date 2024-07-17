package com.ghtk.Auction.service.impl;

import com.ghtk.Auction.dto.request.UserCreationRequest;
import com.ghtk.Auction.dto.response.UserResponse;
import com.ghtk.Auction.repository.UserRepository;
import com.ghtk.Auction.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
		
//		User user = new User();
//		user.setEmail(request.getEmail());
//		user.setPassword(passwordEncoder.encode(request.getPassword()));
//		user.setDateOfBirth(request.getDateOfBirth());
//		user.setFullName(request.getFullName());
//		user.setCreatedAt(LocalDateTime.now());
//		user.setIsVerified(false);
//		user.setStatusAccount("Active");
//		user.setRole("USER");
//		userRepository.save(user);
		
		return UserResponse.builder()
				.email(request.getEmail())
				.password(request.getPassword())
				.isVerified(false)
				.build();
	}
	
	@Override
	public Objects forgetPassword() {
		return null;
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
	
}
