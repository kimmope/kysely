/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.domain;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
public class DemoUser {

	private String username;
	private String lastname;
	private String firstname;

	public DemoUser() {}
	
	public DemoUser(String username, String lastname, String firstname) {
		super();
		this.username = username;
		this.lastname = lastname;
		this.firstname = firstname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

}
