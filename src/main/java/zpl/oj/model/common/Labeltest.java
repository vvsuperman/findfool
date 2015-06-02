package zpl.oj.model.common;

import java.io.Serializable;

public class Labeltest implements Serializable{
	private int id;
	private int testid;
	private int labelid;
	private int value;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTestid() {
		return testid;
	}
	public void setTestid(int testid) {
		this.testid = testid;
	}
	public int getLabelid() {
		return labelid;
	}
	public void setLabelid(int labelid) {
		this.labelid = labelid;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
}
