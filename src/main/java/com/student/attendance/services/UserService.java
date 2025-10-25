package com.student.attendance.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.UserRegisterDto;
import com.student.attendance.repositories.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UsersMapper usersMapper;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UsersMapper usersMapper) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.usersMapper = usersMapper;
	}
	
	public void registerUser(UserRegisterDto payload) {
		var newUser = usersMapper.toUser(payload);
		String hashedPassword = passwordEncoder.encode(newUser.getPassword());
		newUser.setPassword(hashedPassword);
		userRepository.save(newUser);
	}
}
