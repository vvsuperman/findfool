package zpl.oj.model.common;

public class QuizTemplete {
	
	public int getQtId() {
		return qtId;
	}
	public void setQtId(int qtId) {
		this.qtId = qtId;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public String getQuizTName() {
		return quizTName;
	}
	public void setQuizTName(String quizTName) {
		this.quizTName = quizTName;
	}
	public String getQuizTDesc() {
		return quizTDesc;
	}
	public void setQuizTDesc(String quizTDesc) {
		this.quizTDesc = quizTDesc;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	
	private int qtId;
	private int quizId;
	private String quizTName;
	private String quizTDesc;
	private int time;


}
