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

import com.eduix.spring.demo.beans.ServiceBean;

public class DemoApplication {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringApplicationContext.xml");
		
		ServiceBean service = context.getBean(ServiceBean.class);
		
		System.out.println(service.getData("from DemoApplication"));
		
		((ConfigurableApplicationContext) context).close();
		
	}

}