package zpl.oj.model.request;

import java.util.Date;

public class User {

	private int uid;
	private String fname;
	private String lname;
	private String email;
	private String company;
	private int privilege;
	private String pwd;
	private String link;
	private String age;
	private String degree;
	private String school;
	private Date registerDate;
	private Date lastLoginDate;
	private int invited_left;
	private int invitedNum;
	private int state;
	private String tel;
	private String mdUid;// 明道的uid
	private String resetUrl; // 重置密码时随机生成的url
	private int companyId;
	private String userSource; //客户来源
	private String companyName; //公司名

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public String getResetUrl() {
		return resetUrl;
	}

	public void setResetUrl(String resetUrl) {
		this.resetUrl = resetUrl;
	}

	public String getMdUid() {
		return mdUid;
	}

	public void setMdUid(String mdUid) {
		this.mdUid = mdUid;
	}

	public User() {
		this.fname = "no value";
		this.lname = "no value";
		this.email = "no value";
		this.company = "no value";
		this.privilege = 0;
		this.pwd = "no value";
		this.link = "no value";
		this.age = "no value";
		this.degree = "no value";
		this.school = "no value";
		this.registerDate = new Date();
		this.lastLoginDate = new Date();
		this.invited_left = 0;
		this.invitedNum = 0;
		this.state = 1;
		this.tel = "no value";
		this.userSource = "";
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getInvitedNum() {
		return invitedNum;
	}

	public void setInvitedNum(int invitedNum) {
		this.invitedNum = invitedNum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getInvited_left() {
		return invited_left;
	}

	public void setInvited_left(int invited_left) {
		this.invited_left = invited_left;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

}
