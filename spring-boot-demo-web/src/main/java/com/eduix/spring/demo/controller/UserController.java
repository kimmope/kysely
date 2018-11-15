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
import queta.AnswerStats;
import queta.PastQandA;
import queta.Question;
import queta.User;
import queta.YearlyStatus;

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
	public String login() {
		return "login";
	}

	// FIRST QUESTION FORM-HANDLING HAD TO BE DONE SEPARATELY DUE TO INABILITY OF SENDING FORM-PARAMETER DATA FROM FUNCTION. USER NEEDS TO BE CHECKED/CREATED.
	@PostMapping("/firstQuestionForm")
	public String checkUserForm(Model model, User user) {
		log.info("!******** WUC checkUserForm1 ");
		User checkedUser = userClient.checkUser(user);	// Tarkistaa onko user olemassa, jos ei ole, luo uuden
		log.info("!******** WUC checkUserForm2, checkedUser.getUid(): " + checkedUser.getUid());
		model.addAttribute("uid", checkedUser.getUid());		
		Question unansweredQuestion = userClient.getNotAskedQuestion(checkedUser.getUid());		// Etsii kysymyksen jota käyttäjältä ei ole ennen kysytty
		log.info("!******** WUC checkUserForm3, unansweredQuestion.getUid(): " + unansweredQuestion.getQuestion());
		model.addAttribute("uQ", unansweredQuestion);
		if(unansweredQuestion.getQid() == 0) {	// Jos ei ole kysymättömiä kysymyksiä, ohjataan vastaussivulle jossa selitetään tilanne
			log.info("!******** Web user first question if unansweredQuestions");
			return "answerPage";
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
			return "answerPage";
		}
		else {
			return "question";							// Jos on kysymätön kysymys, esitetään se
		}
	}
	
	@PostMapping("/answer")	// Käyttäjän vastaus kantaan
	public String answerForm(Model model, Answer answer) {
		log.info("!*** WUCon checkIfAlreadyAnswered 0 uid, answer: " + answer.getUid() +" "+ answer.getAnswer1());
		if(userClient.checkIfAlreadyAnswered(answer.getUid(), answer.getQid())){ // Prevent form resubmission
			log.info("!******** WUCon checkIfAlreadyAnswered 1 resubmit error uid, qid: " + answer.getUid() +" "+ answer.getQid());
			boolean resubmitError = true;
			model.addAttribute("resubmitError",resubmitError);
			model.addAttribute("answer", answer);
			AnswerStats answerStats = userClient.getAnswerStats(answer.getQid());
			model.addAttribute("answerStats",answerStats);			
			Question oldQuestion = userClient.getQuestion(answer.getQid());
			log.info("!******** WUCon. /answer 2 resubmit error oldQuestion.qid: " + oldQuestion.getQid());
			model.addAttribute("oldQuestion", oldQuestion);
			Question unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
			log.info("!******** WUCon. /answer 3 resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getQid());
			model.addAttribute("unansweredQuestion", unansweredQuestion);		
			return "answerPage";
		}
		else {
			log.info("!******** WUCon checkIfAlreadyAnswered 1 no resubmit error");
			boolean resubmitError = false;
			model.addAttribute("resubmitError",resubmitError);
			userClient.addUserAnswer(answer);				// Lähetä käyttäjän vastaus tietokantaan
			model.addAttribute("answer", answer);
			AnswerStats answerStats = userClient.getAnswerStats(answer.getQid());
			model.addAttribute("answerStats",answerStats);
			log.info("!******** WUCon. /answer 2 no resubmit error, uid, qid: " + answer.getUid() +" "+ answer.getQid());
			Question oldQuestion = userClient.getQuestion(answer.getQid());
			model.addAttribute("oldQuestion", oldQuestion);
			Question unansweredQuestion = userClient.getNotAskedQuestion(answer.getUid());	// Tällä ainoastaan tarkistetaan onko enää kysymättömiä kysymyksiä
			log.info("!******** WUCon. /answer 4 no resubmit error unansweredQuestion.uid,qid: " + unansweredQuestion.getQid());
			model.addAttribute("unansweredQuestion", unansweredQuestion);		
			return "answerPage";
		}
	}

	@PostMapping("/pastAnswers")
	public String history(Model model, @RequestParam int uid) {
		List<PastQandA> pastQandAs = userClient.getPastQandAs(uid);
		model.addAttribute("pastQandAs", pastQandAs);	// VARMISTA ETTÄ NULL-VASTAUS KÄSITELLÄÄN OIKEIN
		User user = userClient.getUser(uid);
		model.addAttribute("user", user);
		model.addAttribute("uid", uid);	// TÄMÄ PITI TEHDÄ KOSKA FORMISSA user.uid TULKATTIIN STRINGIKSI. MUUALLA TOIMII (ESIM: answer.uid ja pastQandA.uid)
		Question unansweredQuestion = userClient.getNotAskedQuestion(uid);	// Tällä ainoastaan haetaan uusi kysymys ja jos sellainen on niin luodaan nappi sinne pääsyyn
		model.addAttribute("unansweredQuestion", unansweredQuestion);		
		return "history";
	}
	
	@PostMapping("/oldAnswer")
	public String oldAnswer(Model model, @RequestParam int uid, @RequestParam int qid){
		List<YearlyStatus> yearlyStatuses = userClient.getYearlyStatuses();
		model.addAttribute("yearlyStatuses",yearlyStatuses);
		PastQandA pastQandA = userClient.getPastQandA(uid,qid);
		model.addAttribute("pastQandA",pastQandA);
		Question oldQuestion = userClient.getQuestion(qid);
		model.addAttribute("oldQuestion", oldQuestion);
		AnswerStats answerStats = userClient.getAnswerStats(qid);
		model.addAttribute("answerStats",answerStats);
		Question unansweredQuestion = userClient.getNotAskedQuestion(uid);
		model.addAttribute("unansweredQuestion", unansweredQuestion);
		return "pastQuestion";
	}
}	
