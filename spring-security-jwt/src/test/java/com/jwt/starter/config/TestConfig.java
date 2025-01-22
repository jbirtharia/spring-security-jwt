package com.jwt.starter.config;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.jwt.starter.entities.AppInfo;
import com.jwt.starter.repository.JournalRepository;
import com.jwt.starter.repository.UserRepository;
import com.jwt.starter.service.JournalService;
import com.jwt.starter.service.RedisService;
import com.jwt.starter.service.UserDetailsServiceImpl;
import com.jwt.starter.utils.JwtUtil;

@TestConfiguration
public class TestConfig {
	
	 @Mock
	 private ValueOperations<String, AppInfo> valueOperations;

	 @Bean
	 RedisService redisService() {
		return Mockito.mock(RedisService.class);
	 }
	 
	 @Bean
	 JwtUtil jwtUtil() {
	    return Mockito.mock(JwtUtil.class); // Create a mock instance of JwtUtil
	 }
	 
	 @Bean
	 UserDetailsServiceImpl userDetailsServiceImpl() {
		 return Mockito.mock(UserDetailsServiceImpl.class);
	 }
	 
	 @Bean
	 RedisTemplate<?, ?> redisTemplate() {
		 return Mockito.mock(RedisTemplate.class);
	 }
	 
	 @Bean
	 UserRepository userRepository() {
		 return Mockito.mock(UserRepository.class);
	 }
	 
	 @Bean
	 JournalService journalService() {
		 return Mockito.mock(JournalService.class);
	 }
	 
	 @Bean
	 JournalRepository journalRepository() {
		 return Mockito.mock(JournalRepository.class);
	 }
}
