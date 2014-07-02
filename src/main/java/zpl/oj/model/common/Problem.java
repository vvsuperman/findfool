package zpl.oj.model.common;

import java.io.Serializable;

import java.util.Date;

public class Problem implements Serializable {

	public Problem(){
		uuid=-1;
	}
	
	/**
	 * 全局统一id,
	 */
	private Integer uuid;
	
	/**
	 * 问题的id,
	 */
	private Integer problemId;
	
	/**
	 * 问题的题目,
	 */
	private String title;
	
	/**
	 * 问题的描述,
	 */
	private String description;
	
	/**
	 * 产生的时间,
	 */
	private Date date;
	
	/**
	 * 试题集id,
	 */
	private Integer problemSetId;
	
	public int getCreator() {
		return creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
	}

	/**
	 * 1：选择题
	 */
	private Integer type;
	
	/**
	 * 时间限制,
	 */
	private Integer limitTime;
	
	/**
	 * 内存限制,
	 */
	private Integer limitMem;
	
	/**
	 * 总共被人做的次数,
	 */
	private Integer submit;
	
	/**
	 * 做对了的人次数,
	 */
	private Integer sloved;
	
	/**
	 * 修改者时间,
	 */
	private Date modifydate;
	private int modifier;
	
	private int creator;

    public int getModifier() {
		return modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }
    
        public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }
    
        public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
        public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
        public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
        public Integer getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(Integer problemSetId) {
        this.problemSetId = problemSetId;
    }
    
        public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
        public Integer getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Integer limitTime) {
        this.limitTime = limitTime;
    }
    
        public Integer getLimitMem() {
        return limitMem;
    }

    public void setLimitMem(Integer limitMem) {
        this.limitMem = limitMem;
    }
    
        public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }
    
        public Integer getSloved() {
        return sloved;
    }

    public void setSloved(Integer sloved) {
        this.sloved = sloved;
    }
    
        public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }
    
}