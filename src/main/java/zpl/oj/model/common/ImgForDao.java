package zpl.oj.model.common;

import java.util.Date;

public class ImgForDao {
	private Integer id;
	private Integer invitedid;
	private String location;
	private Date time;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getInvitedid() {
		return invitedid;
	}
	public void setInvitedid(Integer invitedid) {
		this.invitedid = invitedid;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}	
}
