/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduix.spring.demo2.beans.ServiceBean;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@RestController
public class WebController {
	
	@Autowired
	private ServiceBean service;
	
	@RequestMapping("/")
	public String index() {
		return service.getData();
	}

}
