package com.eduix.spring.demo.client;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import queta.Answer;
import queta.AnswerStats;
import queta.PastQandA;
import queta.Question;
import queta.User;
import queta.YearlyStatus;

//import com.eduix.spring.demo.domain.DemoUser;
//
import org.apache.commons.logging.Log;
//
import org.apache.commons.logging.LogFactory;

@Component
public class UserClient {

// DEBUG LOGGER:	
	private static final Log log = LogFactory.getLog(UserClient.class);
// LOG TO PUT INSIDE CLASS:	log.info("!******** Web UserClient uid: "+uid);
	
	@Autowired						// Spring dependency injectionin luoma automaattinen yhteys allaolevaan constructoriin, kenttään tai setteriin
	RestTemplate restTemplate;		// keskeinen spring-luokka clientin http-kytkentöihin. alusta (template) sisältää metodeja kuten delete, get, post...

	public Question getNotAskedQuestion(int uid) {		// Rakentaa question-sivun käyttäjän id:n perusteella
		Question question = restTemplate.getForObject("/"+uid, Question.class);
		return question;
	}	

	public Question getQuestion(int qid) {				// Hakee vastatun kysymyksen statsit answerStats-sivulle 
		log.info("!******** WUCli getQuestion qid: " + qid);
		Question question = restTemplate.getForObject("/getQuestionData/"+qid, Question.class);	// Oli aiemmin pelkkä /
		return question;
	}
	
	public void addUserAnswer(Answer answer) {
		log.info("!******** WUCli addUserAnswer uid: " + answer.getUid() + ", qid: " + answer.getQid());
		restTemplate.postForObject("/answer", answer ,Answer.class);	// luodaan /answer-osoite ja kutsutaan restiä
	}

	public User checkUser(User checkUser) {
		User user = restTemplate.postForObject("/checkUser", checkUser, User.class);
		return user;
	}
	
	public User getUser(int uid) {
		return restTemplate.getForObject("/user/" + uid, User.class);
	}
	
	public AnswerStats getAnswerStats(int qid) {
		return restTemplate.getForObject("/answerStats/" + qid, AnswerStats.class);
	}	
	
	public List<PastQandA> getPastQandAs(int uid) {	
		PastQandA[] pastQandAs = restTemplate.getForObject("/answerHistory/" + uid, PastQandA[].class); // TUTKI MITÄ TAPAHTUU JOS TÄHÄN TULEE NULL
		return Arrays.asList(pastQandAs);
	}

	public PastQandA getPastQandA(int uid, int qid) {	
		return restTemplate.getForObject("/historicAnswer/" + uid + "/" + qid, PastQandA.class);
	}	
	
//	PREVENT RESUBMISSION OF THE FORM
	public boolean checkIfAlreadyAnswered(int uid, int qid) {
		return restTemplate.getForObject("/checkIfAnswered/" + uid + "/" + qid, boolean.class);
	}
	
	public List<YearlyStatus> getYearlyStatuses() {	
		log.info("!******** WUCli getYearlyStatuses");
		YearlyStatus[] yearlyStatuses = restTemplate.getForObject("/yearlyStatuses", YearlyStatus[].class);
		return Arrays.asList(yearlyStatuses);
	}	
	
}