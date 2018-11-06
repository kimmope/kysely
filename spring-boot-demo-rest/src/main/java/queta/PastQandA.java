package queta;
import java.sql.Timestamp;

public class PastQandA {
	private int uid;
	private int qid;
	private String question;
	private Timestamp timeOfAnswer;
	private String answer;
	
	public PastQandA() {}
	
	public PastQandA(int uid, int qid, String question, Timestamp timeOfAnswer, String answer) {
		super();
		this.uid = uid;
		this.qid = qid;
		this.question = question;
		this.timeOfAnswer = timeOfAnswer;
		this.answer = answer;
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
	public Timestamp getTimeOfAnswer() {
		return timeOfAnswer;
	}
	public void setTimeOfAnswer(Timestamp timeOfAnswer) {
		this.timeOfAnswer = timeOfAnswer;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
