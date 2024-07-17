package com.ghtk.Auction.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
	
	@NotEmpty
	@Email(message = "Email is not valid")
	String email;
	
	@NotEmpty
	@Size(min = 8, message = "Password is not valid")
	String password;

	String fullName;
	
//	@JsonFormat(pattern = "dd//MM/yyyy HH:mm:ss")
	LocalDate dateOfBirth;
}
