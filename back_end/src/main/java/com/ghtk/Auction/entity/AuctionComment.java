package com.ghtk.Auction.entity;

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
@Table(name = "Auction_Comment")
public class AuctionComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(nullable = false, name = "content", length = 100000)
	String content;

	@Column(nullable = false, name = "create_time")
	LocalDateTime createTime;

	@Column(name = "rating")
	Long rating;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auction_id")
	Auction auctionId;
	
}
