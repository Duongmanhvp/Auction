package com.ghtk.auction.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "auction")
public class Auction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false, name = "buyer_id")
	Long buyerId;
	
	@Column(nullable = false ,name = "title")
	String title;

	@Column(name = "description", length = 100000)
	String description;
	
	@Column(name = "status")
	String status;

	@Column(nullable = false, name = "created_at")
	LocalDateTime createdAt; // ngay nguoi ban tao

	@Column(nullable = false, name = "start_time")
	LocalDateTime startTime; // ngay bat dau phien dau gia

	@Column(nullable = false, name = "end_time")
	LocalDateTime endTime;

	@Column(nullable = false, name = "start_bid")
	Long startBid; // gia khoi diem

	@Column(nullable = false, name = "price_per_step")
	Long pricePerStep; // buoc nhay gia

	@Column(name = "end_bid")
	Long endBid; // gia da chot

	@Column(nullable = false, name = "confirm_date")
	LocalDateTime confirmDate; // ngay mo dang ky

	@Column(nullable = false, name = "end_registration")
	LocalDateTime endRegistration; // ngay dong dang ky

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	Product product;
}
