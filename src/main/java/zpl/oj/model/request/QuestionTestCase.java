package zpl.oj.model.request;

public class QuestionTestCase {

	private String text;
	private String isright;
	private Integer score;
	
	public QuestionTestCase() {
		text = null;
		isright = null;
		score = 0;
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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
}
