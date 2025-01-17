package com.jwt.starter.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jwt.starter.payload.AuthenticationExceptionPayload;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class AuthenticationExceptionHandler {
	
	@ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<AuthenticationExceptionPayload> handleAuthenticationException(AuthenticationException ex) {
		
		log.error("Authentication has been failed - {}", ex.getMessage());
        
        AuthenticationExceptionPayload authenticationExceptionPayload = AuthenticationExceptionPayload.builder()
        		.status(HttpStatus.UNAUTHORIZED.value()).error("Authentication failed").message(ex.getMessage())
        		.timestamp(LocalDateTime.now()).build();

        return new ResponseEntity<>(authenticationExceptionPayload, HttpStatus.UNAUTHORIZED);
    }

}
