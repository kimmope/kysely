/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.permitAll()
				.and()
			.logout()
				.permitAll();
		
		
		/*http.authorizeRequests()
      .antMatchers("/", "/home").access("hasRole('USER')")
      .antMatchers("/admin/**").hasRole("ADMIN")
      .and()
      // some more method calls
      .formLogin();
		*/
		
	}
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("admin").password("{noop}admin").roles("ADMIN");
		
		//auth.inMemoryAuthentication().withUser("user")        .password("password").roles("USER");
	}
	//public userDetailsService userDetailsService() {
		
	//}

}
