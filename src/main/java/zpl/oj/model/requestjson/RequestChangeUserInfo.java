package zpl.oj.model.requestjson;

public class RequestChangeUserInfo {

	private RequestUser user;
	private String email;
	private String name;
	private String company;
	private String tel;
	private String pwd;
	private String newPWD;
	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getNewPWD() {
		return newPWD;
	}
	public void setNewPWD(String newPWD) {
		this.newPWD = newPWD;
	}
	
	
}
