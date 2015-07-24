package zpl.oj.model.common;

import java.io.Serializable;

public class Labeltest implements Serializable{
	private Integer id;
	private Integer testid;
	private Integer labelid;
	private Integer isSelected;
	private int type;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTestid() {
		return testid;
	}
	public void setTestid(Integer testid) {
		this.testid = testid;
	}
	public Integer getLabelid() {
		return labelid;
	}
	public void setLabelid(Integer labelid) {
		this.labelid = labelid;
	}
	public Integer getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}

	
}
