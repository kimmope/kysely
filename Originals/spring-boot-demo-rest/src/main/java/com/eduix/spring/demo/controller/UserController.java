/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduix.spring.demo.dao.UserDao;
import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@RestController
public class UserController {

	@Autowired
	private UserDao dao;
	
	@GetMapping("/users")
	public List<DemoUser> getUsers() {
		System.out.println("restcontrolusers");
		return dao.getUsers();
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<DemoUser> getUser(@PathVariable("username") String username) {
		System.out.println("restcontroluserusername");
		DemoUser user = dao.getUser(username);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody DemoUser user) {
		dao.addUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	}
	@PutMapping("/mod")
	public ResponseEntity<?> modUser(@RequestBody DemoUser user) {
		dao.modUser(user);
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getUsername()).toUri();
		URI location = ServletUriComponentsBuilder.fromPath("/user").path("/{username}").buildAndExpand(user.getUsername()).toUri();
		System.out.println("##### "+location.getPath());
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/destroy/{username}")
	//public ResponseEntity<?> deleteUser(@RequestBody DemoUser user) {
	public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
		System.out.println("restcontroldestroy");
		dao.delUser(username);
		
		return ResponseEntity.ok().build();
	}

	
}
