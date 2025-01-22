package com.jwt.starter.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.starter.service.UserDetailsServiceImpl;
import com.jwt.starter.utils.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter  extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil; // Utility class for JWT operations

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			// Extract JWT from Authorization header
			final String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				filterChain.doFilter(request, response); // If no token, continue the filter chain
				return;
			}

			// Authenticating user
			authenticateUserWithJwtToken(request, authHeader);

			// Continue with the filter chain
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException ex) {
			log.error("Token expired - {}", ex.getMessage());
			sendErrorResponse(response, ex);
		}

	}

	private void authenticateUserWithJwtToken(HttpServletRequest request, final String authHeader) {
		
		final String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix
		final String username = jwtUtil.extractUsername(jwtToken); // Extract username from token

		// If username is not null and not authenticated yet
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

			// Validate the token and set the authentication
			if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				// Set the userDetails object in SecurityContextHolder
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
			} 
		}
	}
	
	private void sendErrorResponse(HttpServletResponse response, ExpiredJwtException ex) throws IOException {
		// Handle expired token exception
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\":\"JWT Token has expired\",\"message\":\"" + ex.getMessage() + "\"}");
	}
}
