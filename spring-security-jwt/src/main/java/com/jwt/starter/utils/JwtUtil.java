package com.jwt.starter.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.jwt.starter.payload.JwtPayload;
import com.jwt.starter.security.AuthenticatedUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {


    // Token expiration time (e.g., 1 hour)
    private static final long EXPIRATION_TIME = 60000; //3600000; // 1 hour in milliseconds


	public JwtPayload generateToken(AuthenticatedUser authenticatedUser) {
		
		 // Define custom headers
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT"); // Optional: Default is JWT
        headers.put("alg", "HS256"); // Algorithm used for signing
        Date timeInMilliSeconds = new Date(System.currentTimeMillis());
		String token = createToken(authenticatedUser, headers, timeInMilliSeconds);
		return JwtPayload.builder().token(token).createdTime(timeInMilliSeconds.toString())
				.expirationTime(new Date(System.currentTimeMillis() + EXPIRATION_TIME).toString()).build();
	}

	private String createToken(AuthenticatedUser authenticatedUser, Map<String, Object> headers, Date timeInMilliSeconds) {
		
		// Signing the secret key for JWT
		SecretKey key = Keys.hmacShaKeyFor(JwtPropertiesUtil.getSecretKey().getBytes(StandardCharsets.UTF_8));
		return Jwts.builder()
                .setClaims(createClaims(authenticatedUser)) // Add claims
                .setSubject(authenticatedUser.getUsername()) // Set subject
                .setHeader(headers)	// Add custom headers
                .setIssuedAt(timeInMilliSeconds) // Set issued time
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set expiration time
                .signWith(key, SignatureAlgorithm.HS256) // Sign the token with the secret key
                .compact(); // Build the token
	}
	
	private Map<String, Object> createClaims(AuthenticatedUser authenticatedUser){
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", authenticatedUser.getUsers().getEmail());
		return claims;
	}
	
	// Extract username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(JwtPropertiesUtil.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    
    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
