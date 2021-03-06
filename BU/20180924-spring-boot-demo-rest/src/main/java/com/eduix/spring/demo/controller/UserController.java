package com.eduix.spring.demo.controller;

//	
import org.apache.commons.logging.Log;
//	
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduix.spring.demo.dao.UserDao;
import com.eduix.spring.demo.domain.DemoUser;

import queta.Answer;
import queta.Question;
import queta.User;

@RestController											// Hints for readers and for Sprint about special quality of the class. Renders the classe's resulting string back to caller.
public class UserController {
	
// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserController.class); // Change part "UserController" according to used class name
// LOG TO PUT INSIDE CLASS: log.info("!******** REST user controller qid: "+qid);
	
	@Autowired
	private UserDao dao; 								// Luodaan Database Access Object nimeltä "dao"
	
	@GetMapping("/userspage") 							// kun "userspage"lle tulee get-kutsu niin GetMapping-annotaatio lähettää sen allaolevalle funktiolle 
	public List<DemoUser> getUsers() {
		return dao.getUsers();							// palauttaa UserDao-luokan dao-objektin getUsers-funktion tuloksen
	}
	
	@GetMapping("/user/{username}")						// saa arvonaan urista{usernamen} ja antaa sen allaolevan funktion käyttöön
	public ResponseEntity<DemoUser> getUser(@PathVariable("username") String username) {	// getUser-funktio palauttaa ResponseEntityn joka (perii httpEntityn) on kokonainen http-response (sis. header, body) ja lisää siihen HttpStatus status coden
		try {
			return ResponseEntity.ok(dao.getUser(username));
		}
		catch(DataAccessException e){
			return ResponseEntity.notFound().build();
		}
	}

//	@GetMapping("/user/{username}")						// saa arvonaan urista{usernamen} ja antaa sen allaolevan funktion käyttöön
//	public ResponseEntity<DemoUser> getUser(@PathVariable("username") String username) {	// getUser-funktio palauttaa ResponseEntityn joka (perii httpEntityn) on kokonainen http-response (sis. header, body) ja lisää siihen HttpStatus status coden
//		DemoUser user = dao.getUser(username);			
//		if (user == null) {
//			return ResponseEntity.notFound().build();	// Jos useria ei löydy, palautetaan ResponseEntityn builder notFound-statuksella (Http error 404)
//		}
//		return ResponseEntity.ok(user);					// Jos user löytyy, se palautetaan Ok-statuksella
//	}	
	
	@PostMapping("/user")
	public ResponseEntity<?> addUser(@RequestBody DemoUser user) { // addUser-funktio palauttaa ResponseEntityn määrittelemättömän olion. Parametrina on DemoUser-olio user joka yhdistetään @RequestBodylla @PostMappingista saapuvaan http-responseen
		dao.addUser(user);								// kutsutaan dao-luokan addUser-funktiota user-parametrilla
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()	// Luodaan URI-luokan olio location. ServletUriComponentsBuilder on UriComponentsBuilder lisättynä staattisilla tehdasmetodeilla. Se luo linkin perustuen fromCurrentRequestiin.
													.path("/{username}")	// määritetään osa polkua
													.buildAndExpand(user.getUsername()).toUri(); // peritty UriComponentsBuilderilta, yhdistää build()in ja UriComponents.expand(Map)in. Palauttaa URI-komponentit lisätyillä arvoilla
		return ResponseEntity.created(location).build();	// palauttaa uuden urin created-statuksella
	}
	
/** TRAINING CODE: ------------------ */
	@DeleteMapping("/nakkivene/{id}")
	public void deleteUser(@PathVariable String id) {
		dao.deleteUser(id);
	}
	
	@PostMapping("/edituser")
	public void editUser(@RequestBody DemoUser user) {
		dao.editUser(user);
	}
	
//OWN PROJECT
	@GetMapping("/{uid}")						
	public ResponseEntity<Question> getNotAskedQuestion(@PathVariable("uid") int uid) {
		log.info("!******** REST UserController getNotAskedQuestion uid 1 : " + uid);
		Question question = dao.getNotAskedQuestion(uid);
		return ResponseEntity.ok(question);
	}	
	
	@GetMapping("/getQuestionData/{qid}")						// saa webin userClientilta urista question id:en {gid} ja antaa sen allaolevan funktion käyttöön
	public ResponseEntity<Question> getQuestion(@PathVariable("qid") int qid) {
		Question question = dao.getQuestionById(qid);
		return ResponseEntity.ok(question);
	}
	
	@PostMapping("/answer")			// Kuunnellaan /answer-pagea ja vähennetään sille tuleva kutsu koskemaan ainoastaan allaolevaa funktiota
	public void addAnswer(@RequestBody Answer answer){
		dao.addAnswer(answer);
	}
	
	@PostMapping("/checkUser")	// Kuuntelee /checkUser-pagea ja siirtää sille tulevan kutsun allaolevalle funktiolle
	public ResponseEntity<?> checkUser(@RequestBody String username) { 
		User user = new User();
		log.info("!******** REST UserController checkUser 1 : "+username);
		try {
			log.info("!******** REST UserController checkUser 2");
			user = dao.checkUser(username);
			log.info("!******** REST UserController checkUser 3");
		} 
		catch (RuntimeException e) {
			log.info("!******** REST UserController checkUser 4 before createNewUser");
			user = dao.createNewUser(username);
			log.info("!******** REST UserController checkUser 5 after createNewUser");
		}
		log.info("!******** REST UserController checkUser5 before return");
		return ResponseEntity.ok(user);
	}		
}