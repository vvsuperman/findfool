package zpl.oj.model.common;

import java.io.Serializable;
import java.util.Date;

public class RandomQuizSet implements Serializable {

	private Integer problemSetId;


	private Integer testid;


	private Integer randomid;

	private Integer num;

	private Integer level;

	public Integer getProblemSetId() {
		return problemSetId;
	}

	public void setProblemSetId(Integer problemSetId) {
		this.problemSetId = problemSetId;
	}

	public Integer getTestid() {
		return testid;
	}

	public void setTestid(Integer testid) {
		this.testid = testid;
	}

	public Integer getRandomid() {
		return randomid;
	}

	public void setRandomid(Integer randomid) {
		this.randomid = randomid;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	



}