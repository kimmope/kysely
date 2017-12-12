/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
//@Configuration
//@ComponentScan(basePackages="com.eduix.spring.demo")
//@PropertySource("classpath:application.properties")
public class ApplicationConfig2 {
	
//	@Autowired
//	private Environment env;
//	
//	@Bean
//	public DataSource dataSource() {
//		return new DriverManagerDataSource(env.getProperty("jdbc.url"), env.getProperty("jdbc.username"), env.getProperty("jdbc.password"));
//	}

}
