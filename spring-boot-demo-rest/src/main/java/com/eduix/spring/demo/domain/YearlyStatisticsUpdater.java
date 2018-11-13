//package com.eduix.spring.demo.domain;
//
//import java.util.Calendar;
//import java.util.List;
//import java.util.TimerTask;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//private static class YearlyStatisticsUpdater extends TimerTask{
//
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
//	public void run() {
//		
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		
//		yearlyRecorder(year);
//		
//		public void yearlyRecorder(int year){	// QUESTION IDS (QIDS) INTO LIST
//			List<Integer> questionList = jdbcTemplate.queryForList("SELECT qid FROM questions", Integer.class);
//			for(int qid : questionList) {		// GO THROUGH EACH QUESTION AND PUT PROVINCE IDS (PIDS) INTO LIST
//				List<Integer> provinceList = jdbcTemplate.queryForList("SELECT pid FROM provinces", Integer.class);
//				for(int pid : provinceList) {	// GO THROUGH EACH PROVINCE AND UPDATE yearly_statistics TABLE
//					updateYearlyStatus(year,qid,pid);
//				}
//			}
//		}
//		public void updateYearlyStatus(int year, int qid, int pid) {
//			double mean = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ROUND(AVG(ua.answer),0) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND u.pid = ?),0)", Double.class, qid, pid);
//			double medi = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT AVG(answer) FROM (SELECT ua.answer, @rownum:=@rownum+1 as `row_number`, @total_rows:=@rownum FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid, (SELECT @rownum:=0) c WHERE ua.answer IS NOT NULL AND ua.answer REGEXP '^-?[0-9]+$' AND ua.qid= ? AND u.pid = ? ORDER BY ua.answer) AS a WHERE a.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))),0)", Double.class, qid, pid);
//			double mode = (double)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.answer REGEXP '^-?[0-9]+$' AND ua.qid = ? AND u.pid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", Double.class, qid, pid);
//			String classMode = (String)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT ua.answer FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE (ua.answer='AO01' OR ua.answer='AO02' OR ua.answer='AO03' OR ua.answer='AO04' OR ua.answer='AO05') AND ua.qid = ? AND u.pid = ? GROUP BY ua.answer ORDER BY COUNT(ua.answer) DESC LIMIT 1),0)", String.class, qid, pid);
//			int amntAnswVal1 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO01' AND u.pid = ?),0)", Integer.class, qid, pid);
//			int amntAnswVal2 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO02' AND u.pid = ?),0)", Integer.class, qid, pid);
//			int amntAnswVal3 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO03' AND u.pid = ?),0)", Integer.class, qid, pid);
//			int amntAnswVal4 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO04' AND u.pid = ?),0)", Integer.class, qid, pid);
//			int amntAnswVal15 = (int)jdbcTemplate.queryForObject("SELECT IFNULL((SELECT COUNT(*) FROM user_answers ua INNER JOIN users u ON ua.uid = u.uid WHERE ua.qid = ? AND answer='AO05' AND u.pid = ?),0)", Integer.class, qid, pid);
//			jdbcTemplate.update("INSERT INTO yearly_statistics(qid, pid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal15) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)", qid, pid, year, mean, medi, mode, classMode, amntAnswVal1, amntAnswVal2 , amntAnswVal3, amntAnswVal4, amntAnswVal15 );
//		}		
//	}
//}
