package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;

public class Quiz implements Serializable {

	
	/**
	 * ${item.comment}
	 */
	private Integer quizid;
	
	/**
	 * ${item.comment}
	 */
	private Integer owner;
	
	/**
	 * 测试的名字,
	 */
	private String name;
	
	/**
	 * 创建的时间,
	 */
	private Date date;
	
	/**
	 * 该测试所需的时间，默认70分钟,
	 */
	private Integer time;
	
	/**
	 * 1,2,3,4
	 */
	private String extraInfo;
	
	/**
	 * 全局唯一识别符，只要uuid相同的，那么都是同一个测试，以时间最新的为标准,
	 */
	private Integer uuid;
	
	/**
	 * 这个里面是测试报告发送地址的邮件，使用逗号隔开,
	 */
	private String emails;
	
	//统计数据：题量
	private int questionNum;
	//统计数据：邀请数
	private int invitedNum;
	//统计数据：完成数
	private int finishedNum;
	
	
	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public int getInvitedNum() {
		return invitedNum;
	}

	public void setInvitedNum(int invitedNum) {
		this.invitedNum = invitedNum;
	}

	public int getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(int finishedNum) {
		this.finishedNum = finishedNum;
	}


	

    public Integer getQuizid() {
        return quizid;
    }

    public void setQuizid(Integer quizid) {
        this.quizid = quizid;
    }
    
        public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }
    
        public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
        public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
        public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
    
        public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
    
        public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }
    
    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    
}