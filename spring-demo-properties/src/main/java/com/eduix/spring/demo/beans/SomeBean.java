/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
public class SomeBean {
	
	private String stringProperty;
	private Integer intProperty;
	private Boolean booleanProperty;
	
	public String getStringProperty() {
		return stringProperty;
	}
	public void setStringProperty(String stringProperty) {
		this.stringProperty = stringProperty;
	}
	public Integer getIntProperty() {
		return intProperty;
	}
	public void setIntProperty(Integer intProperty) {
		this.intProperty = intProperty;
	}
	public Boolean getBooleanProperty() {
		return booleanProperty;
	}
	public void setBooleanProperty(Boolean booleanProperty) {
		this.booleanProperty = booleanProperty;
	}	
}
