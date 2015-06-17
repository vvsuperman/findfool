package zpl.oj.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import zpl.oj.model.common.ProblemTestCase;


//用户端和题目的关联表

public class CadScore {
	private int cadid;
	private int setid;
	private int score;

	
	public CadScore() {
		this.cadid = 0;
		this.setid =0;
		this.score =0;
	}
	
	
	public int getCadid() {
		return cadid;
	}



	public void setCadid(int cadid) {
		this.cadid = cadid;
	}



	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	

	public int getSetid() {
		return setid;
	}

	public void setSetid(int setid) {
		this.setid = setid;
	}

	

	
	
}
