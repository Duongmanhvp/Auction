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
public class Auction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_auction")
	Long id;
	
	@Column(nullable = false, name = "id_account_created")
	Long idAccountCreated;
	
	@Column(nullable = false, name = "id_account_buyer")
	Long idAccountBuyer;
	
	@Column(nullable = false ,name = "title")
	String title;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "status")
	String statusAuction;
	
	LocalDateTime createdDate; // ngay nguoi ban tao
	
	LocalDateTime startDate; // ngay bat dau phien dau gia
	
	LocalDateTime endDate;
	
	Long startingBid; // gia khoi diem
	
	Long pricePerStep; // buoc nhay gia
	
	Long endingBid; // gia da chot
	
	LocalDateTime confirmDate; // ngay mo dang ky
	
	LocalDateTime endConfirmDate; // ngay dong dang ky
	
	String productType;
	
	String productName;
	
	String productDescription;
	
	String productImages;
	
	@ManyToMany(mappedBy = "auctions")
	Set<Account> accounts;

}
