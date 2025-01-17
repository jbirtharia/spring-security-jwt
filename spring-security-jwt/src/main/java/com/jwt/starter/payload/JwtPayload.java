package com.jwt.starter.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtPayload {
	
	private String token;
	
	private String createdTime;
	
	private String expirationTime;

}
