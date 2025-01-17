package com.jwt.starter.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserNotFoundExceptionPayload {
	
	private String message;

    private boolean success;

    private HttpStatus httpStatus;

}
