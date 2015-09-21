package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;

public class Quiz implements Serializable {

	public Quiz() {
		this.quizid = 0;
		this.owner = 0;
		this.name = "";
		this.date = new Date();
		this.time = 70;
		this.extraInfo = "";
		this.uuid = 0;
		this.emails = "";
		this.type = 0;
		this.logo = "";
		this.description = "";
		this.startTime = "";
		this.endTime = "";
		this.signedKey = "";
		this.createTime = "";
		this.status = 0;
		this.questionNum = 0;
		this.invitedNum = 0;
		this.finishedNum = 0;
		this.pubStartTime = "";
		this.pubEndTime = "";
		this.openCamera = 1;
		this.parent=0;
	}

	/**
	 * ${item.comment}
	 */
	private Integer quizid;

	/**
	 * ${item.comment}
	 */
	private Integer owner;

	/**
	 * 测试的名字,
	 */
	private String name;

	/**
	 * 创建的时间,
	 */
	private Date date;

	/**
	 * 该测试所需的时间，默认70分钟,
	 */
	private Integer time;

	/**
	 * 1,2,3,4
	 */
	private String extraInfo;

	/**
	 * 全局唯一识别符，只要uuid相同的，那么都是同一个测试，以时间最新的为标准,
	 */
	private Integer uuid;

	/**
	 * 这个里面是测试报告发送地址的邮件，使用逗号隔开,
	 */
	private String emails;

	/**
	 * 测试类型：0:私有;1:挑战赛
	 */
	private int type;

	/**
	 * 挑战赛Logo
	 */
	private String logo;

	/**
	 * 挑战赛描述
	 */
	private String description;

	/**
	 * 开始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 挑战赛签名
	 */
	private String signedKey;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 状态：0:未审核；1:未开始；2:进行中；3:已结束
	 */
	private int status;
	private int  parent;
	
	

	// 统计数据，方便向前端传递，非数据库字段
	// 统计数据：题量
	private int questionNum;
	// 统计数据：邀请数
	private int invitedNum;
	// 统计数据：完成数
	private int finishedNum;

	// 作为挑战赛时候的开始时间
	private String pubStartTime;
	// 作为挑战赛时候的结束时间
	private String pubEndTime;
	// 开启摄像头标记
	private int openCamera;
	
	public int getQuestionNum() {
		return questionNum;
	}

	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}

	public int getInvitedNum() {
		return invitedNum;
	}

	public void setInvitedNum(int invitedNum) {
		this.invitedNum = invitedNum;
	}

	public int getFinishedNum() {
		return finishedNum;
	}

	public void setFinishedNum(int finishedNum) {
		this.finishedNum = finishedNum;
	}

	public Integer getQuizid() {
		return quizid;
	}

	public void setQuizid(Integer quizid) {
		this.quizid = quizid;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSignedKey() {
		return signedKey;
	}

	public void setSignedKey(String signedKey) {
		this.signedKey = signedKey;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPubStartTime() {
		return pubStartTime;
	}

	public void setPubStartTime(String pubStartTime) {
		this.pubStartTime = pubStartTime;
	}

	public String getPubEndTime() {
		return pubEndTime;
	}

	public void setPubEndTime(String pubEndTime) {
		this.pubEndTime = pubEndTime;
	}

	public int getOpenCamera() {
		return openCamera;
	}

	public void setOpenCamera(int openCamera) {
		this.openCamera = openCamera;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

}