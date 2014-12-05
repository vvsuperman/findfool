package zpl.oj.model.solution;

import java.util.ArrayList;
import java.util.List;

public class ReciveSolution {
	int problem_id;
	int language;
	String solution;
	List<ReciveTestCases> user_test_cases;
	int user_id;
	int testid;
	
	public int getTestid() {
		return testid;
	}

	public void setTestid(int testId) {
		this.testid = testId;
	}


	public List<ReciveTestCases> getUser_test_cases() {
		return user_test_cases;
	}


	public ReciveSolution(){
		user_test_cases = new ArrayList<ReciveTestCases>();
		language = 0;
		solution = null;
		problem_id = 0;
		testid =0;
	}
	
	
	public int getProblem_id() {
		return problem_id;
	}


	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}


	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}





	public void setUser_test_cases(List<ReciveTestCases> user_test_cases) {
		this.user_test_cases = user_test_cases;
	}

}
