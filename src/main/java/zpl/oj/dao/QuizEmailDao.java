package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.QuizEmail;

public interface QuizEmailDao {
	//通过测试的id获取该测试的所有关联邮箱
	@Select("select * from quiz_email qe where qe.quizid=#{0}")
	List<QuizEmail> getAllEmailsByQuizId(Integer quizId);
	//通过quizId、email（如"solevial@sohu.com"）获取email实体对象
	@Select("select * from quiz_email qe where qe.quizId=#{0} and qe.email=#{1}")
	QuizEmail getEmailByEmail(Integer quizId,String email);
	//向quiz_email中插入一条邮箱数据
	@Insert("insert into quiz_email(quizId,email) values(#{0},#{1})")
	void insertIntoPublicLinkEmail(Integer quizId,String email);
	
}
