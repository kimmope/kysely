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

import com.eduix.spring.demo.beans.SomeBean;

public class DemoApplication {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringApplicationContext.xml");
		
		SomeBean someBean = context.getBean(SomeBean.class);
		System.out.println("String = " + someBean.getStringProperty() + ", Int=" + someBean.getIntProperty());
		
		((ConfigurableApplicationContext) context).close();
		
	}

}
