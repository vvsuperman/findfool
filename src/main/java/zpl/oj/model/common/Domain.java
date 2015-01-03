package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Domain implements Serializable {
	private int domainId;
	private String domainName;
	private List<ProblemSet> problemSets;
	
	
	public Domain(){
		domainId = -1;
		domainName = "";
		problemSets = new ArrayList<ProblemSet>();
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

