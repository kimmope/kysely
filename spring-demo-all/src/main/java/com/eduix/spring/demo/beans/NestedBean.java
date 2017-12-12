package com.eduix.spring.demo.beans;

public class NestedBean {
	
	private SimpleBean nested;

	public SimpleBean getNested() {
		return nested;
	}

	public void setNested(SimpleBean nested) {
		this.nested = nested;
	}

}
