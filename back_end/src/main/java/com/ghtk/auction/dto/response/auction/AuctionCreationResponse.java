package com.ghtk.auction.dto.response.auction;


import com.ghtk.auction.enums.AuctionStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuctionCreationResponse {
	
	Long product_id;
	
	String title;
	
	String description;
	
	Long start_bid;
	
	Long price_per_step;
	
	LocalDateTime created_at;
	
	AuctionStatus status;
	
}
