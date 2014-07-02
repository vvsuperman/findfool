package zpl.oj.model.requestjson;

import java.util.List;
public class RequestTestSubmit {

	private int testid;
	private List<Integer> qids;
	private RequestUser user;


	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public List<Integer> getQids() {
		return qids;
	}
	public void setQids(List<Integer> qids) {
		this.qids = qids;
	}
	
}
