package zpl.oj.model.responsejson;

import java.util.List;

import zpl.oj.model.common.Quiz;

public class ResponseQuizs {

	private int inviteLeft;
	private int invitedNum;
	private int uid;
	private List<Quiz> tests;
	public int getInviteLeft() {
		return inviteLeft;
	}
	public void setInviteLeft(int inviteLeft) {
		this.inviteLeft = inviteLeft;
	}
	public int getInvitedNum() {
		return invitedNum;
	}
	public void setInvitedNum(int invitedNum) {
		this.invitedNum = invitedNum;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public List<Quiz> getTests() {
		return tests;
	}
	public void setTests(List<Quiz> tests) {
		this.tests = tests;
	}
	
}
