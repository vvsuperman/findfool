package zpl.oj.model.common;

import java.util.List;

public class ProblemTag {

	private int problemId;
	private List<String> tagContext;
	public int getProblemId() {
		return problemId;
	}
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	public List<String> getTagContext() {
		return tagContext;
	}
	public void setTagContext(List<String> tagContext) {
		this.tagContext = tagContext;
	}
}
