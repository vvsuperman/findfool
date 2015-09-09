package zpl.oj.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpl.oj.model.common.ProblemTestCase;

public class TuserProblem {

	private int tuid;
	private Integer problemid;
	private String useranswer;
	private int type;
	private String rightanswer;
	private List<ProblemTestCase> options;
	private int setid;
	private int inviteId;
	private int score;
	private int solutionId;   //编程题的运行ID
	private int level;        //题目的等级
	
	private String email ;//只用来传递参数，没有实际作用


	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public TuserProblem() {
		this.tuid = 0;
		this.problemid = 0;
		this.useranswer = "";
		this.rightanswer ="";
		this.options = new ArrayList();
		this.setid =0;
		this.inviteId =0;
		this.score =0;
		this.level =0;
	}
	
	
	
	public int getLevel() {
		return level;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public int getSolutionId() {
		return solutionId;
	}



	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getInviteId() {
		return inviteId;
	}

	public void setInviteId(int inviteId) {
		this.inviteId = inviteId;
	}

	public int getSetid() {
		return setid;
	}

	public void setSetid(int setid) {
		this.setid = setid;
	}

	
	
	public int getTuid() {
		return tuid;
	}

	public void setTuid(int tuid) {
		this.tuid = tuid;
	}

	public Integer getProblemid() {
		return problemid;
	}

	public void setProblemid(Integer problemid) {
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
