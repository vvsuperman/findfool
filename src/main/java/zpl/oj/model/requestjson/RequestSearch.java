package zpl.oj.model.requestjson;

public class RequestSearch {

	private Integer setid;
	private Integer type;
	private String keyword;
	private Integer page;
	private Integer pageNum;	//一页多少条
	private Integer belong;
	private RequestUser user;
	public Integer getSetid() {
		return setid;
	}
	public void setSetid(Integer setid) {
		this.setid = setid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getBelong() {
		return belong;
	}
	public void setBelong(Integer belong) {
		this.belong = belong;
	}
	public RequestUser getUser() {
		return user;
	}
	public void setUser(RequestUser user) {
		this.user = user;
	}

}
