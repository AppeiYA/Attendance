package com.student.attendance.dtos;

import java.util.UUID;

import com.student.attendance.models.UsersEntity.UserRole;

import jakarta.validation.constraints.*;


public record UserRegisterDto(
		@NotEmpty(message="first_name must not be empty")
		String first_name,
		
		@NotEmpty(message="last_name must not be empty")
		String last_name,
		
		@NotNull(message="Id no cannot be null")
	    @Positive(message = "unique_id must be a positive number")
		Long unique_id,
		
		@NotNull(message = "department_id must not be null")
		UUID department_id,
		
		UserRole role,
		
		@NotEmpty(message = "email must not be empty")
	    @Email(message = "email must be valid")
		String email,
		
		@NotEmpty(message = "password must not be empty")
	    @Size(min = 6, message = "password must be at least 6 characters long")
		String password
		) {

}
