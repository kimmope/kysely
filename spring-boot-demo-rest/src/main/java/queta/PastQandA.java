package queta;
import java.sql.Timestamp;

public class PastQandA {
	private int uid;
	private int qid;
	private String question;
	private Timestamp timeOfAnsw;
	private String answer1;
	
	public PastQandA() {}
	
	public PastQandA(int uid, int qid, String question, Timestamp timeOfAnsw, String answer1) {
		super();
		this.uid = uid;
		this.qid = qid;
		this.question = question;
		this.timeOfAnsw = timeOfAnsw;
		this.answer1 = answer1;
	}

	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Timestamp getTimeOfAnsw() {
		return timeOfAnsw;
	}
	public void setTimeOfAnsw(Timestamp timeOfAnsw) {
		this.timeOfAnsw = timeOfAnsw;
	}
	public String getAnswer1() {
		return answer1;
	}
	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}
	
}
