package zpl.oj.model.requestjson;

import zpl.oj.model.request.Question;

public class RequestAddQuestion {
	private RequestUser user;

	private Question question;
	//add by fw 20141111


	
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
