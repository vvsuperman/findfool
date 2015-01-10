package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultInfo implements Serializable {
	
    public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

	public String getTestCase() {
		return testCase;
	}

	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}

	public String getTestCaseExpected() {
		return testCaseExpected;
	}

	public void setTestCaseExpected(String testCaseResult) {
		this.testCaseExpected = testCaseResult;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	private int testCaseId;
    private String testCase;
    private String testCaseExpected;
    private int score;
	
	public ResultInfo(){
		this.testCaseId = -1;
		this.testCase = "";
		this.testCaseExpected ="";
		this.score =0;
	}
	
	

	
}

