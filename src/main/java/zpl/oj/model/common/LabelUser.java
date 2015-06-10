package zpl.oj.model.common;

import java.io.Serializable;

public class LabelUser implements Serializable {
	private Integer id;
	private Integer testuserid;
	private Integer labelid;
	private String value;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTestuserid() {
		return testuserid;
	}
	public void setTestuserid(Integer testuserid) {
		this.testuserid = testuserid;
	}
	public Integer getLabelid() {
		return labelid;
	}
	public void setLabelid(Integer labelid) {
		this.labelid = labelid;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
