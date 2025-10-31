package com.student.attendance.dtos;

import java.util.UUID;

import com.student.attendance.models.UsersEntity.UserRole;

public record VerifiedUserDto(
		UserRole role,
		String email,
		UUID user_id
		) {

}
