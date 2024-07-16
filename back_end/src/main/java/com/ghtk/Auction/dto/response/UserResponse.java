package com.ghtk.Auction.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
	
	String email;
	String password;
	String phone;
	Boolean isVerified;
	
}
