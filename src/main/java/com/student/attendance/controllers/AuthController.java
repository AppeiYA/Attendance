package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.student.attendance.dtos.LoginResponseDto;
import com.student.attendance.dtos.UserLoginDto;
import com.student.attendance.dtos.UserRegisterDto;
import com.student.attendance.services.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/api/auth/register")
	public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody UserRegisterDto payload) {
		this.authService.registerUser(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "User Registered Successfully");
		res.put("StatusCode", HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	@PostMapping("/api/auth/login")
	public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginDto payload){
		LoginResponseDto response = this.authService.login(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Login successful");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
