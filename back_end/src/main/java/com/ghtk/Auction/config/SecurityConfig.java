package com.ghtk.Auction.config;


import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final String[] PUBLIC_POST_ENDPOINTS = {"v1/users/register" };
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.authorizeHttpRequests(req ->
						req.requestMatchers(HttpMethod.POST , PUBLIC_POST_ENDPOINTS).permitAll()
								.anyRequest().authenticated()
				);
		
		//httpSecurity.oauth2ResourceServer(oAuth2 -> oAuth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		return httpSecurity.build();
	}
//	@Bean
//	JwtDecoder  jwtDecoder() {
//		SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
//		return NimbusJwtDecoder
//				.withSecretKey(secretKeySpec)
//				.macAlgorithm(MacAlgorithm.HS512)
//				.build();
//	};
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
}