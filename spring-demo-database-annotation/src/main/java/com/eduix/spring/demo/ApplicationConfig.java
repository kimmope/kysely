/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Configuration
@ComponentScan(basePackages="com.eduix.spring.demo")
@ImportResource("classpath:properties-config.xml")
public class ApplicationConfig {
	
	@Value("${jdbc.url}")
	private String url;
	
	@Value("${jdbc.username}")
	private String username;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Bean
	public DataSource dataSource() {
		return new DriverManagerDataSource(url, username, password);
	}

}
