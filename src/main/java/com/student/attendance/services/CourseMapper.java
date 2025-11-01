package com.student.attendance.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.student.attendance.dtos.CreateCourseDto;
import com.student.attendance.models.CoursesEntity;
import com.student.attendance.models.UsersEntity;

@Service
public class CourseMapper {

	public CoursesEntity toCourseEntity(CreateCourseDto dto, UUID id) {
		CoursesEntity course = new CoursesEntity();
		course.setName(dto.course_name());
		course.setUnits(dto.units());
		course.setSemester(dto.semester());
		
		UsersEntity instructor = new UsersEntity();
		instructor.setId(id);
		
		course.setInstructor(instructor);
		
		return course;
	}
}
