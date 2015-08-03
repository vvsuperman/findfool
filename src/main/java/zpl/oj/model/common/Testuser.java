package zpl.oj.model.common;

import java.util.Date;

public class Testuser {

	private int tuid;    //testuser 的id，总觉得在统计中会有用，先保留
	private String username;
	private String email;
	private String school;
	private String company;
	
	private String blog;
	private int age;

	private String registerDate;
	private String lastLoginDate;
	private String tel;
	private int state;
	private String degree;
	private String faceid;
	
    private String discipline;	//专业
    private String gratime;		//毕业时间
    
    private String city;		//城市
    private String rollnumber;  //年级
    private String gpa;         //gpa
    private String gender;      //性别
    
    
    
    public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getRollnumber() {
		return rollnumber;
	}


	public void setRollnumber(String rollnumber) {
		this.rollnumber = rollnumber;
	}


	public String getGpa() {
		return gpa;
	}


	public void setGpa(String gpa) {
		this.gpa = gpa;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}

	
    
    
    public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private String pwd;			//用户密码
    
    
    public String getDiscipline() {
		return discipline;
	}


	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}


	public String getGratime() {
		return gratime;
	}


	public void setGratime(String gratime) {
		this.gratime = gratime;
	}

	
	
	
	

	

	public Testuser() {
		this.tuid = 0;
		this.username="";
		this.email ="";
		this.school="";
		this.company="";
		this.blog="";
		this.age= 0;
		this.registerDate="";
		this.lastLoginDate="";
		this.tel="";
		this.state=0;
		this.degree="";
	}
	
	
	public String getFaceid() {
		return faceid;
	}

	public void setFaceid(String faceid) {
		this.faceid = faceid;
	}
	
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}
	
	public int getTuid() {
		return tuid;
	}

	public void setTuid(int tuid) {
		this.tuid = tuid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	
	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}

