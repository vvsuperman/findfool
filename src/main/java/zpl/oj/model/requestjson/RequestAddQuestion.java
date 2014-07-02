package zpl.oj.model.requestjson;

import zpl.oj.model.request.Question;

public class RequestAddQuestion {
	private RequestUser user;

	private Question question;

	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	
}
