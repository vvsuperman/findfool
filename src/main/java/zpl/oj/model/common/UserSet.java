package zpl.oj.model.common;


//user和set的关联表，表示set可以有哪些user
public class UserSet {
	private int suid;
	private int setid;
	private int uid;
	
	public int getSuid() {
		return suid;
	}
	public void setSuid(int suid) {
		this.suid = suid;
	}
	public int getSetid() {
		return setid;
	}
	public void setSetid(int setid) {
		this.setid = setid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
	

}
