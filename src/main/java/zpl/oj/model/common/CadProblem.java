package zpl.oj.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpl.oj.model.common.ProblemTestCase;


//用户端和题目的关联表

public class CadProblem {

	private int cadid;
	private Integer problemid;
	private String useranswer;
	private int ctid;  //cadidate与test关联表主键
	
	private int solutionid;   //编程题的运行ID

	
	
	public int getCadid() {
		return cadid;
	}



	public void setCadid(int cadid) {
		this.cadid = cadid;
	}




	


	public CadProblem() {
		this.cadid = 0;
		this.problemid = 0;
		this.useranswer = "";
		this.ctid =0;
	}
	
	
	



	public int getSolutionId() {
		return solutionid;
	}



	public void setSolutionId(int solutionId) {
		this.solutionid = solutionId;
	}


	public int getCtid() {
		return ctid;
	}

	public void setCtid(int ctid) {
		this.ctid = ctid;
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


}
