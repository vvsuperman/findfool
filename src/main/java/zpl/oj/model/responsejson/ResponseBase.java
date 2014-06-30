package zpl.oj.model.responsejson;

public class ResponseBase {
	private int state;
	private String token;
	private Object message;
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	
	
}
