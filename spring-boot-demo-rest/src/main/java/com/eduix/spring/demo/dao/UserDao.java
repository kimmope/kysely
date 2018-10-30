package com.eduix.spring.demo.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import Rowmappers.QuestionRowMapper;
import Rowmappers.UserRowMapper;
import queta.Answer;
import queta.AnswerStats;
import queta.PastQandA;
import queta.Question;
import queta.User;
//import org.springframework.dao.DataAccessException;

@Repository
public class UserDao {	// DAO = DATA ACCESS OBJECT

// DEBUG LOGGER:	
private static final Log log = LogFactory.getLog(UserDao.class); // Change part "UserDao" according to used class name
// LOG TO PUT INSIDE CLASS:	log.info("!******** REST Dao answer.getUid()+answer.getQid()+answer.getAnswer()(): "+answer.getUid()+" "+answer.getQid()+" "+answer.getAnswer());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Question getNotAskedQuestion(int uid){	// Create database queries for creating not-asked question for question-page
		Question question = new Question(0,"");		// Asettaa tyhjän olion qid:ksi nollan. Tulkitaan webin usercontrollerissa.
		log.info("!******** REST Dao getNotAskedQuestion 1 uid : " + uid);
		String notAsked = "SELECT IFNULL((SELECT qid FROM questions WHERE qid NOT IN (SELECT qid FROM user_answers WHERE uid = ?) LIMIT 1),0)";	// Query for which question is not asked yet from user
		log.info("!******** REST Dao getNotAskedQuestion 2 notAsked : " + notAsked);
		int qid = (int)jdbcTemplate.queryForObject(notAsked, Integer.class, uid); // Saving query-result to object-variable instead of list
		log.info("!******** REST Dao getNotAskedQuestion 3 qid : " + qid);
		if (qid != 0) {								// Checking if there are any questions left which user has not answered
			question = (Question)jdbcTemplate.queryForObject("SELECT * FROM questions WHERE qid = ?", QuestionRowMapper.questionRowMapper,qid);	// Select question which qid is not asked
			log.info("!******** REST Dao getNotAskedQuestion 4 in if-loop question : " + question);
		}
		return question;
	}
	
// GET QUESTION BY ITS ID
	public Question getQuestionById(int qid){		 				// create database query for creating question for question-page
		log.info("!******** REST Dao getQuestionById 1 qid : " + qid);
		Question question = (Question)jdbcTemplate.queryForObject("SELECT * FROM questions WHERE qid = ?", QuestionRowMapper.questionRowMapper, qid);	
		return question;
	}	

// ADD USER'S ANSWER DATA TO DATABASES AND CALCULATE RESPECTIVE STATISTICS TO THEM
	public void addAnswer(Answer answer) {
		log.info("!******** REST DAO addAnswer 1");
		jdbcTemplate.update("INSERT INTO user_answers(uid,qid,answer1) VALUES (?,?,?)", answer.getUid(), answer.getQid(), answer.getAnswer1());
		log.info("!******** REST DAO addAnswer 2 uid ja answer1: " + answer.getUid() + answer.getAnswer1());
		jdbcTemplate.update("UPDATE users SET amntUserAnsw = (SELECT COUNT(*) FROM user_answers WHERE uid = users.uid) WHERE uid = ?",answer.getUid());
		log.info("!******** REST DAO addAnswer 3");
		jdbcTemplate.update("UPDATE questions SET amntAnswTot = amntAnswTot + 1 WHERE qid = ?",answer.getQid());
		log.info("!******** REST DAO addAnswer 4");
		int type = (int)jdbcTemplate.queryForObject("SELECT type FROM questions WHERE qid = ?", Integer.class, answer.getQid());
		if (type == 1 || type == 2) {
			log.info("!******** REST DAO addAnswer 5");
			double avg = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(answer1),0) FROM user_answers WHERE qid = ?", Double.class, answer.getQid());
			log.info("!******** REST DAO addAnswer 6 average created average : " + avg);
			jdbcTemplate.update("UPDATE questions SET average = ? WHERE qid = ?",avg,answer.getQid());
		}
		else if(type == 3) {
			log.info("!******** REST DAO addAnswer 7 answer.getAnswer1() : " + answer.getAnswer1());
			if (answer.getAnswer1().equals("v1")){
				log.info("!******** REST DAO addAnswer 7 adding v1");
				jdbcTemplate.update("UPDATE questions SET amntAnswVal1 = amntAnswVal1 + 1 WHERE qid = ?",answer.getQid());	
			}
			else if (answer.getAnswer1().equals("v2")) {
				log.info("!******** REST DAO addAnswer 7 adding v2");
				jdbcTemplate.update("UPDATE questions SET amntAnswVal2 = amntAnswVal2 + 1 WHERE qid = ?",answer.getQid());	
			}
			else if (answer.getAnswer1().equals("v3")) {
				jdbcTemplate.update("UPDATE questions SET amntAnswVal3 = amntAnswVal3 + 1 WHERE qid = ?",answer.getQid());	
			}
			else if (answer.getAnswer1().equals("v4")) {
				jdbcTemplate.update("UPDATE questions SET amntAnswVal4 = amntAnswVal4 + 1 WHERE qid = ?",answer.getQid());	
			}
			else if (answer.getAnswer1().equals("v5")) {
				jdbcTemplate.update("UPDATE questions SET amntAnswVal5 = amntAnswVal5 + 1 WHERE qid = ?",answer.getQid());	
			}			
		}
		log.info("!******** REST DAO addAnswer end");
}
	
