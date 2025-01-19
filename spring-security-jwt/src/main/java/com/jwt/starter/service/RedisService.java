package com.jwt.starter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@Service
public class RedisService {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// Method to get an object from Redis
	@SneakyThrows
	public <T> T getObject(String key, Class<T> entityClass) {
		
		Object obj  = redisTemplate.opsForValue().get(key);
		// If object is null
		if (obj == null) {
            return null;
        }
		// De-serialize JSON to object
		return objectMapper.readValue(obj.toString(), entityClass);
	}
	
	// Method to set an object into Redis
	@SneakyThrows
    public <T> void setObject(String key, T value) {
        if (value == null) {
            throw new IllegalArgumentException("Value to store in Redis cannot be null");
        }
        // Serialize object to JSON
        String json = objectMapper.writeValueAsString(value);
        // Store JSON in Redis
        redisTemplate.opsForValue().set(key, json);
    }
}
