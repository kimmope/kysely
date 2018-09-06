/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.controller;

import java.net.ConnectException;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduix.spring.demo.client.UserClient;
import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Controller
public class UserController {
	
	@Autowired
	private UserClient userClient;
	
	@RequestMapping("/userspage")
	public String getUsers(Model model) {
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
/**	WORKING
	@RequestMapping("/users")
	public String getUsers(Model model) {
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "users";
	}	
*/
/** TRAINING CODE-- */
	@RequestMapping("/user/{username}")
	public String getUser(Model model, @PathVariable("username") String username) {
		DemoUser user = userClient.getUser(username);
		model.addAttribute("user", user);
		return "user";
	}	
/** --TRAINING CODE */
	
	/** NOT TESTED-- 
	
	@PutMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody DemoUser user) {
		dao.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	 --TRAINING CODE */		
	
	@ExceptionHandler({ConnectException.class})
	public String connectionError() {
		return "connectionError";
	}

}
