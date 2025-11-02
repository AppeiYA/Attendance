package com.student.attendance.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.student.attendance.models.CoursesEntity;
import com.student.attendance.models.EnrolledEntity;
import com.student.attendance.models.UsersEntity;

public interface EnrolledRepository extends JpaRepository<EnrolledEntity, UUID> {
	 boolean existsByStudentAndCourse(UsersEntity student, CoursesEntity course);
}
