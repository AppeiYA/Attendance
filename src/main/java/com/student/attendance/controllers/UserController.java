package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.attendance.dtos.UserDto;
import com.student.attendance.services.UserService;
//
//import com.student.attendance.dtos.UserLoginDto;
//import com.student.attendance.dtos.UserRegisterDto;
//import com.student.attendance.services.UserService;
//
//import jakarta.validation.Valid;

@RestController
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/api/users/me")
	public ResponseEntity<Map<String, Object>> getUser() {
		UserDto response = this.userService.GetUser();
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/api/users/{email}")
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable String email){
		UserDto response = this.userService.getUserByEmail(email);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
