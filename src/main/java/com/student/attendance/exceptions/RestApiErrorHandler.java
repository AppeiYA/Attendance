package com.student.attendance.exceptions;

import java.util.Locale;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestApiErrorHandler {
	private static final Logger log = LoggerFactory.getLogger(RestApiErrorHandler.class);
	
	private final MessageSource messageSource;

	public RestApiErrorHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Error> handleIllegalStateException(HttpServletRequest request, IllegalStateException ex,
			Locale locale){
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.ILLEGAL_STATE.getErrCode(), HttpStatus.EXPECTATION_FAILED.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("IllegalStateException at {} {}: {} ", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(NotFoundError.class)
	public ResponseEntity<Error> handleNotFoundError(HttpServletRequest request, NotFoundError ex,
			Locale locale){
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.NOT_FOUND.getErrCode(), HttpStatus.NOT_FOUND.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("NotFoundError at {} {}: {} ", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<Error> handleUnauthorizedUserException(HttpServletRequest request, UnauthorizedUserException ex,
			Locale locale){
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.DATABASE_ERROR.getErrCode(), HttpStatus.UNAUTHORIZED.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("UnauthorizedUserException at {} {}: {} ", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(JwtException.class)
	public ResponseEntity<Error> handleJwtException(HttpServletRequest request, JwtException ex,
			Locale locale){
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.AUTHENTICATION_ERROR.getErrCode(), HttpStatus.FORBIDDEN.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("JwtException at {} {} : {}", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Error> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex,
			Locale locale){
		
		int status = HttpStatus.UNAUTHORIZED.value();
		 // Differentiate errors if you want
	    if ("User not found".equalsIgnoreCase(ex.getMessage())) {
	        status = HttpStatus.NOT_FOUND.value();
	    } else if ("Incorrect password".equalsIgnoreCase(ex.getMessage())) {
	        status = HttpStatus.UNAUTHORIZED.value();
	    }
	    
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.AUTHENTICATION_ERROR.getErrCode(), 
				status)
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("IllegalArgumentException at {} {} : {}", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.valueOf(status));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Error> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex, 
			Locale locale){
		String valErrors = String.join(", ",
				ex.getBindingResult().getAllErrors().stream()
				.map(error -> error.getDefaultMessage())
				.collect(Collectors.toList())
				);
		Error error = ErrorUtils.createError(valErrors, ErrorCode.INVALID_INPUT_ERROR.getErrCode(), HttpStatus.BAD_REQUEST.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("MethodArgumentNotValidException at {} {} : {}", request.getMethod(), request.getRequestURL(), valErrors);
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> handleException(HttpServletRequest request, Exception ex, Locale locale){
		Error error = ErrorUtils.createError(ErrorCode.GENERIC_ERROR.getErrMsgKey(), ErrorCode.GENERIC_ERROR.getErrCode(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("Exception at {} {} : {}", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Error> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
			HttpMediaTypeNotSupportedException ex, Locale locale){
		Error error = ErrorUtils.createError(ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrMsgKey(),
				ErrorCode.HTTP_MEDIATYPE_NOT_SUPPORTED.getErrCode(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
				log.info("HttpMediaTypeNotSupportedException :: request.getMethod(): " + request.getMethod());
				return new ResponseEntity<>(error, HttpStatus. UNSUPPORTED_MEDIA_TYPE);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<Error> handleDuplicateResourceException(HttpServletRequest request, DuplicateResourceException ex,
			Locale locale){
		Error error = ErrorUtils.createError(ex.getMessage(), ErrorCode.DATABASE_ERROR.getErrCode(), HttpStatus.CONFLICT.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("DuplicateResourceException at {} {}: {} ", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Error> handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex, 
			Locale locale){
		Error error = ErrorUtils.createError(ErrorCode.DATABASE_ERROR.getErrMsgKey(), ErrorCode.DATABASE_ERROR.getErrCode(), HttpStatus.BAD_REQUEST.value())
				.setUrl(request.getRequestURL().toString())
				.setReqMethod(request.getMethod());
		log.error("DataIntegrityViolationException at {} {}: {} ", request.getMethod(), request.getRequestURL(), ex.getMessage());
		
		HttpStatus status = ex.getCause() instanceof ConstraintViolationException
				? HttpStatus.CONFLICT : HttpStatus.INTERNAL_SERVER_ERROR;
		
		return new ResponseEntity<>(error, status);
	}
	
}
