package com.ghtk.Auction.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "id_account")
	Long id;
	@Column(nullable = false, unique = true, length = 10, name = "phone_number")
	String phoneNumber;
	
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
	
	@ManyToMany
	@JoinTable(
			name = "account_auction",
			joinColumns = @JoinColumn(name = "id_account"),
			inverseJoinColumns = @JoinColumn(name = "id_auction")
	)
	Set<Auction> auctions;
	

}
