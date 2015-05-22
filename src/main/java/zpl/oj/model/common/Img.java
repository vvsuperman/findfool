package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Img implements Serializable {
	private String imgData;
	private String email;
	private Integer invitedid;
	private String state;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getImgData() {
		return imgData;
	}
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getInvitedid() {
		return invitedid;
	}
	public void setInvitedid(Integer invitedid) {
		this.invitedid = invitedid;
	}
	
}

