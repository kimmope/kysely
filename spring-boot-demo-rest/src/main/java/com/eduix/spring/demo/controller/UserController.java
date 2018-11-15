package com.eduix.spring.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.eduix.spring.demo.dao.UserDao;
import queta.Answer;
import queta.AnswerStats;
import queta.PastQandA;
import queta.Question;
import queta.User;
import queta.YearlyStatus;

//import java.net.URI;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//import com.eduix.spring.demo.domain.DemoUser;
//import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@RestController		//No need to put @ResponseBody for every function. Hints for readers and for Sprint about special quality of the class. Renders the classe's resulting string back to caller.
public class UserController {
	
// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserController.class); // Change part "UserController" according to used class name
// LOG TO PUT INSIDE CLASS: log.info("!******** REST UserController qid: "+qid);
	
	@Autowired
	private UserDao dao; 								// Esitellään Database Access Object nimeltä "dao"
	
	@GetMapping("/{uid}")						
	public ResponseEntity<Question> getNotAskedQuestion(@PathVariable("uid") int uid) {
		log.info("!******** RUC getNotAskedQuestion 1 uid: " + uid);
		Question question = dao.getNotAskedQuestion(uid);
		log.info("!******** RUC getNotAskedQuestion 2 returning question: " + question);
		return ResponseEntity.ok(question);
	}
	
	@GetMapping("/getQuestionData/{qid}")						// saa webin userClientilta urista question id:en {gid} ja antaa sen allaolevan funktion käyttöön
	public ResponseEntity<Question> getQuestion(@PathVariable("qid") int qid) {
		Question question = dao.getQuestionById(qid);
		return ResponseEntity.ok(question);
	}
	
	@PostMapping("/answer")			// Kuunnellaan /answer-pagea ja vähennetään sille tuleva kutsu koskemaan ainoastaan allaolevaa funktiota
	public void addAnswer(@RequestBody Answer answer){
		log.info("!******** RUC addAnswer 1 answer: " + answer);
		dao.addAnswer(answer);
	}
	
	@PostMapping("/checkUser")	// Kuuntelee /checkUser-pagea ja siirtää sille tulevan kutsun allaolevalle funktiolle. Jos lähettää raakadataa niin <, consumes={application/json"}> 
	public ResponseEntity<?> checkUser(@RequestBody User user) { // @RequestBodyä ei tarvita kuin raakadatan lähetykseen
		User newUser = new User();
		try {
			log.info("!******** RUC try checkUser: " + user.getUsername());
			newUser = dao.checkUser(user.getUsername()); // Testataan onko useria
		} 
		catch (RuntimeException e) {
			newUser = dao.createNewUser(user);	// Jos useria ei ole, niin tehdään sellainen
		}
		return ResponseEntity.ok(newUser);
	}
	
	@GetMapping("/user/{uid}")				
	public ResponseEntity<User> getUser(@PathVariable("uid") int uid) {
		return ResponseEntity.ok(dao.getUser(uid));
	}		

	@GetMapping("/answerStats/{qid}")
	public ResponseEntity<AnswerStats> getAnswerStats(@PathVariable("qid") int qid) {
		return ResponseEntity.ok(dao.getAnswerStats(qid));
	}		
	
	@GetMapping("/answerHistory/{uid}")
	public ResponseEntity<List<PastQandA>> getPastQandAs(@PathVariable("uid") int uid) {
		try {
			return ResponseEntity.ok(dao.getPastQandAs(uid));
		} 
		catch (DataAccessException e) { // TÄTÄ EI KÄSITELLÄ KOSKA USER EI IKINÄ PÄÄSE TILANTEESEEN JOSSA EI OLISI YHTÄÄN VASTAUSTA
			return ResponseEntity.notFound().build(); // TUTKI MITEN TÄMÄ PALAUTUS KÄSITELLÄÄN WEBIN USER CLIENTISSA. Tällä voi palauttaa vain listankin!!!
		}
	}
	
	@GetMapping("/historicAnswer/{uid}/{qid}")
	public ResponseEntity<PastQandA> getPastQandA(@PathVariable("uid") int uid, @PathVariable("qid") int qid) {
		try {
			return ResponseEntity.ok(dao.getPastQandA(uid,qid));
		} 
		catch (DataAccessException e) {
			return ResponseEntity.notFound().build(); // TUTKI MITEN TÄMÄ PALAUTUS KÄSITELLÄÄN WEBIN USER CLIENTISSA. Tällä voi palauttaa vain olionkin! Ei tarvita responseentityä.
		}
	}
	
// PREVENTING RESUBMISSION OF THE FORM
	@GetMapping("/checkIfAnswered/{uid}/{qid}")
	public boolean checkIfAnswered(@PathVariable("uid") int uid, @PathVariable("qid") int qid){
		return dao.checkIfAnswered(uid,qid);
	}
	
	@GetMapping("/yearlyStatuses")
	public ResponseEntity<List<YearlyStatus>> getYearlyStatuses() {
		log.info("!******** RUC getYearlyStatuses");		
		try {
			return ResponseEntity.ok(dao.getYearlyStatuses());
		}
		catch (DataAccessException e) { 
			return ResponseEntity.notFound().build(); 
		}
	}

}