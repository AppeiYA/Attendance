package com.student.attendance.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.student.attendance.dtos.CreateSessionRequestDto;
import com.student.attendance.models.AttendanceSession;
import com.student.attendance.services.AttendanceSessionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sessions")
public class AttendanceSessionController {
	private final AttendanceSessionService attService;

	public AttendanceSessionController(AttendanceSessionService attService) {
		this.attService = attService;
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> createSession(
			@Valid @RequestBody CreateSessionRequestDto payload
			){
		AttendanceSession response = this.attService.createSession(payload);
		
		Map<String, Object> res = new HashMap<>();
		res.put("message", "Session Created Successfully");
		res.put("data", response);
		
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

}
