package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Suggest implements Serializable {
	
	private int sid;
	private int uid;
	private String content;
	private String suggestTime;
	private String email;
	
	


	public Suggest(){
		this.uid =0;
		this.content ="";
		this.suggestTime ="";
		this.email = "";
	}
	
	
	public String getUsername() {
		return email;
	}


	public void setUsername(String username) {
		this.email = username;
	}
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSeggestTime() {
		return suggestTime;
	}
	public void setSeggestTime(String seggestTime) {
		this.suggestTime = seggestTime;
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
}

