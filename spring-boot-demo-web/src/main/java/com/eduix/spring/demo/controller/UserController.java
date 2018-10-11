package com.eduix.spring.demo.controller;				// Default-package

import java.net.ConnectException;						// Imported libraries
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import com.eduix.spring.demo.client.UserClient;
//
import org.apache.commons.logging.Log;
//
import org.apache.commons.logging.LogFactory;
//import com.eduix.spring.demo.domain.DemoUser;

import queta.Answer;
import queta.PastQandA;
import queta.Question;
import queta.User;

@Controller												// marks the class as web controller which is capable of handling the requests
public class UserController {

// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserController.class);
// LOG TO PUT INSIDE CLASS:	 log.info("!******** Web UserController answer.getAnswer(): "+answer.getAnswer());
	
	@Autowired											// marks a constructor, field, or setter method to be autowired by Spring dependency injection. Here connection to UserClient-class.
	private UserClient userClient;

	@ExceptionHandler({ConnectException.class})			// Poikkeuskäsittelijä yhteysvirheeseen, parametrina handlerin luokka joka laukaisee käsittelijän  
	public String connectionError() {					
		return "connectionError";						// Palauttaa stringin jonka niminen ftl-file pitää löytyä templateista joka taas näytetään käyttäjälle
	}	
	
	@RequestMapping("/login")
	public String checkUser() {
		return "login";
	}

