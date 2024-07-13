package com.ghtk.Auction.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, unique = true, name = "email")
	String email;
	
	@Column(nullable = false, name = "password")
	String password;
	
	@Column(nullable = false, name = "full_name")
	String fullName;
	
	@Column(nullable = false, name = "birthday")
	LocalDateTime dateOfBirth;
	
	@Column(name = "gender")
	boolean gender;
	
	@Column(name = "address")
	String address;
	
	@Column(name = "status")
	String statusAccount;
	
	@Column(name = "avatar_url")
	String avatar;
	
	@Column(name = "created_at")
	LocalDate createdAt;
	
	@Column(name = "role")
	String role;

}
