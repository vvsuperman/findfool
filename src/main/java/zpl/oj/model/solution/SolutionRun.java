package zpl.oj.model.solution;

import java.util.Date;

public class SolutionRun {
	int solution_id;
	int user_id;
	int testid;
	int problem_id;
	String solution;
	int language;
	Date date;
	int type;
	public SolutionRun(){
		user_id = 0;
		testid = 0;
		problem_id = 0;
		solution = null;
		language = 0;
		type = 0;
	}
	public int getSolution_id() {
		return solution_id;
	}
	public void setSolution_id(int solution_id) {
		this.solution_id = solution_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public int getProblem_id() {
		return problem_id;
	}
	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	
	
}
