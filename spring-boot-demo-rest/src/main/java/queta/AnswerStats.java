package queta;
import java.sql.Timestamp;

public class AnswerStats {
	private int amountTot;
	private int amountP1;
	private int amountP2;
	private int amountP3;
	private int amountP4;
	private int amountP5;
	private double meanAll;
	private double meanP1;
	private double meanP2;
	private double meanP3;
	private double meanP4;
	private double meanP5;
	private double mediAll;
	private double mediP1;
	private double mediP2;
	private double mediP3;
	private double mediP4;
	private double mediP5;
	private double modeAll;
	private double modeP1;
	private double modeP2;
	private double modeP3;
	private double modeP4;
	private double modeP5;
	private int class1Tot;
	private int class1P1;
	private int class1P2;
	private int class1P3;
	private int class1P4;
	private int class1P5;
	private int class2Tot;
	private int class2P1;
	private int class2P2;
	private int class2P3;
	private int class2P4;
	private int class2P5;
	private int class3Tot;
	private int class3P1;
	private int class3P2;
	private int class3P3;
	private int class3P4;
	private int class3P5;
	private int class4Tot;
	private int class4P1;
	private int class4P2;
	private int class4P3;
	private int class4P4;
	private int class4P5;
	private int class5Tot;
	private int class5P1;
	private int class5P2;
	private int class5P3;
	private int class5P4;
	private int class5P5;
	
	public AnswerStats(int amountTot, int amountP1, int amountP2, int amountP3, int amountP4, int amountP5,
			double meanAll, double meanP1, double meanP2, double meanP3, double meanP4, double meanP5, double mediAll,
			double mediP1, double mediP2, double mediP3, double mediP4, double mediP5, double modeAll, double modeP1,
			double modeP2, double modeP3, double modeP4, double modeP5, int class1Tot, int class1p1, int class1p2,
			int class1p3, int class1p4, int class1p5, int class2Tot, int class2p1, int class2p2, int class2p3,
			int class2p4, int class2p5, int class3Tot, int class3p1, int class3p2, int class3p3, int class3p4,
			int class3p5, int class4Tot, int class4p1, int class4p2, int class4p3, int class4p4, int class4p5,
			int class5Tot, int class5p1, int class5p2, int class5p3, int class5p4, int class5p5) {
		super();
		this.amountTot = amountTot;
		this.amountP1 = amountP1;
		this.amountP2 = amountP2;
		this.amountP3 = amountP3;
		this.amountP4 = amountP4;
		this.amountP5 = amountP5;
		this.meanAll = meanAll;
		this.meanP1 = meanP1;
		this.meanP2 = meanP2;
		this.meanP3 = meanP3;
		this.meanP4 = meanP4;
		this.meanP5 = meanP5;
		this.mediAll = mediAll;
		this.mediP1 = mediP1;
		this.mediP2 = mediP2;
		this.mediP3 = mediP3;
		this.mediP4 = mediP4;
		this.mediP5 = mediP5;
		this.modeAll = modeAll;
		this.modeP1 = modeP1;
		this.modeP2 = modeP2;
		this.modeP3 = modeP3;
		this.modeP4 = modeP4;
		this.modeP5 = modeP5;
		this.class1Tot = class1Tot;
		this.class1P1 = class1p1;
		this.class1P2 = class1p2;
		this.class1P3 = class1p3;
		this.class1P4 = class1p4;
		this.class1P5 = class1p5;
		this.class2Tot = class2Tot;
		this.class2P1 = class2p1;
		this.class2P2 = class2p2;
		this.class2P3 = class2p3;
		this.class2P4 = class2p4;
		this.class2P5 = class2p5;
		this.class3Tot = class3Tot;
		this.class3P1 = class3p1;
		this.class3P2 = class3p2;
		this.class3P3 = class3p3;
		this.class3P4 = class3p4;
		this.class3P5 = class3p5;
		this.class4Tot = class4Tot;
		this.class4P1 = class4p1;
		this.class4P2 = class4p2;
		this.class4P3 = class4p3;
		this.class4P4 = class4p4;
		this.class4P5 = class4p5;
		this.class5Tot = class5Tot;
		this.class5P1 = class5p1;
		this.class5P2 = class5p2;
		this.class5P3 = class5p3;
		this.class5P4 = class5p4;
		this.class5P5 = class5p5;
	}

