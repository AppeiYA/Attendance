package com.student.attendance.dtos;

import jakarta.validation.constraints.*;

public record CreateCourseDto(
		@NotNull(message="course_name cannot be empty")
		@NotEmpty(message="course_name cannot be empty")
		String course_name,
		
		
		@NotNull(message = "units cannot be null")
	    @Min(value = 2, message = "units must be at least 2")
	    @Max(value = 4, message = "units cannot exceed 4")
	    Integer units,

	    @NotNull(message = "semester cannot be null")
	    @Min(value = 1, message = "semester must be either 1 or 2")
	    @Max(value = 2, message = "semester must be either 1 or 2")
	    Integer semester
		) {

}
