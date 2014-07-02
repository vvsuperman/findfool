package zpl.oj.model.common;

import java.io.Serializable;

public class ProblemTestCase implements Serializable {

	
	/**
	 * ${item.comment}
	 */
	private Integer testCaseId;
	
	/**
	 * 问题编号,
	 */
	private Integer problemId;
	
	/**
	 * 该测试用例的分数,
	 */
	private Integer score;
	
	/**
	 * 预期的值,
	 */
	private String exceptedRes;
	
	/**
	 * 参数,
	 */
	private String args;
	
	/**
	 * 这个是说明测试用例,
	 */
	private String detail;

    public Integer getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(Integer testCaseId) {
        this.testCaseId = testCaseId;
    }
    
        public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }
    
        public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    
        public String getExceptedRes() {
        return exceptedRes;
    }

    public void setExceptedRes(String exceptedRes) {
        this.exceptedRes = exceptedRes;
    }
    
        public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
    
        public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
