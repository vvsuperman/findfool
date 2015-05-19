package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizTemplete;


public interface QuizDao {

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE QUIZID = #{0}")
  Quiz getQuiz(int tid);
	
  @Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE name = #{0}")
  Quiz getQuizByName(String name);
	
  @Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE NAME=#{0} and OWNER = #{1} ORDER BY QUIZID DESC limit 1")
  Quiz getQuizByOwnerAndName(String name, int uid);  
	  
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
  		+ " FROM quiz WHERE OWNER=#{0} order by date desc ")
  List<Quiz> getQuizs(int owner);

  @Select("select * from quiz_templete where quizTName = #{quizName}")
  QuizTemplete getQuizTByName(String quizName);
  
  


}
