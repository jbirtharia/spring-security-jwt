package com.jwt.starter.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jwt.starter.security.AuthenticatedUser;

public class SecurityContextHolderUtil {
	
	public static AuthenticatedUser getAuthenticatedUser() {
		
		return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
