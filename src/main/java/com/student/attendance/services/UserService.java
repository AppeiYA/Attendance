package com.student.attendance.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.UserDto;
import com.student.attendance.dtos.VerifiedUserDto;
import com.student.attendance.exceptions.UnauthorizedUserException;
import com.student.attendance.models.UsersEntity;
//import com.student.attendance.dtos.UserRegisterDto;
//import com.student.attendance.exceptions.DuplicateResourceException;
import com.student.attendance.repositories.UserRepository;

@Service
public class UserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final UserRepository userRepository;
	private final UsersMapper usersMapper;
	
	public UserService(UserRepository userRepository, UsersMapper usersMapper) {
		this.userRepository = userRepository;
		this.usersMapper = usersMapper;
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
		log.error(email);
		VerifiedUserDto tokenUser = (VerifiedUserDto) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();
		if(tokenUser.role().name() != "instructor") {
			throw new UnauthorizedUserException("User not authorized");
		}
		UsersEntity user = this.userRepository.findUserByEmail(email);
		if(user == null) {
			throw new IllegalArgumentException("User not exists");
		}
		UserDto responseUser = this.usersMapper.toUser(user);
		return responseUser;
	}
}
