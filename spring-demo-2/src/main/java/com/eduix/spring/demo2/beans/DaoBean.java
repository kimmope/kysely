/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo2.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Repository
public class DaoBean {
	
	private static final Logger log = LoggerFactory.getLogger(DaoBean.class);
	
	public String getData() {
		log.info("Getting demo data from dao");
		return "Demo data";
	}

}