// CHECK IF USER EXISTS OR CREATE NEW IF IT DOESNT
	public User checkUser(String username) {
		try {
			log.info("!******** REST Dao try checkUser username : " + username);
			return jdbcTemplate.queryForObject("SELECT users.*, provinces.province FROM users JOIN provinces USING(pid) WHERE username=?", new UserRowMapper(), username);
		}
		catch(EmptyResultDataAccessException e){
			log.info("!******** REST Dao catch checkUser username : " + username);
			throw new RuntimeException(e);			// Needs to be changed to RuntimeException for UserController
		}
	}
	public User createNewUser(User user) {
		log.info("!******** REST Dao createNewUser username : " + user.getUsername() + " pid: " + user.getPid() );
		jdbcTemplate.update("INSERT INTO users(username,pid,amntUserAnsw,score) VALUES (?, ?, ?, ?)",user.getUsername(),user.getPid(),0,0);
		return jdbcTemplate.queryForObject("SELECT users.*, provinces.province FROM users JOIN provinces USING(pid) WHERE username=?", new UserRowMapper(), user.getUsername());
	}

	public User getUser(int uid) {
		return jdbcTemplate.queryForObject("SELECT users.*, provinces.province FROM users JOIN provinces USING(pid) WHERE uid=?", new UserRowMapper(), uid);
	}	
	
// IF OLD QUESTIONS FOR THE USER EXIST RETURN THEM IN LIST OR ELSE RETURN NULL
	private static RowMapper<PastQandA> ROW_MAPPER_4 = new RowMapper<PastQandA>() {			// Creates new RowMapper-list-object for returning the list of queried PastQandAs
		public PastQandA mapRow(ResultSet resultSet, int row) throws SQLException {
			return new PastQandA(
				resultSet.getInt("uid"),
				resultSet.getInt("qid"),					
				resultSet.getString("question"),
				resultSet.getTimestamp("timeOfAnswer"),				
				resultSet.getString("answer1"));
		}
	};	
	public List<PastQandA> getPastQandAs(int uid) {			// Hae lista aikaisemmista kysymys-vastauspareista
		return jdbcTemplate.query("SELECT u.uid, u.qid, q.question, u.timeOfAnswer, u.answer1 FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? ORDER BY u.timeOfAnswer DESC",ROW_MAPPER_4,uid);	// List queryllä 
	}
	public PastQandA getPastQandA(int uid, int qid) {		// Hae yksi kysymys-vastauspari
		return jdbcTemplate.queryForObject("SELECT u.uid, u.qid, q.question, u.timeOfAnswer, u.answer1 FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? AND q.qid=? ORDER BY u.timeOfAnswer DESC",ROW_MAPPER_4,uid,qid);	//	Objekti queryForObjectillä
	}
	
