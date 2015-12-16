package zpl.oj.model.responsejson;

import java.util.List;

import zpl.oj.model.request.Question;


public class ResponseQuizDetail {

	private List<Question> qs;
	
	private int quizid;
	private String name;
	private int testtime;
	private String extrainfo;
	private String emails;
	private int invited;	
	private int process;
	private int finished;
	
	
		
	public ResponseQuizDetail(){
		extrainfo = null;
		emails = null;
		qs = null;
	}
	
	public int getInvited() {
		return invited;
	}
	public void setInvited(int invited) {
		this.invited = invited;
	}
	public int getProcess() {
		return process;
	}
	public void setProcess(int process) {
		this.process = process;
	}
	public int getFinished() {
		return finished;
	}
	public void setFinished(int finished) {
		this.finished = finished;
	}
	
	
	public List<Question> getQs() {
		return qs;
	}
	public void setQs(List<Question> qs) {
		this.qs = qs;
	}

	public int getQuizid() {
		return quizid;
	}
	public void setQuizid(int quizid) {
		this.quizid = quizid;
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
