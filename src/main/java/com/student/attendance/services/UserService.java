package com.student.attendance.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.CreateCourseDto;
import com.student.attendance.dtos.UserDto;
import com.student.attendance.dtos.VerifiedUserDto;
import com.student.attendance.exceptions.NotFoundError;
import com.student.attendance.exceptions.UnauthorizedUserException;
import com.student.attendance.models.CoursesEntity;
import com.student.attendance.models.UsersEntity;
import com.student.attendance.repositories.CourseRepository;
//import com.student.attendance.dtos.UserRegisterDto;
//import com.student.attendance.exceptions.DuplicateResourceException;
import com.student.attendance.repositories.UserRepository;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final CourseRepository courseRepository;
	private final UsersMapper usersMapper;
	private final CourseMapper courseMapper;
	
	
	public UserService(UserRepository userRepository, UsersMapper usersMapper, CourseRepository courseRepository,
			CourseMapper courseMapper) {
		this.userRepository = userRepository;
		this.usersMapper = usersMapper;
		this.courseRepository = courseRepository;
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
}
