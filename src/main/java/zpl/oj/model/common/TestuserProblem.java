package zpl.oj.model.common;

import java.util.Date;

public class TestuserProblem {

	private int tuid;
	
	private int problemid;
	private String answer;
	
	public TestuserProblem() {
		this.tuid = 0;
		this.problemid = 0;
		this.answer = "";
	}
	
	public int getTuid() {
		return tuid;
	}

	public void setTuid(int tuid) {
		this.tuid = tuid;
	}

	public int getProblemid() {
		return problemid;
	}

	public void setProblemid(int problemid) {
		this.problemid = problemid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	
	
	
	
}
