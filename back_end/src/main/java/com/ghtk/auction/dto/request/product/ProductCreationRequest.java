package com.ghtk.auction.dto.request.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
	@NotBlank
	String name;
	
	@NotEmpty
	String category;
	
	@NotEmpty
	String description;
	
	@NotEmpty
	String image;
	
}
