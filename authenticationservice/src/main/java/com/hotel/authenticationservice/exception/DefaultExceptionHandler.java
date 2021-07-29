package com.hotel.authenticationservice.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@RestControllerAdvice
@EnableWebMvc
public class DefaultExceptionHandler {
	
	private static Logger logger=LoggerFactory.getLogger(DefaultExceptionHandler.class);
	
	 @ExceptionHandler(value = UserIncorrectException.class)
	  public ResponseEntity<ErrorMessage> resourceNotFoundException(UserIncorrectException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),400,"Bad Request",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	  
	    ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage()));
	  
	    return errors;
	 }
	 
	 @ExceptionHandler(value = UserNameIsNullException.class)
	  public ResponseEntity<ErrorMessage> noUsernameException(UserIncorrectException ex, HttpServletRequest  request) {
	    ErrorMessage message = new ErrorMessage(
	        new Date(),400,"Bad Request",
	        ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }
	 
	 @ExceptionHandler(value = Exception.class)
	  public ResponseEntity<ErrorMessage> genericException(Exception ex, HttpServletRequest request) {
		 logger.error("Some error occured"+ex.getMessage());
	    ErrorMessage message = new ErrorMessage(
	        new Date(),500,"Internal Error",
	        "Some Exception Occured"+ex.getMessage(),request.getRequestURI());
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	 
}
