package com.jwt.starter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jwt.starter.payload.UserNotFoundExceptionPayload;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@Hidden
public class UserNotFoundExceptionHandler {
	
	 @ExceptionHandler(UsernameNotFoundException.class)
	    public ResponseEntity<UserNotFoundExceptionPayload> handlerResourceNotFoundException(UsernameNotFoundException ex){
		 	log.error("User not found !!");
		 	log.error("Detailed exception message - {}", ex);
	        return new ResponseEntity<UserNotFoundExceptionPayload>(UserNotFoundExceptionPayload
	        		.builder().message("User Not Found. Please Check Username or Password.").success(false)
	                .httpStatus(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
	    }
}
