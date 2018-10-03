package queta;

public class Question {
	private int qid;
	private String question;
	private int amount_answs;
	private int sum_answers;
	private double average;
	private int true_answer;
	private int type;
	private int amount;
	private int maxlength;
	private int min;
	private int max;
	private int step;
	private String value;
	private String value_1;
	private String value_2;
	private String value_3;
	private String value_4;
	private String value_5;

	public Question() {}
	
	public Question(int qid, String question) {
		this.qid = qid;
		this.question = question;
	}

	public int getAmount_answs() {
		return amount_answs;
	}

	public void setAmount_answs(int amount_answs) {
		this.amount_answs = amount_answs;
	}

	public int getSum_answers() {
		return sum_answers;
	}

	public void setSum_answers(int sum_answers) {
		this.sum_answers = sum_answers;
	}

	public int getTrue_answer() {
		return true_answer;
	}

	public void setTrue_answer(int true_answer) {
		this.true_answer = true_answer;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue_1() {
		return value_1;
	}

	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}

	public String getValue_2() {
		return value_2;
	}

	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}

	public String getValue_3() {
		return value_3;
	}

	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}

	public String getValue_4() {
		return value_4;
	}

	public void setValue_4(String value_4) {
		this.value_4 = value_4;
	}

	public String getValue_5() {
		return value_5;
	}

	public void setValue_5(String value_5) {
		this.value_5 = value_5;
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

	public int getAmountAnsws() {
		return amount_answs;
	}

	public void setAmountAnsws(int amountOfAnswers) {
		this.amount_answs = amountOfAnswers;
	}	

	public int getSumAnswers() {
		return sum_answers;
	}

	public void setSumAnswers(int sumOfAnswers) {
		this.sum_answers = sumOfAnswers;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	public int getTrueAnswer() {
		return true_answer;
	}

	public void setTrueAnswer(int factualAnswer) {
		this.true_answer = factualAnswer;
	}
}
