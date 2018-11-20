package queta;

public class Statistics {
	private int amount;
	private double mean;
	private double medi;
	private double mode;
	private String classMode;
	private int amntAnswVal1;
	private int amntAnswVal2;
	private int amntAnswVal3;
	private int amntAnswVal4;
	private int amntAnswVal5;
	private String id;
	
	public Statistics(){
	}

	public Statistics(int amount, double mean, double medi, double mode, String classMode,
			int amntAnswVal1, int amntAnswVal2, int amntAnswVal3, int amntAnswVal4, int amntAnswVal5, String id) {
		super();
		this.amount = amount;
		this.mean = mean;
		this.medi = medi;
		this.mode = mode;
		this.classMode = classMode;
		this.amntAnswVal1 = amntAnswVal1;
		this.amntAnswVal2 = amntAnswVal2;
		this.amntAnswVal3 = amntAnswVal3;
		this.amntAnswVal4 = amntAnswVal4;
		this.amntAnswVal5 = amntAnswVal5;
		this.id = id;		
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMedi() {
		return medi;
	}

	public void setMedi(double medi) {
		this.medi = medi;
	}

	public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

	public String getClassMode() {
		return classMode;
	}

	public void setClassMode(String classMode) {
		this.classMode = classMode;
	}

	public int getAmntAnswVal1() {
		return amntAnswVal1;
	}

	public void setAmntAnswVal1(int amntAnswVal1) {
		this.amntAnswVal1 = amntAnswVal1;
	}

	public int getAmntAnswVal2() {
		return amntAnswVal2;
	}

	public void setAmntAnswVal2(int amntAnswVal2) {
		this.amntAnswVal2 = amntAnswVal2;
	}

	public int getAmntAnswVal3() {
		return amntAnswVal3;
	}

	public void setAmntAnswVal3(int amntAnswVal3) {
		this.amntAnswVal3 = amntAnswVal3;
	}

	public int getAmntAnswVal4() {
		return amntAnswVal4;
	}

	public void setAmntAnswVal4(int amntAnswVal4) {
		this.amntAnswVal4 = amntAnswVal4;
	}

	public int getAmntAnswVal5() {
		return amntAnswVal5;
	}

	public void setAmntAnswVal5(int amntAnswVal5) {
		this.amntAnswVal5 = amntAnswVal5;
	}

	public String getid() {
		return id;
	}

	public void setQid(String id) {
		this.id = id;
	}	
}
