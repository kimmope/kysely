/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.core.io.Resource;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
public class ResourceBean {
	
	private Resource lorem;

	public Resource getLorem() {
		return lorem;
	}

	public void setLorem(Resource lorem) {
		this.lorem = lorem;
	}
	
	public void print() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(lorem.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
