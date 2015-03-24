package zpl.oj.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Img implements Serializable {
	private String imgData;
	private String email;
	private String imgName;
	
	
	public String getImgData() {
		return imgData;
	}
	public void setImgData(String imgData) {
		this.imgData = imgData;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	
}

