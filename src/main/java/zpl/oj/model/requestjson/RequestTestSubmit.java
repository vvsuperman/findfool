package zpl.oj.model.requestjson;

import java.util.List;
public class RequestTestSubmit {

	private int quizid;
	private List<Integer> qids;
	private RequestUser user;


	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}

	public int getQuizid() {
		return quizid;
	}
	public void setQuizid(int quizid) {
		this.quizid = quizid;
	}
	public List<Integer> getQids() {
		return qids;
	}
	public void setQids(List<Integer> qids) {
		this.qids = qids;
	}
	
}