// CALCULATING FIGURES FOR ANSWER STATISTICS-CLASS
	public AnswerStats getAnswerStats(int qid) {
		int amountTot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ?",Integer.class,qid);
		int amountP1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979",Integer.class,qid);
		int amountP2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980",Integer.class,qid);
		int amountP3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981",Integer.class,qid);
		int amountP4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982",Integer.class,qid);
		int amountP5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983",Integer.class,qid);
		double meanAll = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(answer1),0) FROM user_answers WHERE qid = ?", Double.class, qid);
		double meanP1 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979", Double.class, qid);
		double meanP2 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980", Double.class, qid);
		double meanP3 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981", Double.class, qid);
		double meanP4 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982", Double.class, qid);
		double meanP5 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983", Double.class, qid);
		double mediAll = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(answer1),0) FROM user_answers WHERE qid = ?", Double.class, qid);
		double mediP1 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979", Double.class, qid);
		double mediP2 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980", Double.class, qid);
		double mediP3 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981", Double.class, qid);
		double mediP4 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982", Double.class, qid);
		double mediP5 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983", Double.class, qid);
		double modeAll = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(answer1),0) FROM user_answers WHERE qid = ?", Double.class, qid);
		double modeP1 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979", Double.class, qid);
		double modeP2 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980", Double.class, qid);
		double modeP3 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981", Double.class, qid);
		double modeP4 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982", Double.class, qid);
		double modeP5 = (double)jdbcTemplate.queryForObject("SELECT ROUND(AVG(ua.answer1),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983", Double.class, qid);
		int class1Tot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO01'",Integer.class,qid);
		int class1P1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 979",Integer.class,qid);
		int class1P2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 980",Integer.class,qid);
		int class1P3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 981",Integer.class,qid);
		int class1P4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 982",Integer.class,qid);
		int class1P5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 983",Integer.class,qid);
		int class2Tot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO02'",Integer.class,qid);
		int class2P1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 979",Integer.class,qid);
		int class2P2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 980",Integer.class,qid);
		int class2P3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 981",Integer.class,qid);
		int class2P4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 982",Integer.class,qid);
		int class2P5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 983",Integer.class,qid);
		int class3Tot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO03'",Integer.class,qid);
		int class3P1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 979",Integer.class,qid);
		int class3P2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 980",Integer.class,qid);
		int class3P3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 981",Integer.class,qid);
		int class3P4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 982",Integer.class,qid);
		int class3P5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 983",Integer.class,qid);
		int class4Tot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO04'",Integer.class,qid);
		int class4P1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 979",Integer.class,qid);
		int class4P2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 980",Integer.class,qid);
		int class4P3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 981",Integer.class,qid);
		int class4P4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 982",Integer.class,qid);
		int class4P5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 983",Integer.class,qid);
		int class5Tot = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO05'",Integer.class,qid);
		int class5P1 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 979",Integer.class,qid);
		int class5P2 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 980",Integer.class,qid);
		int class5P3 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 981",Integer.class,qid);
		int class5P4 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 982",Integer.class,qid);
		int class5P5 = (int)jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 983",Integer.class,qid);
		AnswerStats AnswerStats = new AnswerStats(amountTot, amountP1, amountP2, amountP3, amountP4, amountP5, meanAll, meanP1, meanP2, meanP3, meanP4, meanP5, mediAll, mediP1, mediP2, mediP3, mediP4, mediP5, modeAll, modeP1, modeP2, modeP3, modeP4, modeP5, class1Tot, class1P1, class1P2, class1P3, class1P4, class1P5, class2Tot, class2P1, class2P2, class2P3, class2P4, class2P5, class3Tot, class3P1, class3P2, class3P3, class3P4, class3P5, class4Tot, class4P1, class4P2, class4P3, class4P4, class4P5,
				class5Tot, class5P1, class5P2, class5P3, class5P4, class5P5);
		return AnswerStats;
	}
	
// PREVENTING RESUBMISSION OF THE FORM
	public boolean checkIfAnswered(int uid, int qid) {
		try{
			jdbcTemplate.queryForObject("SELECT answer1 FROM user_answers WHERE uid = ? AND QID = ?",String.class, uid,qid);
			return true;
		}
		catch(EmptyResultDataAccessException e){
			return false;
		}
	}
	
}

//!!!! VOIKO TÄMÄN KANSSA KÄYTTÄÄ MYÖS UserRowMapper.java:a???!!!!!!!
//GET EXISTING USER 
//	private static RowMapper<User> ROW_MAPPER_3 = new RowMapper<User>(){
//		public User mapRow(ResultSet resultSet, int row) throws SQLException {
//			return new User(
//			resultSet.getInt("uid"),
//			resultSet.getString("username"),
//			resultSet.getInt("amntUserAnsw"),
//			resultSet.getInt("score"));
//		}
//	};

