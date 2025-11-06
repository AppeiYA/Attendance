package com.student.attendance.services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.CreateSessionRequestDto;
import com.student.attendance.dtos.VerifiedUserDto;
import com.student.attendance.exceptions.NotFoundError;
import com.student.attendance.exceptions.UnauthorizedUserException;
import com.student.attendance.models.AttendanceSession;
import com.student.attendance.models.CoursesEntity;
import com.student.attendance.models.UsersEntity;
import com.student.attendance.repositories.AttendanceSessionRepository;
import com.student.attendance.repositories.CourseRepository;
import com.student.attendance.repositories.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AttendanceSessionService {
	private final AttendanceSessionRepository sessionRepo;
	private final UserRepository userRepo;
	private final CourseRepository courseRepo;
	private final UsersMapper userMapper;
	private final EntityManager entityManager;
	public AttendanceSessionService(AttendanceSessionRepository sessionRepo, UserRepository userRepo,
			CourseRepository courseRepo, UsersMapper userMapper, EntityManager entityManager) {
		this.sessionRepo = sessionRepo;
		this.userRepo = userRepo;
		this.courseRepo = courseRepo;
		this.userMapper = userMapper;
		this.entityManager = entityManager;
	}
	
	@Transactional
	public AttendanceSession createSession(CreateSessionRequestDto request) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		
		CoursesEntity course = courseRepo.findById(request.course_id())
		        .orElseThrow(() -> new NotFoundError("Course not found"));
		
		if (!course.getInstructor().getId().equals(tokenUser.user_id())) {
	        throw new UnauthorizedUserException("You are not the creator of this course");
	    }
		
		UsersEntity user = entityManager.getReference(UsersEntity.class, tokenUser.user_id());
		
		LocalDateTime start = LocalDateTime.now();
	    LocalDateTime end = start.plusMinutes(request.durationMinutes());

	    AttendanceSession session = new AttendanceSession();
	    session.setCourse(course);
	    session.setInitiator(user);
	    session.setLatitude(request.latitude());
	    session.setLongitude(request.longitude());
	    session.setGeoRadiusMeters(request.geoRadiusMeters());
	    session.setStartTime(start);
	    session.setEndTime(end);

	    return sessionRepo.save(session);
	}
}
