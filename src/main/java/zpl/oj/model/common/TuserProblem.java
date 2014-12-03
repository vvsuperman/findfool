package zpl.oj.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpl.oj.model.common.ProblemTestCase;

public class TuserProblem {

	private int tuid;
	private int problemid;
	private String useranswer;
	private int type;
	private String rightanswer;
	private List<ProblemTestCase> options;
	
	

	public TuserProblem() {
		this.tuid = 0;
		this.problemid = 0;
		this.useranswer = "";
		this.rightanswer ="";
		this.options = new ArrayList();
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public List<ProblemTestCase> getOptions() {
		return options;
	}

	public void setOptions(List<ProblemTestCase> options) {
		this.options = options;
	}


}
