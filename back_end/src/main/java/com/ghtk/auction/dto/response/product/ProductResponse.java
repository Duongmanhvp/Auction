package com.ghtk.auction.dto.response.product;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.ghtk.auction.enums.ProductCategory;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
	String owner;
	
	String name;
	
	ProductCategory category;
	
	String description;
	
	String image;
	
	String buyer;
	
}
