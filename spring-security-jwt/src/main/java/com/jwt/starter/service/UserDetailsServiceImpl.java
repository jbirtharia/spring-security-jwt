package com.jwt.starter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.starter.entities.Users;
import com.jwt.starter.repository.UserRepository;
import com.jwt.starter.security.AuthenticatedUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users users = userRepository.findByUserName(username);

		if(null != users) {
			return createAutheticatedUser(users);
		} else {
			throw new UsernameNotFoundException("User Not Found !!");
		}

	}
	
	private UserDetails createAutheticatedUser(Users users) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		users.getRoles().stream().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
		return 
				new AuthenticatedUser(users.getUserName(), users.getPassword(), true, true, true, true, authorities, users);
	}

}
