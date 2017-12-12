/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eduix.spring.demo.beans.DaoBean;
import com.eduix.spring.demo.domain.DemoUser;

public class DemoApplication {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringApplicationContext.xml");
		
		DaoBean dao = context.getBean(DaoBean.class);
		System.out.println("Users:");
		for (DemoUser user : dao.getUsers()) {
			System.out.println(user.getFirstname() + " " + user.getLastname() + " (" + user.getUsername() + ")");
		}
		
		((ConfigurableApplicationContext) context).close();
		
	}

}
