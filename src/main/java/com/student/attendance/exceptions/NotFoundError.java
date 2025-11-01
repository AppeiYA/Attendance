package com.student.attendance.exceptions;

public class NotFoundError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundError(String message) {
		super(message);
	}
}
