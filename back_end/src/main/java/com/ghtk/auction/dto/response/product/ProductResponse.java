package com.ghtk.auction.dto.response.product;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
	String owner;
	
	String name;
	
	String category;
	
	String description;
	
	String image;
	
}
