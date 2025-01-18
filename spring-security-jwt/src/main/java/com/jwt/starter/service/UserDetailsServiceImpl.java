package com.jwt.starter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

		Optional<Users> users = userRepository.findByUserName(username);

		if(null != users && users.isPresent()) {
			// If users found in DB, then create authenticate user object
			return users.map(AuthenticatedUser::new).get();
		} else {
			throw new UsernameNotFoundException("User Not Found !!");
		}

	}
}
