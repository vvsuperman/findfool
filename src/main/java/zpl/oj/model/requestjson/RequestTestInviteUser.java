package zpl.oj.model.requestjson;

import java.util.Date;
import java.util.List;

import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;

public class RequestTestInviteUser {

	private RequestUser user;
	private Integer quizid;
	private String subject;
	private String replyTo;
	private String context;
	private String duration;
	private String system;   //来源系统，paas服务用
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	private String starttime; //试题的开始时间
	private List<InviteUser> invite;	
	
	
    public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getDeadtime() {
		return deadtime;
	}
	public void setDeadtime(String deadtime) {
		this.deadtime = deadtime;
	}
	private String deadtime;   //试题的结束时间
	
	
	
	
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
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
	public List<InviteUser> getInvite() {
		return invite;
	}
	public void setInvite(List<InviteUser> testusers) {
		this.invite = testusers;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	
}
