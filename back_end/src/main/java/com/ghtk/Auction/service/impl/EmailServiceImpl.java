package com.ghtk.Auction.service.impl;

import com.ghtk.Auction.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendOtpEmail(String to, String otp) {

		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject("Your OTP Code");
			message.setText("Your OTP code is: " + otp);
			mailSender.send(message);
		} catch (Exception e) {
			throw new EmailException("OTP service unavailable", e);
		}
	}
	public void sendDefaultPassword(String to, String password) {
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject("Your New PassWord");
			message.setText("Your new password is: " + password);
			mailSender.send(message);
		} catch (Exception e) {
			throw new RuntimeException("OTP service unavailable", e);
		}
	}
}
