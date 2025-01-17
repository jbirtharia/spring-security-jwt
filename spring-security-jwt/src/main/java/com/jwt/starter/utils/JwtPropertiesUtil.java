package com.jwt.starter.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtPropertiesUtil {
	
	@Value("${jwt.secret.key}")
	private String secretKey;

    private static JwtPropertiesUtil instance;

    public JwtPropertiesUtil() {
    	instance = this;
    }

    public static String getSecretKey() {
        return instance.secretKey;
    }
}
