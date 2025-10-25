package com.student.attendance.dtos;

import java.util.UUID;

import com.student.attendance.models.UsersEntity.UserRole;


public record UserRegisterDto(
		String first_name,
		String last_name,
		Long matric_no,
		UUID department_id,
		UserRole role,
		String email,
		String password
		) {

}
