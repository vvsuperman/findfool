package zpl.oj.model.request;

public class QuestionTestCase {

	private Integer caseId;
	private String text;
	private String isright;
	private Integer score;
	
	public QuestionTestCase() {
		caseId = null;
		text = null;
		isright = null;
		score = 0;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
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
