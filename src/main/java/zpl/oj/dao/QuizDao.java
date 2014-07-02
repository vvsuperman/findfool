package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Quiz;


public interface QuizDao {

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE QUIZID = #{0}")
  Quiz getQuiz(int tid);
	
	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO,  UUID, EMAILS "
			+ "FROM QUIZ WHERE UUID = #{0} ORDER BY QUIZID DESC limit 1")
  Quiz getNewestQuizByUuid(int uuid);
	
	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE OWNER = #{0} ORDER BY QUIZID DESC limit 1")
  Quiz getNewestQuizByOwner(int owner);
	
  @Insert("INSERT INTO QUIZ( OWNER,  NAME,  DATE,  TIME,  EXTRA_INFO,  UUID,  EMAILS)"
  		+ " VALUES("
  		+ "#{owner},#{name},#{date},#{time},#{extraInfo},#{uuid},#{emails})")
  void insertQuiz(Quiz quiz);
  
  void deleteQuiz(Quiz quiz);
  
  @Update("UPDATE QUIZ set OWNER = #{owner},   NAME = #{name},  "
  		+ "DATE = #{date},  TIME = #{time},  EXTRA_INFO = #{extraInfo}, "
  		+ "UUID = #{uuid},  EMAILS = #{emails}"
  		+ " where QUIZID = #{quizid}")
  void updateQuiz(Quiz quiz);
    
  @Select("SELECT QUIZID,  OWNER, NAME,  DATE,  TIME,  EXTRA_INFO as extraInfo,  UUID,  EMAILS  "
  		+ " FROM (select * from Quiz order by quizid DESC) as b WHERE OWNER=#{0} group by uuid")
  List<Quiz> getQuizs(int owner);  
  
  
  
//  /**主要用于分页，可返回一页的记录*/
//  List<Quiz> select(Quiz quiz);  
//  
//  /**主要用于分页，可返回总记录数*/
//  int selectCount(Quiz quiz);  
    
}