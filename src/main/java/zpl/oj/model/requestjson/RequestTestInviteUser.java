package zpl.oj.model.requestjson;

import java.util.List;

import zpl.oj.model.common.InviteUser;

public class RequestTestInviteUser {

	private RequestUser user;
	private int tid;
	private String subject;
	private String replyTo;
	private List<InviteUser> invite;
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
	public List<InviteUser> getInvite() {
		return invite;
	}
	public void setInvite(List<InviteUser> invite) {
		this.invite = invite;
	}
	
}
