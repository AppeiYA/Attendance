package com.student.attendance.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.CourseDto;
import com.student.attendance.dtos.CreateCourseDto;
import com.student.attendance.dtos.UserDto;
import com.student.attendance.dtos.VerifiedUserDto;
import com.student.attendance.exceptions.NotFoundError;
import com.student.attendance.exceptions.UnauthorizedUserException;
import com.student.attendance.models.CoursesEntity;
import com.student.attendance.models.EnrolledEntity;
import com.student.attendance.models.UsersEntity;
import com.student.attendance.repositories.CourseRepository;
import com.student.attendance.repositories.EnrolledRepository;
//import com.student.attendance.dtos.UserRegisterDto;
//import com.student.attendance.exceptions.DuplicateResourceException;
import com.student.attendance.repositories.UserRepository;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final EnrolledRepository enrolledRepository;
	private final UsersMapper usersMapper;
	private final CourseMapper courseMapper;
	
	
	public UserService(UserRepository userRepository, UsersMapper usersMapper, CourseRepository courseRepository,
			CourseMapper courseMapper, EnrolledRepository enrolledRepository) {
		this.userRepository = userRepository;
		this.usersMapper = usersMapper;
		this.courseRepository = courseRepository;
		this.enrolledRepository = enrolledRepository;
		this.courseMapper = courseMapper;
	}
	
	public UserDto GetUser() {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		UsersEntity user = this.userRepository.findUserByEmail(tokenUser.email());
		UserDto responseUser = this.usersMapper.toUser(user);
		return responseUser;
	}
	
	public UserDto getUserByEmail(String email) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(!tokenUser.role().name().equals("instructor")) {
			throw new UnauthorizedUserException("User not authorized");
		}
		UsersEntity user = this.userRepository.findUserByEmail(email);
		if(user == null) {
			throw new NotFoundError("User not found");
		}
		UserDto responseUser = this.usersMapper.toUser(user);
		return responseUser;
	}
	
	public void createCourse(CreateCourseDto payload) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(!tokenUser.role().name().equals("instructor")) {
			throw new UnauthorizedUserException("User not authorized");
		}
		
		CoursesEntity course = this.courseMapper.toCourseEntity(payload, tokenUser.user_id());
		courseRepository.save(course);
	}
	
	public void setEnrollStateForCourse(UUID course_id, boolean canEnroll) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(!tokenUser.role().name().equals("instructor")) {
			throw new UnauthorizedUserException("User not authorized");
		}
		int setEnroll = this.courseRepository.setCanEnroll(course_id, canEnroll, tokenUser.user_id());
		if(setEnroll == 0) {
			throw new UnauthorizedUserException("Not authorized to update course");
		}
	}
	
	public List<CourseDto> getCourses(){
		return courseRepository.findAll().stream()
				.map(courseMapper::toCourseDto)
				.collect(Collectors.toList());
	}
	
	public CourseDto getCourseById(UUID course_id) {
		return courseMapper.toCourseDto(courseRepository.findById(course_id).orElse(new CoursesEntity()));
	}
	
	public void enrollForCourse(UUID course_id) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(!tokenUser.role().name().equals("student")) {
			throw new UnauthorizedUserException("Instructors cannot enroll");
		}
		
		CoursesEntity course = this.courseRepository.findById(course_id)
				.orElseThrow(()->new NotFoundError("Course Not found"));
		UsersEntity user = this.userRepository.findById(tokenUser.user_id())
				.orElseThrow(()->new NotFoundError("User Not found"));
		
//		check if user is already enrolled 
		boolean alreadyEnrolled = enrolledRepository.existsByStudentAndCourse(user, course);
		if (alreadyEnrolled) {
		    throw new IllegalStateException("You are already enrolled in this course");
		}
		
		if(course.isCan_enroll()) {
		EnrolledEntity enroll = new EnrolledEntity(user, course);
		this.enrolledRepository.save(enroll);
		}else {
			throw new UnauthorizedUserException("Enrollment is closed");
		}
	}
	
	public void deleteCourse(UUID course_id) {
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(!tokenUser.role().name().equals("instructor")) {
			throw new UnauthorizedUserException("User not permitted");
		}
		
		CoursesEntity course = courseRepository.findById(course_id)
		        .orElseThrow(() -> new IllegalStateException("Course does not exist"));

		    if (!course.getInstructor().getId().equals(tokenUser.user_id())) {
		        throw new UnauthorizedUserException("You can't delete another instructor’s course");
		    }
		
		    courseRepository.delete(course);
	}
}
