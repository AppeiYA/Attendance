package com.student.attendance.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.*;

import com.student.attendance.models.CoursesEntity;


public interface CourseRepository extends JpaRepository<CoursesEntity, UUID> {

	@Transactional
	@Modifying
	@Query(
	    value = "UPDATE courses SET can_enroll = :canEnroll WHERE id = :courseId AND instructor_id = :instructorId",
	    nativeQuery = true
	)
	int setCanEnroll(@Param("courseId") UUID courseId, 
			@Param("canEnroll") boolean canEnroll, 
			@Param("instructorId") UUID instructorId);
	
	@Transactional
	@Modifying 
	@Query(
			value = "DELETE FROM courses c WHERE c.id = :courseId AND c.instructor_id = :instructorId",
			nativeQuery = true
			)
	int deleteCourseByInstructor(@Param("instructorId") UUID instructorId, @Param("courseId") UUID courseId);
	
	
}