	public int getAmountTot() {
		return amountTot;
	}

	public void setAmountTot(int amountTot) {
		this.amountTot = amountTot;
	}

	public int getAmountP1() {
		return amountP1;
	}

	public void setAmountP1(int amountP1) {
		this.amountP1 = amountP1;
	}

	public int getAmountP2() {
		return amountP2;
	}

	public void setAmountP2(int amountP2) {
		this.amountP2 = amountP2;
	}

	public int getAmountP3() {
		return amountP3;
	}

	public void setAmountP3(int amountP3) {
		this.amountP3 = amountP3;
	}

	public int getAmountP4() {
		return amountP4;
	}

	public void setAmountP4(int amountP4) {
		this.amountP4 = amountP4;
	}

	public int getAmountP5() {
		return amountP5;
	}

	public void setAmountP5(int amountP5) {
		this.amountP5 = amountP5;
	}

	public double getMeanAll() {
		return meanAll;
	}

	public void setMeanAll(double meanAll) {
		this.meanAll = meanAll;
	}

	public double getMeanP1() {
		return meanP1;
	}

	public void setMeanP1(double meanP1) {
		this.meanP1 = meanP1;
	}

	public double getMeanP2() {
		return meanP2;
	}

	public void setMeanP2(double meanP2) {
		this.meanP2 = meanP2;
	}

	public double getMeanP3() {
		return meanP3;
	}

	public void setMeanP3(double meanP3) {
		this.meanP3 = meanP3;
	}

	public double getMeanP4() {
		return meanP4;
	}

	public void setMeanP4(double meanP4) {
		this.meanP4 = meanP4;
	}

	public double getMeanP5() {
		return meanP5;
	}

	public void setMeanP5(double meanP5) {
		this.meanP5 = meanP5;
	}

	public double getMediAll() {
		return mediAll;
	}

	public void setMediAll(double mediAll) {
		this.mediAll = mediAll;
	}

	public double getMediP1() {
		return mediP1;
	}

	public void setMediP1(double mediP1) {
		this.mediP1 = mediP1;
	}

	public double getMediP2() {
		return mediP2;
	}

	public void setMediP2(double mediP2) {
		this.mediP2 = mediP2;
	}

	public double getMediP3() {
		return mediP3;
	}

	public void setMediP3(double mediP3) {
		this.mediP3 = mediP3;
	}

	public double getMediP4() {
		return mediP4;
	}

	public void setMediP4(double mediP4) {
		this.mediP4 = mediP4;
	}

	public double getMediP5() {
		return mediP5;
	}

	public void setMediP5(double mediP5) {
		this.mediP5 = mediP5;
	}

	public double getModeAll() {
		return modeAll;
	}

	public void setModeAll(double modeAll) {
		this.modeAll = modeAll;
	}

	public double getModeP1() {
		return modeP1;
	}

	public void setModeP1(double modeP1) {
		this.modeP1 = modeP1;
	}

	public double getModeP2() {
		return modeP2;
	}

	public void setModeP2(double modeP2) {
		this.modeP2 = modeP2;
	}

	public double getModeP3() {
		return modeP3;
	}

	public void setModeP3(double modeP3) {
		this.modeP3 = modeP3;
	}

	public double getModeP4() {
		return modeP4;
	}

	public void setModeP4(double modeP4) {
		this.modeP4 = modeP4;
	}

	public double getModeP5() {
		return modeP5;
	}

	public void setModeP5(double modeP5) {
		this.modeP5 = modeP5;
	}

	public int getClass1Tot() {
		return class1Tot;
	}

	public void setClass1Tot(int class1Tot) {
		this.class1Tot = class1Tot;
	}

	public int getClass1P1() {
		return class1P1;
	}

	public void setClass1P1(int class1p1) {
		class1P1 = class1p1;
	}

