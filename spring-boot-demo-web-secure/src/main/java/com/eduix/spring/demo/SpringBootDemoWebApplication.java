/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@SpringBootApplication
public class SpringBootDemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoWebApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder,
			@Value("${user.service.rest.uri}") String userServiceUri,
			@Value("${user.service.rest.user}") String user,
			@Value("${user.service.rest.password}") String password) {
		return builder
				.rootUri(userServiceUri)
				.basicAuthorization(user, password)
				.build();
	}
}
