package queta;

public class User {
	private int uid;
	private String username;
	private int amount_answers;
	private int score;
	
	public User(){
	}
		
	public User(int uid, String username, int amount_answers, int score) {
		super();
		this.uid = uid;
		this.username = username;
		this.amount_answers = amount_answers;
		this.score = score;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAmount_answers() {
		return amount_answers;
	}

	public void setAmount_answers(int amount_answers) {
		this.amount_answers = amount_answers;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
