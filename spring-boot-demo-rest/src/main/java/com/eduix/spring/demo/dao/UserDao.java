package com.eduix.spring.demo.dao;

// 
import org.apache.commons.logging.Log;
// 
import org.apache.commons.logging.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.eduix.spring.demo.domain.DemoUser;
import Rowmappers.UserRowMapper;
//import Rowmappers.QuestionRowMapper;
import queta.Answer;
import queta.PastQandA;
import queta.Question;
import queta.User;

@Repository
public class UserDao {	// DAO = DATA ACCESS OBJECT
// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserDao.class); // Change part "UserDao" according to used class name
// LOG TO PUT INSIDE CLASS:	log.info("!******** REST Dao answer.getUid()+answer.getQid()+answer.getAnswer()(): "+answer.getUid()+" "+answer.getQid()+" "+answer.getAnswer());
	@Autowired
	private JdbcTemplate jdbcTemplate;

// OWN PROJECT:	
// GET NEW QUESTION
	private static RowMapper<Question> QUESTION_ROW_MAPPER = new RowMapper<Question>(){	// Builds query-results into lists
		public Question mapRow(ResultSet resultSet, int rowNum) throws SQLException {	// 
			return new Question(
				resultSet.getInt("qid"),
				resultSet.getString("question"));
		}
	};
	public Question getNotAskedQuestion(int uid){					// Create database queries for creating not-asked question for question-page
		Question question = new Question();
		String notAsked = "SELECT IFNULL((SELECT qid FROM questions WHERE qid NOT IN (SELECT qid FROM user_answers WHERE uid = ?) LIMIT 1),'noNewQuestions')";	// Query for which question is not asked yet from user
		String qid = (String)jdbcTemplate.queryForObject(notAsked, String.class, uid); // Saving query-result to object-variable instead of list
		if (qid.equals("noNewQuestions")) {							// Checking if there are any questions left which user has not answered
			question.setQid(0);										// Asettaa tyhjän olion qid:ksi nollan. Tulkitaan webin usercontrollerissa.
		}
		else {
			question = (Question)jdbcTemplate.queryForObject("SELECT * FROM questions WHERE qid = ?", QUESTION_ROW_MAPPER,qid);	// Select question which qid is not asked
		}
		return question;
	}
// GET QUESTION ID
	public Question getQuestionById(int qid){		 				// create database query for creating question for question-page
		String sql = "SELECT * FROM questions WHERE qid = ?";	 	// kysymysmerkkiin haetaan arvo seuraavan rivin new Object[]{qid}lla
		Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, QUESTION_ROW_MAPPER);	
		return question;
	}
	
// ADD USER'S ANSWER DATA TO DATABASES	
	public void addAnswer(Answer answer) {
		log.info("!******** REST Dao checkUser username: " + answer.getUid() + answer.getQid() + answer.getAnswer());
		jdbcTemplate.update("INSERT INTO user_answers(uid,qid,answer) VALUES (?,?,?)", answer.getUid(), answer.getQid(), answer.getAnswer());
		jdbcTemplate.update("UPDATE useres SET amount_answers = (SELECT COUNT(*) FROM user_answers WHERE uid = useres.uid) WHERE uid = ?",answer.getUid());
		jdbcTemplate.update("UPDATE questions SET amount_answs = amount_answs + 1 WHERE qid = ?",answer.getQid());
	}
	
// CHECK IF USER EXISTS OR CREATE NEW IF IT DOESNT
	public User checkUser(String username) {
		log.info("!******** REST Dao checkUser username: " + username);
		try {
			return jdbcTemplate.queryForObject("SELECT * FROM useres WHERE username=?", new UserRowMapper(), username);
		}
		catch(EmptyResultDataAccessException e){
			throw new RuntimeException(e);			// Needs to be changed to RuntimeException for UserController
		}
	}
	public User createNewUser(String username) {
		log.info("!******** REST Dao createNewUser1");
		jdbcTemplate.update("INSERT INTO useres(username,amount_answers,score) VALUES (?, ?, ?)",username,0,0);
		return jdbcTemplate.queryForObject("SELECT * FROM useres WHERE username=?", new UserRowMapper(), username);
	}

	private static RowMapper<User> ROW_MAPPER_3 = new RowMapper<User>(){
		public User mapRow(ResultSet resultSet, int row) throws SQLException {
			return new User(
			resultSet.getInt("uid"),
			resultSet.getString("username"),
			resultSet.getInt("amount_answers"),
			resultSet.getInt("score"));
		}
	};
	public User getUser(int uid) {
		return jdbcTemplate.queryForObject("SELECT * FROM useres WHERE uid=?", ROW_MAPPER_3, uid);
	}	
	
