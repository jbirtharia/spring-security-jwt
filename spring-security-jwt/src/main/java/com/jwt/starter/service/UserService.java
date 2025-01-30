package com.jwt.starter.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jwt.starter.entities.Users;
import com.jwt.starter.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Transactional
	public Users createUser(Users users) {
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		users.setRoles(List.of("USER"));
		return userRepository.save(users);
	}

	public List<Users> getAllUsers(){
		return userRepository.findAll();
	}
}
