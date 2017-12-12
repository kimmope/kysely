/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo2.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Service
public class ServiceBean {
	
	private static final Logger log = LoggerFactory.getLogger(ServiceBean.class);

	@Autowired
	private DaoBean dao;
	
	public String getData() {
		log.info("Getting demo data from service");
		return dao.getData();
	}
}
