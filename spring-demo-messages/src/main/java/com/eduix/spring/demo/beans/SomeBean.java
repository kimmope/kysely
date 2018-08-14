/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
public class SomeBean {
	
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public void doSomething() {
		String message = messageSource.getMessage("message", null, new Locale(""));
		System.out.println(message);
		message = messageSource.getMessage("unknown", null, "This is default from SomeBean.java", null);
		System.out.println(message);
		message = messageSource.getMessage("message", null, new Locale("en"));
		System.out.println(message);
		message = messageSource.getMessage("message", null, new Locale("fi"));
		System.out.println(message);		
		
		String error = messageSource.getMessage("error", new Object[] {this.getClass().getName(), "forced error"}, null);
		System.out.println(error);
	}
}