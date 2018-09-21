/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.controller;

import java.net.ConnectException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eduix.spring.demo.client.UserClient;
import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Controller
public class UserController {
	
	@Autowired
	private UserClient userClient;
	
	@RequestMapping("/users")
	public String getUsers(Model model) {
		System.out.println("webcontrolusers");
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
	
	@RequestMapping("/user/{username}")
	public String getUser(@PathVariable String username, Model model) {
		System.out.println("webcontroluser");
		DemoUser u = userClient.getUser(username);
		System.out.println(u.getFirstname());
		model.addAttribute("user", u);
		return "userinfo";
	}
	
	@RequestMapping("/add")
	public String addUser() {
		System.out.println("webcontroladd");
		return "add";
	}
	
	@PostMapping("/add")
	public String addUser(Model model, DemoUser user) {
		System.out.println("webcontroladdpost");
		userClient.addUser(user);
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
	@PostMapping("/mod")
	public String modUser(Model model, DemoUser user) {
		System.out.println("webcontrolmod");
		userClient.modUser(user);
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
	@RequestMapping("/modifyf/{username}")
	public String modForm(@PathVariable String username, Model model) {
		System.out.println("webcontrolmf");
		DemoUser u = userClient.getUser(username);
		System.out.println(u.getFirstname());
		model.addAttribute("user", u);
		return "mod";
	}
	
	@PostMapping("/del/{username}")
	public String delUser(@PathVariable String username, Model model) {
		System.out.println("webcontroldel");
		userClient.del(username);
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
	
	@ExceptionHandler({ConnectException.class})
	public String connectionError() {
		return "connectionError";
	}

}