	public int getClass1P2() {
		return class1P2;
	}

	public void setClass1P2(int class1p2) {
		class1P2 = class1p2;
	}

	public int getClass1P3() {
		return class1P3;
	}

	public void setClass1P3(int class1p3) {
		class1P3 = class1p3;
	}

	public int getClass1P4() {
		return class1P4;
	}

	public void setClass1P4(int class1p4) {
		class1P4 = class1p4;
	}

	public int getClass1P5() {
		return class1P5;
	}

	public void setClass1P5(int class1p5) {
		class1P5 = class1p5;
	}

	public int getClass2Tot() {
		return class2Tot;
	}

	public void setClass2Tot(int class2Tot) {
		this.class2Tot = class2Tot;
	}

	public int getClass2P1() {
		return class2P1;
	}

	public void setClass2P1(int class2p1) {
		class2P1 = class2p1;
	}

	public int getClass2P2() {
		return class2P2;
	}

	public void setClass2P2(int class2p2) {
		class2P2 = class2p2;
	}

	public int getClass2P3() {
		return class2P3;
	}

	public void setClass2P3(int class2p3) {
		class2P3 = class2p3;
	}

	public int getClass2P4() {
		return class2P4;
	}

	public void setClass2P4(int class2p4) {
		class2P4 = class2p4;
	}

	public int getClass2P5() {
		return class2P5;
	}

	public void setClass2P5(int class2p5) {
		class2P5 = class2p5;
	}

	public int getClass3Tot() {
		return class3Tot;
	}

	public void setClass3Tot(int class3Tot) {
		this.class3Tot = class3Tot;
	}

	public int getClass3P1() {
		return class3P1;
	}

	public void setClass3P1(int class3p1) {
		class3P1 = class3p1;
	}

	public int getClass3P2() {
		return class3P2;
	}

	public void setClass3P2(int class3p2) {
		class3P2 = class3p2;
	}

	public int getClass3P3() {
		return class3P3;
	}

	public void setClass3P3(int class3p3) {
		class3P3 = class3p3;
	}

	public int getClass3P4() {
		return class3P4;
	}

	public void setClass3P4(int class3p4) {
		class3P4 = class3p4;
	}

	public int getClass3P5() {
		return class3P5;
	}

	public void setClass3P5(int class3p5) {
		class3P5 = class3p5;
	}

	public int getClass4Tot() {
		return class4Tot;
	}

	public void setClass4Tot(int class4Tot) {
		this.class4Tot = class4Tot;
	}

	public int getClass4P1() {
		return class4P1;
	}

	public void setClass4P1(int class4p1) {
		class4P1 = class4p1;
	}

	public int getClass4P2() {
		return class4P2;
	}

	public void setClass4P2(int class4p2) {
		class4P2 = class4p2;
	}

	public int getClass4P3() {
		return class4P3;
	}

	public void setClass4P3(int class4p3) {
		class4P3 = class4p3;
	}

	public int getClass4P4() {
		return class4P4;
	}

	public void setClass4P4(int class4p4) {
		class4P4 = class4p4;
	}

	public int getClass4P5() {
		return class4P5;
	}

	public void setClass4P5(int class4p5) {
		class4P5 = class4p5;
	}

	public int getClass5Tot() {
		return class5Tot;
	}

	public void setClass5Tot(int class5Tot) {
		this.class5Tot = class5Tot;
	}

	public int getClass5P1() {
		return class5P1;
	}

	public void setClass5P1(int class5p1) {
		class5P1 = class5p1;
	}

	public int getClass5P2() {
		return class5P2;
	}

	public void setClass5P2(int class5p2) {
		class5P2 = class5p2;
	}

	public int getClass5P3() {
		return class5P3;
	}

	public void setClass5P3(int class5p3) {
		class5P3 = class5p3;
	}

	public int getClass5P4() {
		return class5P4;
	}

	public void setClass5P4(int class5p4) {
		class5P4 = class5p4;
	}

	public int getClass5P5() {
		return class5P5;
	}

	public void setClass5P5(int class5p5) {
		class5P5 = class5p5;
	}	
	
}

