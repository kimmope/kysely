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
		List<DemoUser> users = userClient.getUsers();
		model.addAttribute("users", users);
		return "userlist";
	}
	
	@ExceptionHandler({ConnectException.class})
	public String connectionError() {
		return "connectionError";
	}

}
