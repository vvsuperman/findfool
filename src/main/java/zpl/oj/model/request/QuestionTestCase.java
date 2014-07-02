package zpl.oj.model.request;

public class QuestionTestCase {

	private String text;
	private String isright;
	private int score;
	
	public QuestionTestCase() {
		text = null;
		isright = null;
		score = 0;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIsright() {
		return isright;
	}
	public void setIsright(String isright) {
		this.isright = isright;
	}
	
	
}
