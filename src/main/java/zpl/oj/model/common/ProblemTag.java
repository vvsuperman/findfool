package zpl.oj.model.common;

import java.util.List;

public class ProblemTag {

	private int problemId;
	
	private int problemid;
	public int getProblemid() {
		return problemid;
	}
	public void setProblemid(int problemid) {
		this.problemid = problemid;
	}
	public int getTpid() {
		return tpid;
	}
	public void setTpid(int tpid) {
		this.tpid = tpid;
	}
	public int getTagid() {
		return tagid;
	}
	public void setTagid(int tagid) {
		this.tagid = tagid;
	}
	private int tpid;
	private int tagid;
	
	
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
