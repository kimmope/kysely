package com.eduix.spring.demo.client;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.eduix.spring.demo.domain.DemoUser;

@Component
public class UserClient {

private static final Log log = LogFactory.getLog(UserClient.class);
	
	@Autowired						// Spring dependency injectionin luoma automaattinen yhteys allaolevaan constructoriin, kenttään tai setteriin
	RestTemplate restTemplate;		// keskeinen spring-luokka clientin http-kytkentöihin. alusta (template) sisältää metodeja kuten delete, get, post...

	public List<DemoUser> getUsers() {	// DemoUser-olioita sisältävän listan palauttava getUsers-metodi joka ei saa parametreja
		DemoUser[] users = restTemplate.getForObject("/userspage", DemoUser[].class);	// hakee getillä parametrin 1.stä urlista, mahdollinen vastaus muutetaan DemoUser[].class-tyyppiseksi  
		return Arrays.asList(users);	// palauttaa usersit-list viewinä (johon tehtävät muutokset palautuvat alkuperäiseen arrayhyn)
	}

	public DemoUser getUser(String username) {
		DemoUser user = restTemplate.getForObject("/user/"+username, DemoUser.class);
		return user;
	}
	
/** TRAINING CODE: ------------------ */
	public void addUser(DemoUser demouser) {
		restTemplate.postForObject("/user", demouser ,DemoUser.class);	// postForObject luo uuden resurssin lähettämällä HTTP:n POSTilla objektin (demouser) URI-templateen ja palauttamalla vastauksena saadun esityksen DemoUser.class-tyyppisenä
	}

	public void editUser(DemoUser demouser) {
		log.info("USER CLIENT username = " + demouser.getUsername());
		restTemplate.postForObject("/edituser", demouser ,DemoUser.class);	// postForObject luo uuden resurssin lähettämällä HTTP:n POSTilla objektin (demouser) URI-templateen ja palauttamalla vastauksena saadun esityksen DemoUser.class-tyyppisenä
	}	
	
	public void deleteUser(String id) {
		restTemplate.delete("/nakkivene/"+id);	// poistaa resurssin parametrissa määritetystä urlista.
	}
	
/** OWN PROJECT: ------------------ */	
//	public void addAnswer(Answer answer) {
//		restTemplate.postForObject("/answer", answer ,Answer.class);		
//	}
//	
//	public Question getQuestion(int qid) {
//		Question question = restTemplate.getForObject("/"+qid, Question.class);
//		return question;
//	}	
}