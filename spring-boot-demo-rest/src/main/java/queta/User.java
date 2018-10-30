package queta;

public class User {
	private int uid;
	private String username;
	private int pid;
	private int amntUserAnsw;
	private int score;
	private String province;		
	
	public User(){
	}
		
	public User(int uid, int pid, String username, int amntUserAnsw, int score, String province) {
		super();
		this.uid = uid;
		this.pid = pid;		
		this.username = username;
		this.amntUserAnsw = amntUserAnsw;
		this.score = score;
		this.province = province;		
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
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
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}	
}
