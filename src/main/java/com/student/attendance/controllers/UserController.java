package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.attendance.dtos.CourseDto;
import com.student.attendance.dtos.CreateCourseDto;
import com.student.attendance.dtos.UserDto;
import com.student.attendance.services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
//
//import com.student.attendance.dtos.UserLoginDto;
//import com.student.attendance.dtos.UserRegisterDto;
//import com.student.attendance.services.UserService;
//
//import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/profile/me")
	public ResponseEntity<Map<String, Object>> getUser() {
		UserDto response = this.userService.GetUser();
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<Map<String, Object>> getUserByEmail(
			@Valid
			@Email(message = "Invalid email format")
			@PathVariable String email
			){
		UserDto response = this.userService.getUserByEmail(email);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@PostMapping("/courses")
	public ResponseEntity<Map<String, Object>> createCourse(@Valid @RequestBody CreateCourseDto payload){
		this.userService.createCourse(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Course created successfully");
		res.put("StatusCode", HttpStatus.CREATED.value());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(res);
	}
	
	@PatchMapping("/courses/{courseId}/enroll/{canEnroll}")
	public ResponseEntity<Map<String, Object>> setCourseEnroll(
			@PathVariable UUID courseId,
	        @PathVariable boolean canEnroll){
		
		this.userService.setEnrollStateForCourse(courseId, canEnroll);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Course updated successfully");
		res.put("StatusCode", HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/courses")
	public ResponseEntity<Map<String, Object>> getCourses(){
		List<CourseDto> courses = this.userService.getCourses();
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", courses);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@GetMapping("/courses/{courseId}")
	public ResponseEntity<Map<String, Object>> getCourseById(@PathVariable UUID courseId){
		CourseDto course = this.userService.getCourseById(courseId);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("data", course);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@PostMapping("/courses/{courseId}/enroll")
	public ResponseEntity<Map<String, Object>> enrollForCourse(@PathVariable UUID courseId){
		this.userService.enrollForCourse(courseId);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Enrolled successfully");
		res.put("StatusCode", HttpStatus.OK.value());
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@DeleteMapping("/courses/{courseId}")
	public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable UUID courseId){
		this.userService.deleteCourse(courseId);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Success");
		res.put("StatusCode", HttpStatus.OK);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
		
	}
	
}
