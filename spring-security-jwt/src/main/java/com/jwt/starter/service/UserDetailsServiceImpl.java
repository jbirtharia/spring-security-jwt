package com.jwt.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.starter.repository.UserRepository;
import com.jwt.starter.security.AuthenticatedUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUserName(username)
				.map(AuthenticatedUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found !!"));
	}
}
