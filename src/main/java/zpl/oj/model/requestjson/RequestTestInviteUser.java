package zpl.oj.model.requestjson;

import java.util.List;

import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;

public class RequestTestInviteUser {

	private RequestUser user;
	private Integer quizid;
	private String subject;
	private String replyTo;
	private String context;
	private List<Testuser> testusers;
	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public Integer getQuizid() {
		return quizid;
	}
	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public List<Testuser> getInvite() {
		return testusers;
	}
	public void setInvite(List<Testuser> testusers) {
		this.testusers = testusers;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
