package com.student.attendance.dtos;

import jakarta.validation.constraints.*;

public record UserLoginDto(
		@NotEmpty(message="email cannot be empty")
		@Email(message="email must be valid")
		String email,
		
		@NotEmpty(message="password cannot be empty")
		@Size(min = 6, message = "password must be at least 6 characters long")
		String password
		) {

}
