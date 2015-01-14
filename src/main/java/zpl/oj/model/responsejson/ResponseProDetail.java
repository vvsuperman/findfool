package zpl.oj.model.responsejson;

import java.util.ArrayList;
import java.util.List;

import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.ResultInfo;

public class ResponseProDetail {
	public String getUseranswer() {
		return useranswer;
	}
	public void setUseranswer(String useranswer) {
		this.useranswer = useranswer;
	}
	public List<ProblemTestCase> getResultInfos() {
		return resultInfos;
	}
	public void setResultInfos(List<ProblemTestCase> resultInfos) {
		this.resultInfos = resultInfos;
	}
	
	public ResponseProDetail(){
		useranswer  = "";
		resultInfos = new ArrayList<ProblemTestCase>();
		language =0;
	}
	
	private String useranswer;
	private List<ProblemTestCase> resultInfos;
	private int language;
	
	
	public int getLanguage() {
		return language;
	}
	public void setLanguage(int language) {
		this.language = language;
	}
	
	
}
