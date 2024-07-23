package com.ghtk.auction.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(
			HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		response.setStatus(401);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		
		response.getWriter().write(response.getStatus());
		response.getWriter().write(authException.getMessage());
		response.flushBuffer();
	}
	
}