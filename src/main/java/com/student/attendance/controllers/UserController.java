package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.attendance.dtos.CreateCourseDto;
import com.student.attendance.dtos.UserDto;
import com.student.attendance.services.UserService;

import jakarta.validation.Valid;
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
	
	@PostMapping("/api/users/courses")
	public ResponseEntity<Map<String, Object>> createCourse(@Valid @RequestBody CreateCourseDto payload){
		this.userService.createCourse(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Course created successfully");
		res.put("StatusCode", HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	@PatchMapping("/api/users/courses/{courseId}/enroll/{canEnroll}")
	public ResponseEntity<Map<String, Object>> setCourseEnroll(
			@PathVariable UUID courseId,
	        @PathVariable boolean canEnroll){
		
		this.userService.setEnrollStateForCourse(courseId, canEnroll);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Course updated successfully");
		res.put("StatusCode", HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
}
