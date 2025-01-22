package com.jwt.starter.controllers;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jwt.starter.utils.JwtPropertiesUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
@AutoConfigureMockMvc
public class JournalControllerWithAuthenticationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String AUTHENTICATED_USER_USERNAME = "Sachin";
	private static final long EXPIRATION_TIME = 60000; 


	@Test
	@WithMockUser
	public void testSecuredEndpointWithUser() throws Exception {

		objectMapper.registerModule(new JavaTimeModule());
		// Act
		ResultActions result = mockMvc.perform(get("/journals")
				.header("Authorization", "Bearer " + jwtHeader()));

		// Assert
		result.andExpect(status().isOk())
		.andExpect(authenticated());
	}

	private String jwtHeader(){

		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		SecretKey key = Keys.hmacShaKeyFor(JwtPropertiesUtil.getSecretKey().getBytes(StandardCharsets.UTF_8));
		return Jwts.builder()
				.setSubject(AUTHENTICATED_USER_USERNAME)
				.setHeader(headers)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

}
