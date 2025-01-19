package com.jwt.starter.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
	    info = @Info(title = "Journal's API", version = "1.0", description = "API Documentation with JWT"),
	    security = @SecurityRequirement(name = "BearerAuth")
	)
	@SecurityScheme(
	    name = "BearerAuth",
	    scheme = "bearer",
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT"
	)
public class SwaggerConfig {

	@Bean
    GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("journal-api")
                .pathsToMatch("/public/**", "/journals/**", "/info/**")
                .build();
    }
	
	@Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v3/api-docs/**")
                        .allowedOrigins("http://localhost:9000") // Adjust origin as needed
                        .allowedMethods("GET", "POST", "OPTIONS");
            }
        };
    }
}
