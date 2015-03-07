package zpl.oj.model.requestjson;

public class RequestUserLogin {

	private String name;
	private String email;
	private String pwd;
	private String company;
	private String tel;
	
	private String mdUid; //明道的用户ID
	
	
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMdUid() {
		return mdUid;
	}
	public void setMdUid(String mdUid) {
		this.mdUid = mdUid;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
