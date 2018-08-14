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

import com.eduix.spring.demo.beans.ResourceBean;
import com.eduix.spring.demo.beans.ResourceBean2;

public class DemoApplication {
	
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringApplicationContext.xml");
		
		ResourceBean resourceBean = context.getBean(ResourceBean.class);
		resourceBean.print();
		
		ResourceBean2 resourceBean2 = context.getBean(ResourceBean2.class);
		resourceBean2.print();
		
		ResourceBean3 resourceBean3 = context.getBean(ResourceBean3.class);
		resourceBean3.print();
		
		((ConfigurableApplicationContext) context).close();
		
	}

}