	// FIRST QUESTION FORM-HANDLING HAD TO BE DONE SEPARATELY DUE TO INABILITY OF SENDING FORM-PARAMETER DATA FROM FUNCTION. USER NEEDS TO BE CHECKED/CREATED.
	@PostMapping("/firstQuestionForm")
	public String checkUserForm(Model model, User user) {
		User checkedUser = userClient.checkUser(user.getUsername());	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
		model.addAttribute("uid", checkedUser.getUid());		
		Question unansweredQuestion = userClient.getNotAskedQuestion(checkedUser.getUid());		// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
		model.addAttribute("uQ", unansweredQuestion);
		if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan vastaussivulle jossa selitetään tilanne
			log.info("!******** Web user first question if unansweredQuestions");
			return "answerStats";
		}
		else {	// Jos on kysymätön kysymys, esitetään se
			return "question";
		}
	}	

	@PostMapping("/newQuestion")	// Sama mistä /newQuestion/{uid}-kutsu tulee. Ohjaa allaolevalle
	public String getNotAskedQuestion(Model model, @RequestParam int uid) {	// Model taikuudesta, pathvariable "uid" loginformista
		model.addAttribute("uid", uid);
		Question unansweredQuestion = userClient.getNotAskedQuestion(uid);	// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
		model.addAttribute("uQ", unansweredQuestion);
		if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan viestisivulle
			return "answerStats";
		}
		else {
			return "question";							// Jos on kysymätön kysymys, esitetään se
		}
	}
	
	@PostMapping("/answer")
	public String answerForm(Model model, Answer answer) {
		if(userClient.checkIfAlreadyAnswered(answer.getUid(), answer.getQid())){ // Prevent form resubmission
			log.info("!******** WUCon. /answer 1 resubmit error uid, qid: " + answer.getUid() +" "+ answer.getQid());
			boolean resubmitError = true;
			model.addAttribute("resubmitError",resubmitError);
			model.addAttribute("answer", answer);
			Question oldQuestion = userClient.getQuestion(answer.getQid());
			log.info("!******** WUCon. /answer 2 resubmit error oldQuestion.qid: " + oldQuestion.getQid());
			model.addAttribute("oldQuestion", oldQuestion);
			Question unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
			log.info("!******** WUCon. /answer 3 resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getQid());
			model.addAttribute("unansweredQuestion", unansweredQuestion);		
			return "answerStats";
		}
		else {
			log.info("!******** WUCon. /answer 1 no resubmit error");
			boolean resubmitError = false;
			model.addAttribute("resubmitError",resubmitError);
			userClient.addUserAnswer(answer);				// Lähetä käyttäjän vastaus tietokantaan
			model.addAttribute("answer", answer);
			log.info("!******** WUCon. /answer 2 no resubmit error, uid, qid: " + answer.getUid() +" "+ answer.getQid());
			Question oldQuestion = userClient.getQuestion(answer.getQid());
			log.info("!******** average: " + oldQuestion.getAverage());
			model.addAttribute("oldQuestion", oldQuestion);
			Question unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
			log.info("!******** WUCon. /answer 4 no resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getQid());
			model.addAttribute("unansweredQuestion", unansweredQuestion);		
			return "answerStats";
		}
	}
	
	@PostMapping("/pastAnswers")
	public String history(Model model, @RequestParam int uid) {
		List<PastQandA> pastQandAs = userClient.getPastQandAs(uid);
		model.addAttribute("pastQandAs", pastQandAs);	// VARMISTA ETTÄ NULL-VASTAUS KÄSITELLÄÄN OIKEIN
		User user = userClient.getUser(uid);
		model.addAttribute("user", user);
		model.addAttribute("uid", uid);	// TÄMÄ PITI TEHDÄ KOSKA FORMISSA user.uid TULKATTIIN STRINGIKSI??? MUUALLA TOIMII (ESIM: answer.uid ja pastQandA.uid)
		Question unansweredQuestion = userClient.getNotAskedQuestion(uid);	// Tällä ainoastaan haetaan uusi kysymys ja jos sellainen on niin luodaan nappi sinne pääsyyn
		model.addAttribute("unansweredQuestion", unansweredQuestion);		
		return "history";
	}
	
	@PostMapping("/oldAnswer")
	public String oldAnswer(Model model, @RequestParam int uid, @RequestParam int qid){
		PastQandA pastQandA = userClient.getPastQandA(uid,qid);	// Toimiiko function overloading userclientissä
		model.addAttribute("pastQandA",pastQandA);
		Question unansweredQuestion = userClient.getNotAskedQuestion(uid);	// Tällä ainoastaan haetaan uusi kysymys ja jos sellainen on niin luodaan nappi sinne pääsyyn
		model.addAttribute("unansweredQuestion", unansweredQuestion);			
		return "pastQuestion";
	}
}	
// EN SAANUT PELAAMAAN MONIDATAOLIOTA
//@PostMapping("/firstQuestionForm")
//public String checkUserForm(Model model, User user) {
//	log.info("!******** WUContr. /firstQuestionForm checkUserForm 0 username: " + user.getUsername());
//	User checkedUser = userClient.checkUser(user.getUsername());	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
//	log.info("!******** WUContr. /firstQuestionForm checkUserForm 1 uid: " + checkedUser.getUid());
//	Question2 unansweredQuestion = userClient.getNotAskedQuestion(checkedUser.getUid());		// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
//	model.addAttribute("uQ", unansweredQuestion);
//	if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan vastaussivulle jossa selitetään tilanne
//		log.info("!******** WUContr. /firstQuestionForm 2, no unanswered questions");
//		return "answerStats";
//	}
//	else {	// Jos on kysymätön kysymys, esitetään se
//		log.info("!******** WUContr. /firstQuestionForm 2, unanswered question qid: " + unansweredQuestion.getQid());
//		return "question";
//	}
//}	
//@PostMapping("/newQuestion")	// Sama mistä /newQuestion/{uid}-kutsu tulee. Ohjaa allaolevalle
//public String getNotAskedQuestion(Model model, @RequestParam int uid) {	// Model taikuudesta, pathvariable "uid" loginformista
//	log.info("!******** Web UserController newQuestion 1 uid: " + uid);
//	Question2 unansweredQuestion = userClient.getNotAskedQuestion(uid);	// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
//	model.addAttribute("uQ", unansweredQuestion);
//	if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan viestisivulle
//		log.info("!******** WUContr. /newQuestion 2, no unansweredQuestion qid: " + unansweredQuestion.getQid());
//		return "answerStats";
//	}
//	else {
//		log.info("!******** WUContr. /newQuestion 2, unansweredQuestion.uid,qid: " + unansweredQuestion.getUid() +" "+ unansweredQuestion.getQid());
//		return "question";							// Jos on kysymätön kysymys, esitetään se
//	}
//}	
//@PostMapping("/answer")
//public String answerForm(Model model, Answer answer) {
//	if(userClient.checkIfAlreadyAnswered(answer.getUid(), answer.getQid())){ // Prevent resubmission of the form
//		log.info("!******** WUCon. /answer 1 resubmit error uid, qid: " + answer.getUid() +" "+ answer.getQid());
//		boolean resubmitError = true;
//		model.addAttribute("resubmitError",resubmitError);
//		model.addAttribute("answer", answer);
//		Question2 oldQuestion = userClient.getQuestion(answer.getQid());
//		log.info("!******** WUCon. /answer 2 resubmit error oldQuestion.qid: " + oldQuestion.getQid());
//		model.addAttribute("oldQuestion", oldQuestion);
//		Question2 unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
//		log.info("!******** WUCon. /answer 3 resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getUid() +" "+ unansweredQuestion.getQid());
//		model.addAttribute("unansweredQuestion", unansweredQuestion);		
//		return "answerStats";
//	}
//	else {
//		log.info("!******** WUCon. /answer 1 no resubmit error");
//		boolean resubmitError = false;
//		model.addAttribute("resubmitError",resubmitError);
//		userClient.addUserAnswer(answer);				// Lähetä käyttäjän vastaus tietokantaan
//		model.addAttribute("answer", answer);
//		log.info("!******** WUCon. /answer 2 no resubmit error, uid, qid: " + answer.getUid() +" "+ answer.getQid());
//		Question2 oldQuestion = userClient.getQuestion(answer.getQid());
//		log.info("!******** WUCon. /answer 3 no resubmit error oldQuestion.qid: " + oldQuestion.getQid());
//		model.addAttribute("oldQuestion", oldQuestion);
//		Question2 unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
//		log.info("!******** WUCon. /answer 4 no resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getUid() +" "+ unansweredQuestion.getQid());
//		model.addAttribute("unansweredQuestion", unansweredQuestion);		
//		return "answerStats";
//	}	

//@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)	// Testiä formin resubmittauksen estämiseen
//public String resubmitError() {					
//	return "resubmitError";						
//}		

//@ExceptionHandler(HttpServerErrorException.class)	// Ohjaa formin resubmittausvirheen webissä, mutta REST kaatuu
//public String resubmitError() {					
//	return "resubmitError";						
//}	

//BACKUP
//@PostMapping("/loginForm")
//public String checkUserForm(Model model, User user) {	// Model tulee taikuudesta, user loginformista
//	User checkedUser = userClient.checkUser(user.getUsername());	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
//	model.addAttribute("user",checkedUser);
//	int uid = checkedUser.getUid();
//	return "redirect:/newQuestion/"+uid;
//}
//BACKUP
//	@PostMapping("/newQuestion")	// Sama mistä /newQuestion/{uid}-kutsu tulee. Ohjaa allaolevalle
//	public String getNotAskedQuestion(Model model, @RequestParam int uid) {	// Model taikuudesta, pathvariable "uid" loginformista
//		Question question = userClient.getNotAskedQuestion(uid);	// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
//		model.addAttribute("question", question);
//		model.addAttribute("uid", uid);
//		// JOS POST EI RATKAISE BACK-NAPPULAN CRASHAUSONGELMAA NIIN JOS MAHDOLLISTA NIIN TARKASTA TÄSSÄ ONKO SIVULLETULIJAN UID/QID JO VASTATTU
//		if(question.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan viestisivulle
//			return "answerStats";
//		}
//		else {
//			return "question";			// Jos on kysymätön kysymys, esitetään se
//		}
//	}


// ORIGINAL CODES + TESTS
//	@PostMapping("/options")
//	public String options(Model model, Answer answer) {
//		userClient.getOldUserAnswers(answer);
//		model.addAttribute("answer", answer);
//		Question question = userClient.getQuestion(answer.getQid());
//		model.addAttribute("question", question);
//		return "answerStats";
//	}

// RESPONSE ERROREIDEN KÄSITTELYTESTI
//@ResponseStatus(value=HttpStatus.NOT_FOUND)
//public userNotFoundException extends RuntimeException{
//	
//}

//@GetMapping("/userspage")							// kun tässä mainitulle "userspage"-sivulle (voi olla mikä vaan) tullaan lähetetään get_kutsu, tämä annotaatio lähettää sen allaolevalle funktiolle
//public String getUsers(Model model) {				// parametrina annetaan MVC:n java model-object
//	List<DemoUser> listOfUsers = userClient.getUsers(); 	// userClientin getUsers-funktion tulos asetetaan DemoUser-olioina arraylistiin nimeltä "listOfUsers"
//	model.addAttribute("listOfUsers", listOfUsers);				// lisää modeliin "listOfUsers"-nimisen model attribuutin jonka arvo on listOfUsers
//	return "userlist";								// palauttaa userlist- nimisen ftl-tiedoston
//}

// Tässä testailtiin testi kuinka palautetaan tietokannasta erroreita 
//@GetMapping("/user/{username}")						// Kun sivu lähettää rakentumisvaiheessaan http get-kutsun, @GetMapping- annotaatio lähettää mainitun sisällön ({username}) allaolevalle funktiolle
//public String getUser(Model model, @PathVariable("username") String username) { // parametreina springin model, @pathvariable lukee urlin aaltosulkeiden välisen osan ja asettaa sen stringiin nimeltä username. ("username")a ei tarvita mikäli sana pysyy samana kuin lähetettävä string
//	try {
//		DemoUser user = userClient.getUser(username);
//		model.addAttribute("user", user);
//		return "user";
//	}
//	catch(RestClientException e){
//		return "noSuchUser";
//	}
//	DemoUser user = userClient.getUser(username);	// Tehdään DemoUser-luokan olio user käyttämällä useClientin getUser-metodia username-parametrilla
//	if (user == null) {
//		return "noSuchUser";
//	}
//	else {
//		model.addAttribute("user", user);			// lisätään modeliin user-olio
//		return "user";	
//	}
//}

//@GetMapping("/user/{username}")						// Kun sivu lähettää rakentumisvaiheessaan http get-kutsun, @GetMapping- annotaatio lähettää mainitun sisällön ({username}) allaolevalle funktiolle
//public String getUser(Model model, @PathVariable("username") String username) { // parametreina springin model, @pathvariable lukee urlin aaltosulkeiden välisen osan ja asettaa sen stringiin nimeltä username. ("username")a ei tarvita mikäli sana pysyy samana kuin lähetettävä string
//	DemoUser user = userClient.getUser(username);	// Tehdään DemoUser-luokan olio user käyttämällä useClientin getUser-metodia username-parametrilla
//	if (user == null) {
//		return "noSuchUser";
//	}
//	else {
//		model.addAttribute("user", user);			// lisätään modeliin user-olio
//		return "user";	
//	}
//}	

//@RequestMapping("/adduser")							// luo perustietopolun ja kutsuu seuraavaa funktiota. Kaikki http-kutsut /-polulla ohjataan home-metodiin
//public String adduser() {							// parametriton adduser-funktio joka palauttaa stringin
//	return "adduser";								// Palauttaa string-tyyppisen adduser-ftl-sivun
//}
//@PostMapping("/adduserform")						// vähentää seuraavan funktiokutsun koskemaan ainoastaan adduserform-formista lähetettyä post-kutsua
//public String addUserForm(DemoUser demouser) {		// stringin palauttava funktio jonka parametrina demouser-olio
//	userClient.addUser(demouser);					// kutsutaan userClientin adduser-funktiota demouser-parametrille
//	return "redirect:/userspage";					// kun palataan userclientista mennään uuteen osoitteeseen, tässä: localhost:62000/userspage.
//}
//
///** TRAINING CODE: ------------------ */
//@GetMapping("/edituser/{username}")
//public String getEditUser(Model model, @PathVariable("username") String username) {
//	DemoUser user = userClient.getUser(username);
//	model.addAttribute("user", user);
//	return "edituser";
//}	
//@PostMapping("/edituserform")
//public String editUserForm(DemoUser demouser) {
//	userClient.editUser(demouser);
//	return "redirect:/userspage";
//}	
//
//@GetMapping("/deleteuser")							// vähentää seuraavan funktiokutsun koskemaan ainoastaan deleteuser-sivun käynnistyksen laukaisemaa GET-tyyppistä tietoa (tässä: "deleteuser?Id=${user.username}) 
//public String deleteUser(@RequestParam String Id) {	// stringin palauttava deleteUser-metodi jonka parametrina @RequestParamin keräämä parametri (tässä a hrefin Id)
//	userClient.deleteUser(Id);						// kutsutaan userClientin deleteUseria Id:llä joka juuri noudettiin userspagelta
//	return "redirect:/userspage";					// palatessa siirrytään uudelle sivulle (reloadataan) userspage
//}

// FIGHT AGAINST RESUBMISSION OF THE FORM
//@PostMapping("/firstQuestionForm")
//public String checkUserForm(Model model, User user) {
//	User checkedUser = userClient.checkUser(user.getUsername());	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
//	model.addAttribute("uid", checkedUser.getUid());		
//	Question unansweredQuestion = userClient.getNotAskedQuestion(checkedUser.getUid());		// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
//	model.addAttribute("unansweredQuestion", unansweredQuestion);
//	if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan vastaussivulle jossa selitetään tilanne
//		log.info("!******** Web user first question if unansweredQuestions");
//		return "answerStats";
//	}
//	else {	// Jos on kysymätön kysymys, esitetään se
//		log.info("!******** Web UserController there is unansweredQuestion: "+unansweredQuestion.getQid());
//		if(userClient.checkIfAlreadyAnswered(checkedUser.getUid(), unansweredQuestion.getQid())){ // Jos yrittää uudelleenlähettää formia
//			log.info("!******** Web UserController there is resubmitError: "+checkedUser.getUid()+" "+unansweredQuestion.getQid());
//			return "resubmitError";
//		}
//		else {
//			log.info("!******** Web UserController there is no resubmitError: "+checkedUser.getUid()+" "+unansweredQuestion.getQid());
//			return "question";
//		}
//	}
//}	




