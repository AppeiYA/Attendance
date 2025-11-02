package com.student.attendance.dtos;

import java.util.UUID;

public record CourseDto(
		UUID course_id,
		String course_name,
		Integer units,
		Integer semester
		) {

}
