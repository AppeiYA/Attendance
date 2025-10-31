package com.student.attendance.exceptions;

public class UnauthorizedUserException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedUserException(String message) {
		super(message);
	}
}
