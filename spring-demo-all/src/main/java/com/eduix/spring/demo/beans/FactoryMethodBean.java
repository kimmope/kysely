package com.eduix.spring.demo.beans;

public class FactoryMethodBean extends SimpleBean {
	
	public FactoryMethodBean(String data) {
		super(data);
	}

	public static FactoryMethodBean createInstance(String data) {
		return new FactoryMethodBean(data);
	}
	
}
