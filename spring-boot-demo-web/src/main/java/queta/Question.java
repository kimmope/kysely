package queta;

public class Question {
	private int qid;
	private String question;
	private int type;	
	private int maxLength;
	private int min;
	private String minTitle;
	private int max;
	private String maxTitle;
	private int step;
	private int subQuestAmnt;
	private String defVal1;
	private String defVal2;
	private String defVal3;
	private String defVal4;
	private String defVal5;
	private String colHead1;
	private String colHead2;
	private String colHead3;
	private String colHead4;
	private String colHead5;
	private String rowHead1;
	private String rowHead2;
	private String rowHead3;
	private String rowHead4;
	private String rowHead5;
	private String message;
	private String info;
	private int amntAnswTot;
	private int amntAnswVal1;
	private int amntAnswVal2;
	private int amntAnswVal3;
	private int amntAnswVal4;
	private int amntAnswVal5;
	private int sumAnswers;
	private double average;
	private double mean;
	private double mode;	
	private String trueAnswer;	
	
	public Question() {}

	public Question(int qid, String question) {
		this.qid = qid;
		this.question = question;
	}

	public Question(int qid, String question, int type, int maxLength, int min, String minTitle, int max,
			String maxTitle, int step, int subQuestAmnt, String defVal1, String defVal2, String defVal3, String defVal4, String defVal5,
			String colHead1, String colHead2, String colHead3, String colHead4, String colHead5, String rowHead1,
			String rowHead2, String rowHead3, String rowHead4, String rowHead5, String message, String info,
			int amntAnswTot, int amntAnswVal1, int amntAnswVal2, int amntAnswVal3, int amntAnswVal4, int amntAnswVal5,
			int sumAnswers, double average, double mean, double mode, String trueAnswer) {
		super();
		this.qid = qid;
		this.question = question;
		this.type = type;
		this.maxLength = maxLength;
		this.min = min;
		this.minTitle = minTitle;
		this.max = max;
		this.maxTitle = maxTitle;
		this.step = step;
		this.subQuestAmnt = subQuestAmnt;
		this.defVal1 = defVal1;
		this.defVal2 = defVal2;
		this.defVal3 = defVal3;
		this.defVal4 = defVal4;
		this.defVal5 = defVal5;
		this.colHead1 = colHead1;
		this.colHead2 = colHead2;
		this.colHead3 = colHead3;
		this.colHead4 = colHead4;
		this.colHead5 = colHead5;
		this.rowHead1 = rowHead1;
		this.rowHead2 = rowHead2;
		this.rowHead3 = rowHead3;
		this.rowHead4 = rowHead4;
		this.rowHead5 = rowHead5;
		this.message = message;
		this.info = info;
		this.amntAnswTot = amntAnswTot;
		this.amntAnswVal1 = amntAnswVal1;
		this.amntAnswVal2 = amntAnswVal2;
		this.amntAnswVal3 = amntAnswVal3;
		this.amntAnswVal4 = amntAnswVal4;
		this.amntAnswVal5 = amntAnswVal5;
		this.sumAnswers = sumAnswers;
		this.average = average;
		this.mean = mean;
		this.mode = mode;
		this.trueAnswer = trueAnswer;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getMinTitle() {
		return minTitle;
	}

	public void setMinTitle(String minTitle) {
		this.minTitle = minTitle;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getMaxTitle() {
		return maxTitle;
	}

	public void setMaxTitle(String maxTitle) {
		this.maxTitle = maxTitle;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getSubQuestAmnt() {
		return subQuestAmnt;
	}

	public void setSubQuestAmnt(int subQuestAmnt) {
		this.subQuestAmnt = subQuestAmnt;
	}

	public String getDefVal1() {
		return defVal1;
	}

	public void setDefVal1(String defVal1) {
		this.defVal1 = defVal1;
	}

	public String getDefVal2() {
		return defVal2;
	}

	public void setDefVal2(String defVal2) {
		this.defVal2 = defVal2;
	}

	public String getDefVal3() {
		return defVal3;
	}

	public void setDefVal3(String defVal3) {
		this.defVal3 = defVal3;
	}

	public String getDefVal4() {
		return defVal4;
	}

	public void setDefVal4(String defVal4) {
		this.defVal4 = defVal4;
	}

	public String getDefVal5() {
		return defVal5;
	}

	public void setDefVal5(String defVal5) {
		this.defVal5 = defVal5;
	}

	public String getColHead1() {
		return colHead1;
	}

	public void setColHead1(String colHead1) {
		this.colHead1 = colHead1;
	}

	public String getColHead2() {
		return colHead2;
	}

	public void setColHead2(String colHead2) {
		this.colHead2 = colHead2;
	}

	public String getColHead3() {
		return colHead3;
	}

	public void setColHead3(String colHead3) {
		this.colHead3 = colHead3;
	}

	public String getColHead4() {
		return colHead4;
	}

	public void setColHead4(String colHead4) {
		this.colHead4 = colHead4;
	}

	public String getColHead5() {
		return colHead5;
	}

	public void setColHead5(String colHead5) {
		this.colHead5 = colHead5;
	}

	public String getRowHead1() {
		return rowHead1;
	}

	public void setRowHead1(String rowHead1) {
		this.rowHead1 = rowHead1;
	}

	public String getRowHead2() {
		return rowHead2;
	}

	public void setRowHead2(String rowHead2) {
		this.rowHead2 = rowHead2;
	}

	public String getRowHead3() {
		return rowHead3;
	}

	public void setRowHead3(String rowHead3) {
		this.rowHead3 = rowHead3;
	}

	public String getRowHead4() {
		return rowHead4;
	}

	public void setRowHead4(String rowHead4) {
		this.rowHead4 = rowHead4;
	}

	public String getRowHead5() {
		return rowHead5;
	}

	public void setRowHead5(String rowHead5) {
		this.rowHead5 = rowHead5;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getAmntAnswTot() {
		return amntAnswTot;
	}

	public void setAmntAnswTot(int amntAnswTot) {
		this.amntAnswTot = amntAnswTot;
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

	public int getSumAnswers() {
		return sumAnswers;
	}

	public void setSumAnswers(int sumAnswers) {
		this.sumAnswers = sumAnswers;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMode() {
		return mode;
	}

	public void setMode(double mode) {
		this.mode = mode;
	}

	public String getTrueAnswer() {
		return trueAnswer;
	}

	public void setTrueAnswer(String trueAnswer) {
		this.trueAnswer = trueAnswer;
	}		
	
}