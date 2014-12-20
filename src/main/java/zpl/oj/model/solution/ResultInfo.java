package zpl.oj.model.solution;

public class ResultInfo {
	int resultinfo_id;
	int test_case_id;
	int solution_id;
	int cost_time;
	int cost_mem;
	String test_case_result;
	String test_case;
	int score;
	public int getResultinfo_id() {
		return resultinfo_id;
	}
	public void setResultinfo_id(int resultinfo_id) {
		this.resultinfo_id = resultinfo_id;
	}
	public int getTest_case_id() {
		return test_case_id;
	}
	public void setTest_case_id(int test_case_id) {
		this.test_case_id = test_case_id;
	}
	public int getSolution_id() {
		return solution_id;
	}
	public void setSolution_id(int solution_id) {
		this.solution_id = solution_id;
	}
	public int getCost_time() {
		return cost_time;
	}
	public void setCost_time(int cost_time) {
		this.cost_time = cost_time;
	}
	public int getCost_mem() {
		return cost_mem;
	}
	public void setCost_mem(int cost_mem) {
		this.cost_mem = cost_mem;
	}
	public String getTest_case_result() {
		return test_case_result;
	}
	public void setTest_case_result(String test_case_result) {
		this.test_case_result = test_case_result;
	}
	public String getTest_case() {
		return test_case;
	}
	public void setTest_case(String test_case) {
		this.test_case = test_case;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
