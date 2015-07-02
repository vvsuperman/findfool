package zpl.oj.model.common;

public class ChallengeRule {
	int crid;
	int domainid;//红，白，蓝 :1,2,3
	
	int testid;//测试的id
	int cutoffnum;//可做题数
	public int getCutoffnum() {
		return cutoffnum;
	}
	public void setCutoffnum(int cutoffnum) {
		this.cutoffnum = cutoffnum;
	}
	String testname ;//测试的名称
	double plus;
	
	
	
	public int getDomainid() {
		return domainid;
	}
	public void setDomainid(int domainid) {
		this.domainid = domainid;
	}
	
	public String getTestname() {
		return testname;
	}
	public void setTestname(String testname) {
		this.testname = testname;
	}
	public double getPlus() {
		return plus;
	}
	public void setPlus(double plus) {
		this.plus = plus;
	}
	public int getCrid() {
		return crid;
	}
	public void setCrid(int crid) {
		this.crid = crid;
	}
	public int getDomid() {
		return domainid;
	}
	public void setDomid(int domid) {
		this.domainid = domid;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public int getCutoffscore() {
		return cutoffnum;
	}
	public void setCutoffscore(int cutoffscore) {
		this.cutoffnum = cutoffscore;
	}
	

}
