//package com.ghtk.auction.config;
//
//
//import com.ghtk.auction.entity.User;
//import com.ghtk.auction.enums.UserRole;
//import com.ghtk.auction.repository.UserRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Slf4j
//public class AppInit {
//
//	final PasswordEncoder passwordEncoder;
//
//	final String ADMIN_EMAIL = "admin@gmail.com";
//
//	final String ADMIN_PASSWORD = "admin123";
//
//
//	@ConditionalOnProperty(
//			prefix = "spring",
//			value = "datasource.driverClassName",
//			havingValue = "com.mysql.cj.jdbc.Driver")
//	@Bean
//	ApplicationRunner applicationRunner(UserRepository userRepository) {
//		log.info("Initializing application.....");
//		return args -> {
//			if (userRepository.findByRole(UserRole.ADMIN).isEmpty()){
//
//				userRepository.save(User.builder()
//						.email(ADMIN_EMAIL)
//						.password(passwordEncoder.encode(ADMIN_PASSWORD))
//						.role(UserRole.ADMIN)
//						.isVerified(true)
//						.build());
//				log.warn("admin user has been created with default password: admin123, please change it");
//			}
//			log.info("Application initialization completed .....");
//		};
//	}
//}
