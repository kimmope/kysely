package com.eduix.spring.demo.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import Rowmappers.QuestionRowMapper;
import Rowmappers.UserRowMapper;
import Rowmappers.YearlyStatusRowMapper;
import queta.Answer;
import queta.AnswerStats;
import queta.PastQandA;
import queta.Question;
import queta.User;
import queta.YearlyStatus;
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
		jdbcTemplate.update("INSERT INTO user_answers(uid,qid,answer) VALUES (?,?,?)", answer.getUid(), answer.getQid(), answer.getAnswer());
		jdbcTemplate.update("UPDATE users SET amntUserAnsw = (SELECT COUNT(*) FROM user_answers WHERE uid = users.uid) WHERE uid = ?",answer.getUid());
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
				resultSet.getString("answer"));
		}
	};	
	public List<PastQandA> getPastQandAs(int uid) {			// Hae lista aikaisemmista kysymys-vastauspareista
		return jdbcTemplate.query("SELECT u.uid, u.qid, q.question, u.timeOfAnswer, u.answer FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? ORDER BY u.timeOfAnswer DESC",ROW_MAPPER_4,uid);	// List queryllä 
	}
	public PastQandA getPastQandA(int uid, int qid) {		// Hae yksi kysymys-vastauspari
		return jdbcTemplate.queryForObject("SELECT u.uid, u.qid, q.question, u.timeOfAnswer, u.answer FROM user_answers u INNER JOIN questions q ON u.qid=q.qid WHERE u.uid=? AND q.qid=? ORDER BY u.timeOfAnswer DESC",ROW_MAPPER_4,uid,qid);	//	Objekti queryForObjectillä
	}
	
// CALCULATING FIGURES FOR ANSWER STATISTICS-CLASS
	public AnswerStats getAnswerStats(int qid) {
		log.info("!******** REST DAO AnswerStats Start qid = " + qid);
		// Kaikkien yhteensä ja jokaisen regionin vastausmäärät erikseen
		int amountTot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ?),0)",Integer.class,qid);
		int amountP1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979),0)", Integer.class, qid);
		int amountP2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980),0)", Integer.class, qid);
		int amountP3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981),0)", Integer.class, qid);
		int amountP4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982),0)", Integer.class, qid);
		int amountP5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983),0)", Integer.class, qid);
		// Kaikkien yhteensä ja jokaisen regionin keskiarvot erikseen
		double meanAll = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(answer),0) FROM user_answers WHERE qid = ?),0)", Double.class, qid);
		double meanP1 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 979),0)", Double.class, qid);
		double meanP2 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 980),0)", Double.class, qid);
		double meanP3 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 981),0)", Double.class, qid);
		double meanP4 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 982),0)", Double.class, qid);
		double meanP5 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = 983),0)", Double.class, qid);
		log.info("!******** REST DAO AnswerStats 0");
		// Kaikkien yhteensä ja jokaisen regionin mediaanit (keskimmäiset arvot) erikseen
		double mediAll = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers, (SELECT @rownum:=0) c WHERE answer IS NOT NULL AND answer REGEXP '^-?[0-9]+$' AND qid=? ORDER BY answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mediP1 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid=? AND u.pid = 979 ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mediP2 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid=? AND u.pid = 980 ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mediP3 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid=? AND u.pid = 981 ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mediP4 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid=? AND u.pid = 982 ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mediP5 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid=? AND u.pid = 983 ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		log.info("!******** REST DAO AnswerStats 1");
		// Kaikkien yhteensä ja jokaisen regionin yleisin luokka 
		String classModeAll = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT answer FROM user_answers WHERE (answer='AO01' OR answer='AO02' OR answer='AO03' OR answer='AO04' OR answer='AO05') AND qid = ? GROUP BY answer ORDER BY COUNT(answer) DESC LIMIT 1),0)",String.class,qid);
		String classModeP1 = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = 979 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		String classModeP2 = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = 980 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		String classModeP3 = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = 981 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		String classModeP4 = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = 982 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		String classModeP5 = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = 983 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		log.info("!******** REST DAO AnswerStats 2");
		// Kaikkien yhteensä ja jokaisen regionin yleisin arvo
		double modeAll = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT answer FROM user_answers WHERE answer REGEXP '^-?[0-9]+$' AND qid = ? GROUP BY answer ORDER BY COUNT(answer) DESC LIMIT 1),0)",Double.class,qid);
		double modeP1 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = 979 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		double modeP2 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = 980 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		double modeP3 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = 981 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		double modeP4 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = 982 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		double modeP5 = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = 983 GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		log.info("!******** REST DAO AnswerStats 3");
		// Kaikkien luokkien yhteinen ja jokaisen regionin jokaisen luokan vastausmäärä
		int class1Tot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO01'),0)",Integer.class,qid);
		int class1P1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 979),0)", Integer.class, qid);
		int class1P2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 980),0)", Integer.class, qid);
		int class1P3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 981),0)", Integer.class, qid);
		int class1P4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 982),0)", Integer.class, qid);
		int class1P5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = 983),0)", Integer.class, qid);
		int class2Tot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO02'),0)",Integer.class,qid);
		int class2P1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 979),0)", Integer.class, qid);
		int class2P2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 980),0)", Integer.class, qid);
		int class2P3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 981),0)", Integer.class, qid);
		int class2P4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 982),0)", Integer.class, qid);
		int class2P5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = 983),0)", Integer.class, qid);
		int class3Tot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO03'),0)",Integer.class,qid);
		int class3P1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 979),0)", Integer.class, qid);
		int class3P2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 980),0)", Integer.class, qid);
		int class3P3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 981),0)", Integer.class, qid);
		int class3P4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 982),0)", Integer.class, qid);
		int class3P5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = 983),0)", Integer.class, qid);
		int class4Tot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO04'),0)",Integer.class,qid);
		int class4P1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 979),0)", Integer.class, qid);
		int class4P2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 980),0)", Integer.class, qid);
		int class4P3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 981),0)", Integer.class, qid);
		int class4P4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 982),0)", Integer.class, qid);
		int class4P5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = 983),0)", Integer.class, qid);
		int class5Tot = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers WHERE qid = ? AND answer='AO05'),0)",Integer.class,qid);
		int class5P1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 979),0)", Integer.class, qid);
		int class5P2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 980),0)", Integer.class, qid);
		int class5P3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 981),0)", Integer.class, qid);
		int class5P4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 982),0)", Integer.class, qid);
		int class5P5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = 983),0)", Integer.class, qid);
		log.info("!******** REST DAO AnswerStats 4");
		AnswerStats AnswerStats = new AnswerStats(amountTot, amountP1, amountP2, amountP3, amountP4, amountP5, meanAll, meanP1, meanP2, meanP3, meanP4, meanP5, mediAll, mediP1, mediP2, mediP3, mediP4, mediP5,classModeAll,classModeP1,classModeP2,classModeP3,classModeP4,classModeP5,modeAll, modeP1, modeP2, modeP3, modeP4, modeP5, class1Tot, class1P1, class1P2, class1P3, class1P4, class1P5, class2Tot, class2P1, class2P2, class2P3, class2P4, class2P5, class3Tot, class3P1, class3P2, class3P3, class3P4, class3P5, class4Tot, class4P1, class4P2, class4P3, class4P4, class4P5,
				class5Tot, class5P1, class5P2, class5P3, class5P4, class5P5);
		return AnswerStats;
	}
	
