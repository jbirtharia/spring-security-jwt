package com.jwt.starter.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.jwt.starter.entities.Users;
import com.jwt.starter.security.AuthenticatedUser;

public class SecurityContextHolderUtil {


	public static AuthenticatedUser getAuthenticatedUser() {

		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {

			setSecurityContext();
		}
		return (AuthenticatedUser) 
				SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	private static void setSecurityContext() {
		User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Users users = Users.builder().userName(loggedInUser.getUsername())
				.password(loggedInUser.getPassword()).roles(loggedInUser.getAuthorities().stream()
						.map(g -> g.getAuthority().substring(5)).toList()).build();
		AuthenticatedUser authenticatedUser = new AuthenticatedUser(users);

		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken
				(authenticatedUser, null,authenticatedUser.getAuthorities()));
	}

}
