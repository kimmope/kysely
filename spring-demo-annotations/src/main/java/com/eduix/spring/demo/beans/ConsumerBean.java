/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerBean {
	
//	private static final Log log = LogFactory.getLog(ConsumerBean.class);

	private ServiceBean service;
	
	public String getData() {
		return service.getData("from ConsumerBean");
	}

	@Autowired
	public void setService(ServiceBean service) {
		this.service = service;
	}		
}
