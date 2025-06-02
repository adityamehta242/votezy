package com.aditya.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex)
	{
        logger.warn("Resource not found: {}", ex.getMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value() , ex.getMessage());
		return new  ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex)
	{
        logger.warn("Duplicate resource: {}", ex.getMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value() , ex.getMessage());
		return new  ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(VoteNotAllowException.class)
	public ResponseEntity<ErrorResponse> handleVoteNotAllowException(VoteNotAllowException ex)
	{
		logger.warn("voter are Forbidden to vote {} " , ex.getMessage());
		ErrorResponse error = new ErrorResponse(HttpStatus.FORBIDDEN.value() , ex.getMessage());
		return new  ResponseEntity<>(error,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String , String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String , String > errors = new HashMap<>();
		BindingResult bResult = ex.getBindingResult();
		List<FieldError> errorList = bResult.getFieldErrors();
		for(FieldError error : errorList)
		{
			errors.put(error.getField(), error.getDefaultMessage());
		}
		
		return new ResponseEntity<>(errors , HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() , ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex)
	{
		logger.error("Unexpected error occurred", ex);
		ErrorResponse error  = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() , ex.getMessage());
		return new ResponseEntity<>(error , HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getConstraintViolations().forEach(cv -> {
	        String path = cv.getPropertyPath().toString();
	        String message = cv.getMessage();
	        errors.put(path, message);
	    });
	    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	
}
