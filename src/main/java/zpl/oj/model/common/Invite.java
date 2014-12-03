package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;


public class Invite implements Serializable {
	
	public Invite() {
		this.iid = 0;
		this.testid = 0;
		this.hrid = 0;
		this.uid = 0;
		this.invitetime = "";
		this.duration = "";
		this.begintime = "";
		this.finishtime = "";
		this.score = "";
		this.state = 1;
	}

	/**
	 * ${item.comment}
	 */
	private Integer iid;
	
	/**
	 * 测试的id,
	 */
	private Integer testid;
	
	/**
	 * 测试的hr,
	 */
	private Integer hrid;
	
	/**
	 * 被邀请的用户id，如果该email第一次邀请，则系统自动注册,
	 */
	private Integer uid;
	
	/**
	 * 邀请发出去的时间,
	 */
	private String invitetime;
	
	
	/**
	 * 做题时间,
	 */
	private String duration;
	

	/**
	 * 邀请发出去的时间,
	 */
	private String begintime;

	/**
	 * 用户完成的时间，如果是0表示没有完成,
	 */
	private String finishtime;
	
	/**
	 * 存放的形式为
	 */
	private String score;
	
	
	private Integer state;
	

    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }
    
        public Integer getTestid() {
        return testid;
    }

    public void setTestid(Integer testid) {
        this.testid = testid;
    }
    
        public Integer getHrid() {
        return hrid;
    }

    public void setHrid(Integer hrid) {
        this.hrid = hrid;
    }
    
        public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    
     
    
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    
    public String getInvitetime() {
		return invitetime;
	}

	public void setInvitetime(String invitetime) {
		this.invitetime = invitetime;
	}

	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}
	

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

    
}