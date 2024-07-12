package com.til.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
	@Value("${cors.allowed.origin}")
	private String ALLOWED_ORIGIN_URL;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins(ALLOWED_ORIGIN_URL)
					.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}
}
