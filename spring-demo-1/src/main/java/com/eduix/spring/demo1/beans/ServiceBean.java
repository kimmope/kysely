/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo1.beans;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServiceBean {
	
	private static final Log log = LogFactory.getLog(ServiceBean.class);
	
	private DaoBean dao;

	public void setDao(DaoBean dao) {
		this.dao = dao;
	}
	
	public String getData() {
		log.info("Getting demo data from service");
		return dao.getData();
	}

}
