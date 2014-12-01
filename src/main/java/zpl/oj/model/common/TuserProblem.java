package zpl.oj.model.common;

import java.util.Date;

public class TuserProblem {

	private int tuid;
	private int problemid;
	private String useranswer;
	

	private String rightanswer;
	
	public TuserProblem() {
		this.tuid = 0;
		this.problemid = 0;
		this.useranswer = "";
		this.rightanswer ="";
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
	
	public String getUseranswer() {
		return useranswer;
	}

	public void setUseranswer(String useranswer) {
		this.useranswer = useranswer;
	}

	public String getRightanswer() {
		return rightanswer;
	}

	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
	}


}
