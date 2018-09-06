package queta;

public class Answer {

	private int uid;
	private int qid;
	private String answer;

	public Answer() {}
	
	public Answer(int uid, int qid, String answer) {
		super();
		this.uid = uid;
		this.qid = qid;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
