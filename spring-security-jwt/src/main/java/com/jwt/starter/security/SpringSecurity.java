package com.jwt.starter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.starter.filter.JwtFilter;
import com.jwt.starter.service.UserDetailsServiceImpl;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SpringSecurity {
	
	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private UserDetailsServiceImpl userDetailService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	 return http.authorizeHttpRequests(request -> request
                 .requestMatchers("/public/**","/swagger-ui/**",
                		 "/v3/api-docs/**","/swagger-ui.html").permitAll()	// Allow Swagger
                 .requestMatchers(HttpMethod.GET, "/info/**").permitAll()
                 .anyRequest().authenticated())
         .csrf(AbstractHttpConfigurer::disable)
         .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
         .build();
    }
    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

}
