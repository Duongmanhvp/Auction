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
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	String content;
	
	LocalDateTime createTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_account")
	Account idAccount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_auction")
	Auction idAuction;
	
}
