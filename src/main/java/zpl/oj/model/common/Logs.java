package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;

public class Logs implements Serializable  {
   
   private int logId;
   private String action;
   private Date datetime;
   private int uid;
   private int hrId;
   
 
public Logs(){
	   this.logId =0;
	   this.action = "";
	   this.datetime = new Date();
	   this.uid = 0;
	   this.hrId = 0;
   }


	public int getHrId() {
		return hrId;
	}
	
	public void setHrId(int hrId) {
		this.hrId = hrId;
	}

   
   public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getDatedtime() {
		return datetime;
	}
	public void setDatedtime(Date datedtime) {
		this.datetime = datedtime;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
   
   

}
