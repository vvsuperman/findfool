package zpl.oj.model.responsejson;

import java.util.List;

import zpl.oj.model.request.Question;


public class ResponseQuizDetail {

	private List<Question> qs;
	
	private int quizid;
	private int testtime;
	private String extrainfo;
	private String emails;
	public ResponseQuizDetail(){
		extrainfo = null;
		emails = null;
		qs = null;
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
	
	
}
