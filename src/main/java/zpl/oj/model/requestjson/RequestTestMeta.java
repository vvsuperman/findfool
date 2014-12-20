package zpl.oj.model.requestjson;


public class RequestTestMeta {

	private RequestUser user;
	private int quizid;
	private String name;
	private int testtime;
	private String extrainfo;
	private String emails;
	public int getQuizid() {
		return quizid;
	}
	public void setQuizid(int quizid) {
		this.quizid = quizid;
	}
	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
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
