/*
 * Copyright (c) 2017 Eduix Oy
 * All rights reserved
 */
package com.eduix.spring.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eduix.spring.demo.domain.DemoUser;

/**
 * @author Jarkko Leponiemi <jarkko.leponiemi@eduix.fi>
 */
@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<DemoUser> getUsers() {
		return jdbcTemplate.query("SELECT * FROM users ORDER BY lastname, firstname", ROW_MAPPER);
	}

	public DemoUser getUser(String username) {
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", ROW_MAPPER, username);
		} catch (DataAccessException e) {
			return null;
		}
	}
	
	public void addUser(DemoUser user) {
		jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?)", user.getUsername(), user.getFirstname(), user.getLastname());
	}
	public void modUser(DemoUser user) {
		jdbcTemplate.update("UPDATE users SET firstname=?, lastname=? WHERE username=?", user.getFirstname(), user.getLastname(),user.getUsername());
	}
	
	public void delUser(DemoUser user) {
		jdbcTemplate.update("DELETE FROM users WHERE username=? AND firstname=? AND lastname=?", user.getUsername(), user.getFirstname(), user.getLastname());
	}
	
	public void delUser(String username) {
		jdbcTemplate.update("DELETE FROM users WHERE username=?", username);
	}

	private static RowMapper<DemoUser> ROW_MAPPER = new RowMapper<DemoUser>() {

		public DemoUser mapRow(ResultSet resultSet, int row) throws SQLException {
			return new DemoUser(
					resultSet.getString("username"), 
					resultSet.getString("lastname"),
					resultSet.getString("firstname"));
		}

	};

}
