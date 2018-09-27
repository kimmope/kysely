package com.eduix.spring.demo.client;

//	
import org.apache.commons.logging.Log;
//	
import org.apache.commons.logging.LogFactory;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.eduix.spring.demo.domain.DemoUser;
import queta.Answer;
import queta.PastQandA;
import queta.Question;
import queta.User;

@Component
public class UserClient {

// DEBUG LOGGER:		
	private static final Log log = LogFactory.getLog(UserClient.class);
// LOG TO PUT INSIDE CLASS:	log.info("!******** Web user client uid: "+uid);
	
	@Autowired						// Spring dependency injectionin luoma automaattinen yhteys allaolevaan constructoriin, kenttään tai setteriin
	RestTemplate restTemplate;		// keskeinen spring-luokka clientin http-kytkentöihin. alusta (template) sisältää metodeja kuten delete, get, post...

// OWN PROJECT:
	public Question getNotAskedQuestion(int uid) {		// Rakentaa question-sivun käyttäjän id:n perusteella
		Question question = restTemplate.getForObject("/"+uid, Question.class);
		return question;
	}		
	
	public Question getQuestion(int qid) {				// Hakee vastatun kysymyksen statsit answerStats-sivulle 
		Question question = restTemplate.getForObject("/getQuestionData/"+qid, Question.class);	// Oli aiemmin pelkkä /
		return question;
	}
	
	public void addUserAnswer(Answer answer) {
		restTemplate.postForObject("/answer", answer ,Answer.class);	// luodaan /answer-osoite ja kutsutaan restiä
	}

	public User checkUser(String username) {
		User user = restTemplate.postForObject("/checkUser", username, User.class);
		return user;
	}
	
	public User getUser(int uid) {
		log.info("!******** Web UserClient getUser uid: " + uid);
		return restTemplate.getForObject("/user/" + uid, User.class);
	}	
	
	public List<PastQandA> getPastQandAs(int uid) {	
		log.info("!******** Web UserClient getPastQandAs uid: " + uid);
		PastQandA[] pastQandAs = restTemplate.getForObject("/answerHistory/" + uid, PastQandA[].class); // TUTKI MITÄ TAPAHTUU JOS TÄHÄN TULEE NULL
		return Arrays.asList(pastQandAs);
	}

	public PastQandA getPastQandA(int uid, int qid) {	
		log.info("!******** Web UserClient getPastQandA uid,qid: " + uid + ", " + qid);
		return restTemplate.getForObject("/historicAnswer/" + uid + "/" + qid, PastQandA.class);
	}	
}
//TESTI PALAUTUSARVOLLE	
//	public DemoUser getUser(String username) {
//		try {
//			DemoUser demouser = restTemplate.getForObject("/user/"+username, DemoUser.class);
//			return demouser;
//		}
//		catch(Exception e) {
//			return null;
//		}
//	}	

//public List<DemoUser> getUsers() {	// DemoUser-olioita sisältävän listan palauttava getUsers-metodi joka ei saa parametreja
//DemoUser[] demousers = restTemplate.getForObject("/userspage", DemoUser[].class);	// hakee getillä parametrin 1.stä urlista, mahdollinen vastaus muutetaan DemoUser[].class-tyyppiseksi  
//return Arrays.asList(demousers);	// palauttaa usersit-list viewinä (johon tehtävät muutokset palautuvat alkuperäiseen arrayhyn)
//}
//
//public DemoUser getUser(String username) { //Demouser-olion palauttava funktio joka saa parametrinaan usernamen
//return restTemplate.getForObject("/user/"+username, DemoUser.class);
//}

///** TRAINING CODE: ------------------ */
//public void addUser(DemoUser demouser) {
//restTemplate.postForObject("/user", demouser ,DemoUser.class);	// postForObject luo uuden resurssin lähettämällä HTTP:n POSTilla objektin (demouser) URI-templateen ja palauttamalla vastauksena saadun esityksen DemoUser.class-tyyppisenä
//}
//
//public void editUser(DemoUser demouser) {
//restTemplate.postForObject("/edituser", demouser ,DemoUser.class);	// postForObject luo uuden resurssin lähettämällä HTTP:n POSTilla objektin (demouser) URI-templateen ja palauttamalla vastauksena saadun esityksen DemoUser.class-tyyppisenä
//}	
//
//public void deleteUser(String id) {
//restTemplate.delete("/nakkivene/"+id);	// poistaa resurssin parametrissa määritetystä urlista.
//}
