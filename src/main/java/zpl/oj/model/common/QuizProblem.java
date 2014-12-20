package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;

public class QuizProblem implements Serializable {

	
	/**
	 * ${item.comment}
	 */
	private Integer tpid;
	
	/**
	 * 测试id,
	 */
	private Integer quizid;
	
	/**
	 * 问题id,
	 */
	private Integer problemid;
	
	/**
	 * ${item.comment}
	 */
	private Date date;
	
	/**
	 * （1,2,3,4）的形式
	 */
	private String lang;

    public Integer getTpid() {
        return tpid;
    }

    public void setTpid(Integer tpid) {
        this.tpid = tpid;
    }
    
        public Integer getQuizid() {
        return quizid;
    }

    public void setQuizid(Integer quizid) {
        this.quizid = quizid;
    }
    
        public Integer getProblemid() {
        return problemid;
    }

    public void setProblemid(Integer problemid) {
        this.problemid = problemid;
    }
    
        public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
        public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
    
}