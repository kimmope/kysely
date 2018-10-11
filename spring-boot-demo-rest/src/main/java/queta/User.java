package queta;

public class User {
	private int uid;
	private String username;
	private int amntUserAnsw;
	private int score;
	
	public User(){
	}
		
	public User(int uid, String username, int amntUserAnsw, int score) {
		super();
		this.uid = uid;
		this.username = username;
		this.amntUserAnsw = amntUserAnsw;
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

	public int getAmntUserAnsw() {
		return amntUserAnsw;
	}

	public void setAmntUserAnsw(int amntUserAnsw) {
		this.amntUserAnsw = amntUserAnsw;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
