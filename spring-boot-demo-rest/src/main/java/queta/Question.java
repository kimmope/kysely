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
	private String rowHead;
	private String message;
	private String info;
	
	public Question() {}

	public Question(int qid, String question) {
		this.qid = qid;
		this.question = question;
	}

	public Question(int qid, String question, int type, int maxLength, int min, String minTitle, int max,
			String maxTitle, int step, String defVal1, String defVal2, String defVal3, String defVal4, String defVal5,
			String colHead1, String colHead2, String colHead3, String colHead4, String colHead5, String rowHead, String message, String info) {
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
		this.rowHead = rowHead;
		this.message = message;
		this.info = info;
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

	public String getRowHead() {
		return rowHead;
	}

	public void setRowHead(String rowHead) {
		this.rowHead = rowHead;
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
	
}