package zpl.oj.model.common;

import java.io.Serializable;

import java.util.Date;

public class Tag implements Serializable {
	
	private int tagId;
	private String tagName;
	
	public Tag(){
		this.tagId =-1;
		this.tagName="";
	}
	
	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}



	
	
	
    
}