package zpl.oj.model.requestjson;

import java.util.List;

public class RequestTestMeta {

	private RequestUser user;
	private int tid;
	private String name;
	private int testtime;
	private String extrainfo;
	private String emails;
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
	public String getExtrainfo() {
		return extrainfo;
	}
	public void setExtrainfo(String extrainfo) {
		this.extrainfo = extrainfo;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	
}
