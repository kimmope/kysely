/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Repository
public class DaoBean {
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<DemoUser> getUsers() {
		return jdbcTemplate.query("SELECT * FROM users ORDER BY lastname, firstname", new RowMapper<DemoUser>() {

			public DemoUser mapRow(ResultSet resultSet, int row) throws SQLException {
				return new DemoUser(
						resultSet.getString("username"),
						resultSet.getString("lastname"),
						resultSet.getString("firstname")
						);	
			}
			
		});
	}

}
