package com.eduix.spring.demo.dao;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eduix.spring.demo.domain.DemoUser;

import Rowmappers.QuestionRowMapper;
//import Rowmappers.QuestionRowMapper;
import queta.Question;

@Repository
public class UserDao {

// DEBUG LOGGER:	private static final Log log = LogFactory.getLog(UserDao.class); // Change part "UserDao" according to used class name
// LOG TO PUT INSIDE CLASS: log.info("HERE DEBUG TEXT");
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static RowMapper<DemoUser> ROW_MAPPER = new RowMapper<DemoUser>() {
		public DemoUser mapRow(ResultSet resultSet, int row) throws SQLException {
			return new DemoUser(
					resultSet.getString("username"), 
					resultSet.getString("lastname"),
					resultSet.getString("firstname"));
		}
	};	
		
// ROWMAPPER INFO: https://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
	
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

/** TRAINING CODE: ------------------ */	
	public void editUser(DemoUser user) {
		jdbcTemplate.update("UPDATE users SET username=?, firstname=?, lastname=? WHERE username=?", user.getUsername(), user.getFirstname(), user.getLastname(), user.getUsername());
	}	

	public void deleteUser(String id) {
		jdbcTemplate.update("DELETE FROM users WHERE username=?",id);
	}	

/** OWN PROJECT: ------------------ */	
//  BeanPropertyRowMapper sample	
//	public Question getQuestionById(int qid) {
//		try {
//			String sql = "SELECT * FROM questions WHERE qid=?";
//			Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, new BeanPropertyRowMapper<>(Question.class));
//			return question;
//		} catch (DataAccessException e) {
//			return null;
//		}
//	}
	public Question getQuestionById(int qid){		 
		String sql = "SELECT * FROM questions WHERE qid = ?";	 
		Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, new QuestionRowMapper());			
		return question;
	}
}