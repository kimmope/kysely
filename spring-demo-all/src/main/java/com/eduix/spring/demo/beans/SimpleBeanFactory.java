package com.eduix.spring.demo.beans;

public class SimpleBeanFactory {
	
	private int id = 1;
	
	public SimpleBean createSimpleBean() {
		return new SimpleBean("Factory created id " + id++);
	}

}
