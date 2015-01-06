package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Domain implements Serializable {
	private int domainId;
	private String domainName;
	private List<ProblemSet> problemSets;
	private int type; //set的类型 1-选择题 2-简答 3-编程
	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Domain(){
		domainId = -1;
		domainName = "";
		problemSets = new ArrayList<ProblemSet>();
		this.type =1;
	}
	
	public int getDomainId() {
		return domainId;
	}
	public void setDomainId(int domainId) {
		this.domainId = domainId;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	public List<ProblemSet> getProblemSets() {
		return problemSets;
	}

	public void setProblemSets(List<ProblemSet> problemSets) {
		this.problemSets = problemSets;
	}

	
}