// PREVENTING RESUBMISSION OF THE FORM
	public boolean checkIfAnswered(int uid, int qid) {
		try{
			jdbcTemplate.queryForObject("SELECT answer FROM user_answers WHERE uid = ? AND QID = ?",String.class, uid,qid);
			return true;
		}
		catch(EmptyResultDataAccessException e){
			return false;
		}
	}

	public void updateYearlyStatusPerProvince(int year, int qid, int pid) {
		double mean = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = ?),0)", Double.class, qid, pid);
		double medi = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid= ? AND u.pid = ? ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid, pid);
		double mode = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid, pid);
		String classMode = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid, pid);
		int amntAnswVal1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = ?),0)", Integer.class, qid, pid);
		int amntAnswVal2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = ?),0)", Integer.class, qid, pid);
		int amntAnswVal3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = ?),0)", Integer.class, qid, pid);
		int amntAnswVal4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = ?),0)", Integer.class, qid, pid);
		int amntAnswVal5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = ?),0)", Integer.class, qid, pid);
		jdbcTemplate.update("INSERT INTO yearly_statuses_per_province(qid, pid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", qid, pid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5 );
	}		

	public List<YearlyStatus> getYearlyStatusesPerProvince() {
		return jdbcTemplate.query("SELECT qid, pid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5 FROM yearly_statuses_per_province", YearlyStatusRowMapper.yearlyStatusRowMapper); 
	}

	public void updateYearlyStatus(int year, int qid) {
		double mean = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ?),0)", Double.class, qid);
		double medi = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid= ? ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid);
		double mode = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid);
		String classMode = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid);
		int amntAnswVal1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01'),0)", Integer.class, qid);
		int amntAnswVal2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02'),0)", Integer.class, qid);
		int amntAnswVal3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03'),0)", Integer.class, qid);
		int amntAnswVal4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04'),0)", Integer.class, qid);
		int amntAnswVal5 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05'),0)", Integer.class, qid);
		jdbcTemplate.update("INSERT INTO yearly_statuses(qid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5) VALUES (?,?,?,?,?,?,?,?,?,?,?)", qid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5 );
	}
	
	public List<YearlyStatus> getYearlyStatuses() {
		log.info("!******** DAO getYearlyStatuses");
		return jdbcTemplate.query("SELECT qid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal5 FROM yearly_statuses", YearlyStatusRowMapper.yearlyStatusRowMapper); 
	}
}

