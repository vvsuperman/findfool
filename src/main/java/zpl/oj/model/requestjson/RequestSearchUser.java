package zpl.oj.model.requestjson;

public class RequestSearchUser {

	private String keyword;
	private int page;
	private int pageNum;
	private RequestUser user;


	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
