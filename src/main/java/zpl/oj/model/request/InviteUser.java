package zpl.oj.model.request;

public class InviteUser {

	
	private String username;
	
	private String email;
	private String tel;

    private String test;
    private int openCamera;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getOpenCamera() {
		return openCamera;
	}

	public void setOpenCamera(int openCamera) {
		this.openCamera = openCamera;
	}
	
}
