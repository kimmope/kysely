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

import com.eduix.spring.demo.beans.DependentBean;
import com.eduix.spring.demo.beans.SimpleBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DemoApplication {

	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("SpringApplicationContext.xml");

		try {
			dump(context.getBean("noBean"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
		
		dump(context.getBean("simpleBean1"));
		
		dump(context.getBean("simpleBean"));
		
		dump(context.getBean("simpleBean2"));
		
		try {
			dump(context.getBean(SimpleBean.class));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println();
		}
		
		dump(context.getBeansOfType(SimpleBean.class));
		
		dump(context.getBean("simpleBean4", "Simple instance 1"));
		
		dump(context.getBean("simpleBean4"));
		
		dump(context.getBean("listBean"));
		
		dump(context.getBean("nestedBean"));
		
		dump(context.getBean("initMethodBean"));
		
		dump(context.getBean("factoryMethodBean"));
		
		dump(context.getBean("factoryCreated1"));
		
		dump(context.getBean("factoryCreated2"));
		
		DependentBean dep1 = context.getBean("dependentBean1", DependentBean.class);
		dump(dep1);
		System.out.println(dep1.getData());
		System.out.println();
		
		DependentBean dep2 = context.getBean("dependentBean2", DependentBean.class);
		dump(dep2);
		System.out.println(dep2.getData());
		System.out.println();
		
		dump(context.getBean("autowireBean1"));
		
		dump(context.getBean("autowireBean2"));

		((ConfigurableApplicationContext) context).close();

	}

	public static void dump(Object object) {
		if (object == null) {
			System.out.println("null");
		} else {
			System.out.println(object.toString() + ":");
			System.out.println(gson.toJson(object));
		}
		System.out.println();
	}

}
