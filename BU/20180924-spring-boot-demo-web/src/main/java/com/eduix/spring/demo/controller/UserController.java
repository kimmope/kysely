package com.eduix.spring.demo.controller;				// Default-package

// 
import org.apache.commons.logging.Log;
// 
import org.apache.commons.logging.LogFactory;

import java.net.ConnectException;						// Imported libraries
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

import com.eduix.spring.demo.client.UserClient;
import com.eduix.spring.demo.domain.DemoUser;

import queta.Answer;
import queta.Question;
import queta.User;

@Controller												// marks the class as web controller which is capable of handling the requests
public class UserController {

// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserController.class);
// LOG TO PUT INSIDE CLASS:	 log.info("!******** Web user controller answer.getAnswer(): "+answer.getAnswer());
	
	@Autowired											// marks a constructor, field, or setter method to be autowired by Spring dependency injection. Here connection to UserClient-class.
	private UserClient userClient;

	@ExceptionHandler({ConnectException.class})			// Poikkeuskäsittelijä yhteysvirheeseen, parametrina handlerin luokka joka laukaisee käsittelijän  
	public String connectionError() {					
		return "connectionError";						// Palauttaa stringin jonka niminen ftl-file pitää löytyä templateista joka taas näytetään käyttäjälle
	}	
	
//	@ResponseStatus(value=HttpStatus.NOT_FOUND)
//	public userNotFoundException extends RuntimeException{
//		
//	}
	
	@GetMapping("/userspage")							// kun tässä mainitulle "userspage"-sivulle (voi olla mikä vaan) tullaan lähetetään get_kutsu, tämä annotaatio lähettää sen allaolevalle funktiolle
	public String getUsers(Model model) {				// parametrina annetaan MVC:n java model-object
		List<DemoUser> listOfUsers = userClient.getUsers(); 	// userClientin getUsers-funktion tulos asetetaan DemoUser-olioina arraylistiin nimeltä "listOfUsers"
		model.addAttribute("listOfUsers", listOfUsers);				// lisää modeliin "listOfUsers"-nimisen model attribuutin jonka arvo on listOfUsers
		return "userlist";								// palauttaa userlist- nimisen ftl-tiedoston
	}

	// Tässä testailtiin testi kuinka palautetaan tietokannasta erroreita 
	@GetMapping("/user/{username}")						// Kun sivu lähettää rakentumisvaiheessaan http get-kutsun, @GetMapping- annotaatio lähettää mainitun sisällön ({username}) allaolevalle funktiolle
	public String getUser(Model model, @PathVariable("username") String username) { // parametreina springin model, @pathvariable lukee urlin aaltosulkeiden välisen osan ja asettaa sen stringiin nimeltä username. ("username")a ei tarvita mikäli sana pysyy samana kuin lähetettävä string
		try {
			DemoUser user = userClient.getUser(username);
			model.addAttribute("user", user);
			return "user";
		}
		catch(RestClientException e){
			return "noSuchUser";
		}
//		DemoUser user = userClient.getUser(username);	// Tehdään DemoUser-luokan olio user käyttämällä useClientin getUser-metodia username-parametrilla
//		if (user == null) {
//			return "noSuchUser";
//		}
//		else {
//			model.addAttribute("user", user);			// lisätään modeliin user-olio
//			return "user";	
//		}
	}

//	@GetMapping("/user/{username}")						// Kun sivu lähettää rakentumisvaiheessaan http get-kutsun, @GetMapping- annotaatio lähettää mainitun sisällön ({username}) allaolevalle funktiolle
//	public String getUser(Model model, @PathVariable("username") String username) { // parametreina springin model, @pathvariable lukee urlin aaltosulkeiden välisen osan ja asettaa sen stringiin nimeltä username. ("username")a ei tarvita mikäli sana pysyy samana kuin lähetettävä string
//		DemoUser user = userClient.getUser(username);	// Tehdään DemoUser-luokan olio user käyttämällä useClientin getUser-metodia username-parametrilla
//		if (user == null) {
//			return "noSuchUser";
//		}
//		else {
//			model.addAttribute("user", user);			// lisätään modeliin user-olio
//			return "user";	
//		}
//	}	
	
