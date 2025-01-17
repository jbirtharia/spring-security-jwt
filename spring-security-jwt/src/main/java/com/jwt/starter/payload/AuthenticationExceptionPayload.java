package com.jwt.starter.payload;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticationExceptionPayload {
	
	private Integer status;
	
	private String error;
	
	private String message;
	
	private LocalDateTime timestamp;

}
