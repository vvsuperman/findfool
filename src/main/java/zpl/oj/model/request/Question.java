package zpl.oj.model.request;

import java.util.List;

public class Question {

	private Integer qid;
	private String name;
	private int type;
	private List<String> tag;
	private String context;
	private List<QuestionTestCase> answer;
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public List<QuestionTestCase> getAnswer() {
		return answer;
	}
	public void setAnswer(List<QuestionTestCase> answer) {
		this.answer = answer;
	}
	public List<String> getTag() {
		return tag;
	}
	public void setTag(List<String> tag) {
		this.tag = tag;
	}
	
}
