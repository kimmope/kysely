package queta;

public class Question {
	private int qid;
	private String question;
	private int amountOfAnswers;
	private int sumOfAnswers;
	private double average;
	private int factualAnswer;

	public Question() {}
	
	public Question(int qid, String question) {
		this.qid = qid;
		this.question = question;
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

	public int getAmountOfAnswers() {
		return amountOfAnswers;
	}

	public void setAmountOfAnswers(int amountOfAnswers) {
		this.amountOfAnswers = amountOfAnswers;
	}	

	public int getSumOfAnswers() {
		return sumOfAnswers;
	}

	public void setSumOfAnswers(int sumOfAnswers) {
		this.sumOfAnswers = sumOfAnswers;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	public int getFactualAnswer() {
		return factualAnswer;
	}

	public void setFactualAnswer(int factualAnswer) {
		this.factualAnswer = factualAnswer;
	}
}
