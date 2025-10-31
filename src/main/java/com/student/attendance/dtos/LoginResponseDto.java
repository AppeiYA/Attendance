package com.student.attendance.dtos;

public record LoginResponseDto(
		String email,
		Long unique_id,
		String first_name,
		String last_name,
		String token
		) {

}
