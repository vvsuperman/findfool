package zpl.oj.model.common;


//用户测试关联表，用来记录用户回答题数，分数等
public class CadTest {
	
	   int ctid;
	   int testid;
	   int cadid;
	   double score;
	   int pnums;
	   int nums;
	   public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	int state;
	
	
   public int getCtid() {
		return ctid;
	}
	public void setCtid(int id) {
		this.ctid = id;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public int getCadid() {
		return cadid;
	}
	public void setCadid(int cadid) {
		this.cadid = cadid;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getPnums() {
		return pnums;
	}
	public void setPnums(int pnums) {
		this.pnums = pnums;
	}
	public int getNums() {
		return nums;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}

   public String getBegintime() {
	return begintime;
}
public void setBegintime(String begintime) {
	this.begintime = begintime;
}
String begintime;
}
