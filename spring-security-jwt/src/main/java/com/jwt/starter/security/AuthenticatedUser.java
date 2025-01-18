package com.jwt.starter.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jwt.starter.entities.Users;

public class AuthenticatedUser extends User{

	private static final long serialVersionUID = 1L;
	
	private Users users;

	public AuthenticatedUser(Users users) {
		super(users.getUserName(), users.getPassword(), true, true, true, true, getAuthorities(users));
		this.setUsers(users);
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}
	
	private static List<SimpleGrantedAuthority> getAuthorities(Users users){
		return 
				users.getRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).toList();
	}

}
