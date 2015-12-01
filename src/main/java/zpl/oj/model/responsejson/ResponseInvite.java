package zpl.oj.model.responsejson;

import zpl.oj.model.common.Invite;


/*
 * 用户返回测试报告列表的查询
 * */
public class ResponseInvite extends Invite {
	
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	private String email;
	
	public ResponseInvite(){
		super();
		this.email="";
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
