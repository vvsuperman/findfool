package zpl.oj.model.requestjson;

import java.util.List;

import zpl.oj.model.request.QuestionTestCase;

public class RequestSolution {
	private RequestUser user;
	private Integer quizid;
	private Integer qid;
	private Integer language;
	private Integer type;
	private List<QuestionTestCase> answer;
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
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public List<QuestionTestCase> getAnswer() {
		return answer;
	}
	public void setAnswer(List<QuestionTestCase> answer) {
		this.answer = answer;
	}
	public Integer getLanguage() {
		return language;
	}
	public void setLanguage(Integer language) {
		this.language = language;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
}
