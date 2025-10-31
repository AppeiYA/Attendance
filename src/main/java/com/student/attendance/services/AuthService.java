package com.student.attendance.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.student.attendance.dtos.LoginResponseDto;
import com.student.attendance.dtos.UserLoginDto;
import com.student.attendance.dtos.UserRegisterDto;
import com.student.attendance.exceptions.DuplicateResourceException;
import com.student.attendance.models.UsersEntity;
import com.student.attendance.repositories.UserRepository;
import com.student.attendance.utils.JwtUtils;

@Service
public class AuthService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UsersMapper usersMapper;
	private final JwtUtils jwtUtils;
//	private static final Logger log = LoggerFactory.getLogger(AuthService.class);

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, 
			UsersMapper usersMapper, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.usersMapper = usersMapper;
		this.jwtUtils = jwtUtils;
	}
	
	public void registerUser(UserRegisterDto payload) {
		try{
			var newUser = usersMapper.toUser(payload);
			String hashedPassword = passwordEncoder.encode(newUser.getPassword());
			newUser.setPassword(hashedPassword);
			userRepository.save(newUser);
		}catch(DataIntegrityViolationException ex) {
			throw  new DuplicateResourceException("Email already exists");
		}	
	}
	
	public LoginResponseDto login(UserLoginDto payload) {
//			check if email exists 
			UsersEntity user = userRepository.findUserByEmail(payload.email());
			if(user == null) {
				throw new IllegalArgumentException("User not found");
			}
			if(!passwordEncoder.matches(payload.password(), user.getPassword())) {
				throw new IllegalArgumentException("Incorrect password")
;			}
			
//			successful login then generate token
			String token = jwtUtils.generateToken(user.getEmail(), user.getId(), user.getRole());
			
			LoginResponseDto response = new LoginResponseDto(user.getEmail(), user.getMatric_no(), user.getFirst_name(),
					user.getLast_name(), token);
			return response;
	}
	
}
