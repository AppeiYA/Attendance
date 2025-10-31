package com.student.attendance.dtos;


import com.student.attendance.models.UsersEntity.UserRole;

public record UserDto(
		String first_name,
		String last_name,
		String email,
		Long unique_id,
		String department_name,
		String faculty_name,
		UserRole role
		) {

}