	@RequestMapping("/adduser")							// luo perustietopolun ja kutsuu seuraavaa funktiota. Kaikki http-kutsut /-polulla ohjataan home-metodiin
	public String adduser() {							// parametriton adduser-funktio joka palauttaa stringin
		return "adduser";								// Palauttaa string-tyyppisen adduser-ftl-sivun
	}
	@PostMapping("/adduserform")						// vähentää seuraavan funktiokutsun koskemaan ainoastaan adduserform-formista lähetettyä post-kutsua
	public String addUserForm(DemoUser demouser) {		// stringin palauttava funktio jonka parametrina demouser-olio
		userClient.addUser(demouser);					// kutsutaan userClientin adduser-funktiota demouser-parametrille
		return "redirect:/userspage";					// kun palataan userclientista mennään uuteen osoitteeseen, tässä: localhost:62000/userspage.
	}
	
/** TRAINING CODE: ------------------ */
	@GetMapping("/edituser/{username}")
	public String getEditUser(Model model, @PathVariable("username") String username) {
		DemoUser user = userClient.getUser(username);
		model.addAttribute("user", user);
		return "edituser";
	}	
	@PostMapping("/edituserform")
	public String editUserForm(DemoUser demouser) {
		userClient.editUser(demouser);
		return "redirect:/userspage";
	}	
	
	@GetMapping("/deleteuser")							// vähentää seuraavan funktiokutsun koskemaan ainoastaan deleteuser-sivun käynnistyksen laukaisemaa GET-tyyppistä tietoa (tässä: "deleteuser?Id=${user.username}) 
	public String deleteUser(@RequestParam String Id) {	// stringin palauttava deleteUser-metodi jonka parametrina @RequestParamin keräämä parametri (tässä a hrefin Id)
		userClient.deleteUser(Id);						// kutsutaan userClientin deleteUseria Id:llä joka juuri noudettiin userspagelta
		return "redirect:/userspage";					// palatessa siirrytään uudelle sivulle (reloadataan) userspage
	}

/** OWN PROJECT  -------------------- */
	
	@RequestMapping("/login")
	public String checkUser() {
		return "login";
	}
	
	@PostMapping("/loginForm")
	public String checkUserForm(Model model, User user) {	// Model tulee taikuudesta, user loginformista
		log.info("!******** Web UserController checkUserForm user.getUsername() 1: " + user.getUsername());
		User checkedUser = userClient.checkUser(user.getUsername());	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
		model.addAttribute("user",checkedUser);
		int uid = checkedUser.getUid();
		log.info("!******** Web UserController checkUserForm uid 2 : " + uid);
		return "redirect:/newQuestion/"+uid;
	}
	
	@GetMapping("/newQuestion/{uid}")	// Sama mistä /newQuestion/{uid}-kutsu tulee. Ohjaa allaolevalle
	public String getNotAskedQuestion(Model model, @PathVariable("uid") int uid) {	// Model taikuudesta, pathvariable "uid" loginformista
		Question question = userClient.getNotAskedQuestion(uid);	// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
		model.addAttribute("question", question);
		model.addAttribute("uid", uid);
		if(question.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan viestisivulle
			return "noMoreQuestions";
		}
		else {
			return "question";			// Jos on kysymätön kysymys, esitetään se
		}
	}
	
	@PostMapping("/answerForm")
	public String answerForm(Model model, Answer answer) {
		userClient.addUserAnswer(answer);
		model.addAttribute("answer", answer);
		Question question = userClient.getQuestion(answer.getQid());
		model.addAttribute("question", question);
		Question nextQuestion = userClient.getNotAskedQuestion(answer.getUid());
		model.addAttribute("nextQuestion", nextQuestion);		
		return "answerStats";
	}
	
//	@PostMapping("/options")
//	public String options(Model model, Answer answer) {
//		userClient.getOldUserAnswers(answer);
//		model.addAttribute("answer", answer);
//		Question question = userClient.getQuestion(answer.getQid());
//		model.addAttribute("question", question);
//		return "answerStats";
//	}
}