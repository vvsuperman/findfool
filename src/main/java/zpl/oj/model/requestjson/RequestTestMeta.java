package zpl.oj.model.requestjson;

import java.util.List;

public class RequestTestMeta {

	private RequestUser user;
	private int tid;
	private int testtime;
	private List<Integer> extrainfo;
	private List<String> emails;
	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getTesttime() {
		return testtime;
	}
	public void setTesttime(int testtime) {
		this.testtime = testtime;
	}
	public List<Integer> getExtrainfo() {
		return extrainfo;
	}
	public void setExtrainfo(List<Integer> extrainfo) {
		this.extrainfo = extrainfo;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	
	
}