//GET NEW QUESTION OLD WORKING
//	private static RowMapper<Question> QUESTION_ROW_MAPPER = new RowMapper<Question>(){	// Builds query-results into lists
//		public Question mapRow(ResultSet resultSet, int rowNum) throws SQLException {	// 
//			return new Question(
//				resultSet.getInt("qid"),
//				resultSet.getString("question"));
//		}
//	};

// EN KEKSINYT MITEN OLIOON SAA TIETOA MUUALTA KUIN KANNASTA, MUUTENKIN ALKOI VAIKUTTAMAAN HANKALALTA
// PALATTIIN KANTAKOHTAISIIN OLIOIHIN KUNNES YHDISTELMÄT / MUUSTA MATERIAALISTA KOOSTETTAVAT TULEVAT VÄLTTÄMÄTTÖMIKSI
//public Question2 getNotAskedQuestion(int uid){	// Create database queries for creating not-asked question for question-page
//	log.info("!******** REST Dao 1 uid : " + uid);
//	String notAsked = "SELECT IFNULL((SELECT qid FROM questions WHERE qid NOT IN (SELECT qid FROM user_answers WHERE uid = ?) LIMIT 1),0)";	// Query for which question is not asked yet from user
//	int qid = (int)jdbcTemplate.queryForObject(notAsked, Integer.class, uid); // Saving query-result to object-variable instead of list
//	log.info("!******** REST Dao 2 qid : " + qid);
//	if (qid != 0) {								// Checking if there are any questions left which user has not answered
//		return jdbcTemplate.queryForObject("SELECT u.uid, q.qid, q.question, q.amount_answs, q.sum_answers, q.average, q.true_answer, q.type, q.amount, q.maxlength, q.min, q.max, q.step, q.def_val, q.value_1, q.value_2, q.value_3, q.value_4, q.value_5 FROM questions q INNER JOIN user_answers u ON q.qid=u.qid WHERE q.qid = ? LIMIT 1", QuestionRowMapper.questionRowMapper,qid);	// Select question which qid is not asked
//	} else {
//		log.info("!******** REST Dao 3 qid : " + qid);
//		return new Question2(0,"");			
//	}
//}	

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

// FIGHT AGAINST RESUBMISSION OF THE FORM
//	public boolean checkIfAnswered(int uid, int qid) {
//		try{
//			jdbcTemplate.queryForObject("SELECT * FROM user_answers WHERE uid = ? AND QID = ?",boolean.class, uid,qid);
//			log.info("!******** REST Dao2 true, is already answered");
//			return true;
//		}
//		catch(EmptyResultDataAccessException e){
//			log.info("!******** REST Dao2 false, not yet answered");
//			return false;
//		}
//	}
// THIS WORKED
//String placeHolder = (String)jdbcTemplate.queryForObject("SELECT answer FROM user_answers WHERE uid = ? AND QID = ?",String.class, uid,qid);


////ADD USER'S ANSWER DATA TO DATABASES	
//	public void addAnswer(Answer answer) { 
//		try {
//			jdbcTemplate.update("INSERT INTO user_answers(uid,qid,answer) VALUES (?,?,?)", answer.getUid(), answer.getQid(), answer.getAnswer());
//			jdbcTemplate.update("UPDATE users SET amount_answers = (SELECT COUNT(*) FROM user_answers WHERE uid = users.uid) WHERE uid = ?",answer.getUid());
//			jdbcTemplate.update("UPDATE questions SET amount_answs = amount_answs + 1 WHERE qid = ?",answer.getQid());			
//		}
//		catch(DataIntegrityViolationException e) {	// EI LÖYTYNYT HYVÄÄ TAPAA PALAUTTAA TÄTÄ TIETOA WEB-CONTROLLERIIN
//			throw new RuntimeException(e);
//		}
//	}

// TOIMI
//public Question getQuestionById(int qid){		 				// create database query for creating question for question-page
//String sql = "SELECT * FROM questions WHERE qid = ?";	 	// kysymysmerkkiin haetaan arvo seuraavan rivin new Object[]{qid}lla
//Question question = (Question)jdbcTemplate.queryForObject(sql, new Object[]{qid}, QUESTION_ROW_MAPPER);	
//return question;
//}
	
