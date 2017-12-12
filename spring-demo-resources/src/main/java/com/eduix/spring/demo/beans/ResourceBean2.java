/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
public class ResourceBean2 implements ResourceLoaderAware {
	
	private ResourceLoader resourceLoader;
	
	public void print() {
		Resource lorem = resourceLoader.getResource("texts/lorem.txt");
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

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
