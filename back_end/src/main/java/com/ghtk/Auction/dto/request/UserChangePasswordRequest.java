package com.ghtk.Auction.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserChangePasswordRequest {
	
//	String email;
	
	@NotEmpty(message = "Old password is required")
	String oldPassword;
	
	@NotEmpty(message = "New password is required")
	@Size(min = 8, message = "New password must be at least 8 characters long")
	String newPassword;
}
