package com.ghtk.auction.dto.request.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserForgetPasswordRequest {
	
	@NotEmpty
	@Email(message = "Email is not valid")
	String email;
	
	//String phone;
}
