package com.student.attendance.dtos;

import java.util.UUID;

import jakarta.validation.constraints.*;

public record CreateSessionRequestDto(
		@NotNull(message = "course_id must not be null")
		UUID course_id,
		
		@NotNull(message = "latitude must not be null")
	    @DecimalMin(value = "-90.0", message = "latitude cannot be less than -90")
	    @DecimalMax(value = "90.0", message = "latitude cannot be greater than 90")
	    double latitude,

	    @NotNull(message = "longitude must not be null")
	    @DecimalMin(value = "-180.0", message = "longitude cannot be less than -180")
	    @DecimalMax(value = "180.0", message = "longitude cannot be greater than 180")
	    double longitude,

	    @Positive(message = "geoRadiusMeters must be greater than 0")
	    double geoRadiusMeters,

	    @Positive(message = "durationMinutes must be greater than 0")
	    int durationMinutes
		) {

}
