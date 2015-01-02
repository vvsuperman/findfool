package zpl.oj.dao;


import org.apache.ibatis.annotations.Select;
import zpl.oj.model.common.VerifyQuestion;


public interface VerifyQuestionDao {
	@Select("select * from verify_question limit #{index},1")
	VerifyQuestion getVerifyQuestion(int index);
	
	@Select("select count(*) from verify_question")
	int getVerifyQuestionCount();
}
