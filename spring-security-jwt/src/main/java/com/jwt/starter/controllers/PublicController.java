package com.jwt.starter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.starter.entities.Users;
import com.jwt.starter.payload.JwtPayload;
import com.jwt.starter.security.AuthenticatedUser;
import com.jwt.starter.service.UserDetailsServiceImpl;
import com.jwt.starter.service.UserService;
import com.jwt.starter.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@SecurityRequirements
    @Operation(summary = "To create a new user", description = "Returns user object after creation")
	@PostMapping("/signup")
	public ResponseEntity<Users> createUser(@RequestBody Users users){

		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(users));
	}
	
	@SecurityRequirements
    @Operation(summary = "To generate JWT token to access all endpoints", description = "Returns JWT token after authentication")
	@PostMapping("/login")
	public ResponseEntity<JwtPayload> login(@RequestBody Users users){
				
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(users.getUserName(), users.getPassword()));
		AuthenticatedUser authenticatedUser = (AuthenticatedUser) userDetailsServiceImpl.loadUserByUsername(users.getUserName());
		log.info("JWT token generated for username - {}", authenticatedUser.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(jwtUtil.generateToken(authenticatedUser));
	}

	@SecurityRequirements
	@Operation(summary = "To get list of users", description = "Returns user list")
	@PostMapping("/users")
	public ResponseEntity<List<Users>> getUsers(){

		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}
}
