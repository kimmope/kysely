/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Component
public class UserClient {

	@Autowired
	RestTemplate restTemplate;

	public List<DemoUser> getUsers() {
		DemoUser[] users = restTemplate.getForObject("/users", DemoUser[].class);
		return Arrays.asList(users);
	}
	
	public DemoUser getUser(String name) {
		System.out.println("webclientgetuser");
		String s = "/user/" + name; 
		System.out.println(s);
		DemoUser u = restTemplate.getForObject(s, DemoUser.class);
		return u;
	}
	
	public void addUser(DemoUser user) {
		System.out.println("webclientadduser");
		restTemplate.put("/user", user);
	}
	public void modUser(DemoUser user) {
		System.out.println("webclientmoduser");
		restTemplate.put("/mod", user);
	}
	
	public void del(String name) {
		System.out.println("webclientdel");
		String s = "/destroy/" + name; 
		System.out.println(s);
		restTemplate.delete(s);
		
	}
}
