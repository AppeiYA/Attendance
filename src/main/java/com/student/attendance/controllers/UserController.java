package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.attendance.dtos.UserRegisterDto;
import com.student.attendance.services.UserService;

@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserRegisterDto payload) {
		this.userService.registerUser(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "User Registered Successfully");
		res.put("StatusCode", HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
}
