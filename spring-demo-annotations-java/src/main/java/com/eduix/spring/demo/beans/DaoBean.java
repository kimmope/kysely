/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DaoBean {
	
	private static final Log log = LogFactory.getLog(DaoBean.class);
	
	public String getData() {
		log.info("Getting demo data from dao");
		return "Demo data";
	}

}
