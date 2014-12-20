package zpl.oj.model.solution;

public class UserTestCase {
	int solution_id;
	String args;
	public int getSolution_id() {
		return solution_id;
	}
	public void setSolution_id(int solution_id) {
		this.solution_id = solution_id;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public UserTestCase(){
		
	}
	public UserTestCase(int solution_id,String args){
		this.solution_id = solution_id;
		this.args = args;
	}
}
