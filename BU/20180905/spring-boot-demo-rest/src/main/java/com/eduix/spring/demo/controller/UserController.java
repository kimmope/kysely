package com.eduix.spring.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController											// Hints for readers and for Sprint about special quality of the class. Renders the classe's resulting string back to caller.
public class UserController {

// DEBUG LOGGER:		private static final Log log = LogFactory.getLog(UserController.class);
	
	@Autowired
	private UserDao dao; 								// Luodaan Database Access Object nimeltä "dao"
	
	@GetMapping("/userspage") 							// kun "userspage"lle tulee get-kutsu niin GetMapping-annotaatio lähettää sen allaolevalle funktiolle 
	public List<DemoUser> getUsers() {
		return dao.getUsers();							// palauttaa UserDao-luokan dao-objektin getUsers-funktion tuloksen
	}
	
	@GetMapping("/user/{username}")						// saa arvonaan urista{usernamen} ja antaa sen allaolevan funktion käyttöön
	public ResponseEntity<DemoUser> getUser(@PathVariable("username") String username) {	// getUser-funktio palauttaa ResponseEntityn joka (perii httpEntityn) on kokonainen http-response (sis. header, body) ja lisää siihen HttpStatus status coden
		DemoUser user = dao.getUser(username);			
		if (user == null) {
			return ResponseEntity.notFound().build();	// Jos useria ei löydy, palautetaan ResponseEntityn builder notFound-statuksella (Http error 404)
		}
		return ResponseEntity.ok(user);					// Jos user löytyy, se palautetaan Ok-statuksella
	}

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
	public ResponseEntity<?> deleteUser(@PathVariable String id) {
		dao.deleteUser(id);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/edituser")
	public ResponseEntity<?> editUser(@RequestBody DemoUser user) {
		dao.editUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}").buildAndExpand(user.getUsername()).toUri();
		return ResponseEntity.created(location).build();
	}
	
// Own project
//	@GetMapping("/{qid}")						// saa arvonaan urista{usernamen} ja antaa sen allaolevan funktion käyttöön
//	public ResponseEntity<Question> getQuestion(@PathVariable("qid") int qid) {
//		Question question = dao.getQuestion(qid);
//		if (question == null) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok(question);
//	}	
}