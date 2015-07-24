package zpl.oj.util.json;

public class JsonLabel{
	
	private Integer labelid;
	private String labelname;
	private Boolean isSelected;
	
	private int labeltype;

	public int getLabeltype() {
		return labeltype;
	}
	public void setLabeltype(int labeltype) {
		this.labeltype = labeltype;
	}
	public Integer getLabelid() {
		return labelid;
	}
	public void setLabelid(Integer labelid) {
		this.labelid = labelid;
	}
	public String getLabelname() {
		return labelname;
	}
	public void setLabelname(String labelname) {
		this.labelname = labelname;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
}
