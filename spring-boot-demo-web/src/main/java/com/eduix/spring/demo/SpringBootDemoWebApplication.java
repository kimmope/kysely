package com.eduix.spring.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication														// Same as @Configuration (for class that defines beans), @EnableAutoConfiguration (Configures default configurations such as database connection), @ConponentScan (scans the package where the where the application is located)
public class SpringBootDemoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoWebApplication.class, args);	// Calls run
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder,			// Tekee beanin jota rest-puoli käyttää
			@Value("${user.service.rest.uri}") String userServiceUri) {		// user.service.rest.uri hakee application.propertiesista localhostin urin
		return builder.rootUri(userServiceUri).build();						// palauttaa sisäisellä builderilla muokatun urin 
	}
}
