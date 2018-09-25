package queta;
import java.sql.Timestamp;

public class PastQandA {
	private int uid;
	private int qid;
	private String question;
	private Timestamp time_of_answ;
	private String answer;
	
	public PastQandA() {}
	
	public PastQandA(int uid, int qid, String question, Timestamp time_of_answ, String answer) {
		super();
		this.uid = uid;
		this.qid = qid;
		this.question = question;
		this.time_of_answ = time_of_answ;
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
	public Timestamp getTime_of_answ() {
		return time_of_answ;
	}
	public void setTime_of_answ(Timestamp time_of_answ) {
		this.time_of_answ = time_of_answ;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