// IF OLD QUESTIONS FOR THE USER EXIST RETURN THEM IN LIST OR ELSE RETURN NULL
// TESTING PAGE-SPECIFIC OBJECT CREATED FROM DATA OF DIFFERENT DATABASES
	private static RowMapper<PastQandA> ROW_MAPPER_4 = new RowMapper<PastQandA>() {			// Creates new RowMapper-list-object for returning the list of queried PastQandAs
		public PastQandA mapRow(ResultSet resultSet, int row) throws SQLException {
			return new PastQandA(
				resultSet.getInt("uid"),
				resultSet.getInt("qid"),					
				resultSet.getString("question"),
				resultSet.getTimestamp("time_of_answ"),				
				resultSet.getString("answer"));
		}
	};	
	public List<PastQandA> getPastQandAs(int uid) {
		return jdbcTemplate.query("SELECT u.uid, u.qid, q.question, u.time_of_answ, u.answer FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? ORDER BY u.time_of_answ DESC",ROW_MAPPER_4,uid);	// List queryllä 
	}
	public PastQandA getPastQandA(int uid, int qid) {
		return jdbcTemplate.queryForObject("SELECT u.uid, u.qid, q.question, u.time_of_answ, u.answer FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? AND q.qid=? ORDER BY u.time_of_answ DESC",ROW_MAPPER_4,uid,qid);	//	Objekti queryForObjectillä
	}	
}

// BeanPropertyRowMapper ei toiminut koska getJdbcTemplatea ei tunnistettu
//public List<OldQetA> getUsersOldAnswers(int uid){
//String sql = "SELECT * FROM user_answers WHERE uid = ?";
//List<OldQetA> oldQetAs = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(OldQetA.class),uid);
//return oldQetAs;
//}


//ROWMAPPER INFO: https://www.mkyong.com/spring/spring-jdbctemplate-querying-examples/
//	private static RowMapper<DemoUser> ROW_MAPPER = new RowMapper<DemoUser>() {			// Creates new RowMapper-list-object for returning the list of queried DemoUsers
//		public DemoUser mapRow(ResultSet resultSet, int row) throws SQLException {
//			return new DemoUser(
//				resultSet.getString("username"),
//				resultSet.getString("lastname"),
//				resultSet.getString("firstname"));
//		}
//	};
//	public List<DemoUser> getUsers() {
//		return jdbcTemplate.query("SELECT * FROM users ORDER BY lastname, firstname", ROW_MAPPER);	// Tekee queryn mukaisen listan demousereista
//	}
//	public DemoUser getUser(String username) {
//		return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", ROW_MAPPER, username);	// Tekee ROW_MAPPER-listan yhdestä rivistä olion
//	}
//	public DemoUser getUser(String username) {
//		try {
//			return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username=?", ROW_MAPPER, username);	// Tekee ROW_MAPPER-listan yhdestä rivistä olion
//		} catch (DataAccessException e) {
//			return null;
//		}
//	}		
//	public void addUser(DemoUser user) {
//		jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?)", user.getUsername(), user.getFirstname(), user.getLastname());
//	}
///** TRAINING CODE: ------------------ */	
//	public void editUser(DemoUser user) {
//		jdbcTemplate.update("UPDATE users SET username=?, firstname=?, lastname=? WHERE username=?", user.getUsername(), user.getFirstname(), user.getLastname(), user.getUsername());
//	}	
//	public void deleteUser(String id) {
//		jdbcTemplate.update("DELETE FROM users WHERE username=?",id);
//	}	
	