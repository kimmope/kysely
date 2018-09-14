package com.eduix.spring.demo.dao;

// 
import org.apache.commons.logging.Log;
// 
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.eduix.spring.demo.domain.DemoUser;

import Rowmappers.QuestionRowMapper;
import queta.Answer;
//import Rowmappers.QuestionRowMapper;
import queta.Question;

@Repository
public class UserDao {

// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserDao.class); // Change part "UserDao" according to used class name
// LOG TO PUT INSIDE CLASS:	log.info("!******** REST Dao answer.getUid()+answer.getQid()+answer.getAnswer()(): "+answer.getUid()+" "+answer.getQid()+" "+answer.getAnswer());
	
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

// OWN PROJECT:
// TEST
	public Question getNotAskedQuestion(int uid){	// create database queries for creating not-asked question for question-page
		String notAsked = "SELECT qid FROM questions WHERE qid NOT IN (SELECT qid FROM user_answers WHERE uid = ?) LIMIT 1";	// Query for which question is not asked yet from user
		String qid = (String)jdbcTemplate.queryForObject(notAsked, new Object[]{uid}, String.class); // Saving query-result to object-variable
		log.info("!******** REST Dao uid = " + uid + " qid = " + qid +" not asked = " + notAsked);
		String sql = "SELECT * FROM questions WHERE qid = ?";				// Select question which qid is not asked
		log.info("!******** REST Dao uid = " + uid + " qid = " + qid +" not asked = " + notAsked);
		Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, new QuestionRowMapper());			
		return question;
	}
// TEST
	public Question getQuestionById(int qid){		 			// create database query for creating question for question-page++
		log.info("!******** REST Dao qid = "+qid);
		String sql = "SELECT * FROM questions WHERE qid = ?";	 // kysymysmerkkiin haetaan ilmeisesti arvo seuraavan rivin new Object[]{qid}lla?
		Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, new QuestionRowMapper());			
		return question;
	}
	public void addAnswer(Answer answer) {
		jdbcTemplate.update("INSERT INTO user_answers(uid,qid,answer) VALUES (?,?,?)", answer.getUid(), answer.getQid(), answer.getAnswer());
		jdbcTemplate.update("UPDATE useres SET amount_answers = amount_answers + ?",1);
	}
}

//BeanPropertyRowMapper sample	
//public Question getQuestionById(int qid) {
//	try {
//		String sql = "SELECT * FROM questions WHERE qid=?";
//		Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, new BeanPropertyRowMapper<>(Question.class));
//		return question;
//	} catch (DataAccessException e) {
//		return null;
//	}
//}