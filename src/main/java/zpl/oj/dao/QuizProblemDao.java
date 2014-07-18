package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.QuizProblem;


public interface QuizProblemDao {

	@Select("select TPID,  QUIZID,  PROBLEMID,  DATE,  LANG "
			+ " FROM QUIZPROBLEM WHERE PROBLEMID = #{0}")
  List<QuizProblem> getQuizProblemsByProblemId(int id);
  
	@Select("SELECT TPID,   QUIZID,   PROBLEMID,   DATE,   LANG    "
			+ "  FROM QUIZPROBLEM WHERE QUIZID=#{0}")
  List<QuizProblem> getQuizProblemsByQuizId(int quizId);  
	
	@Insert("INSERT INTO QUIZPROBLEM( QUIZID,  PROBLEMID,  DATE,  LANG)"
			+ " VALUES( #{quizid}, #{problemid}, #{date}, #{lang})")
  void insertQuizproblem(QuizProblem quizproblem);
  
 // void deleteQuizproblem();
  
  //void updateQuizproblem(QuizProblem quizproblem);
    

  
//  /**主要用于分页，可返回一页的记录*/
//  List<QuizProblem> select(QuizProblem quizproblem);  
//  
//  /**主要用于分页，可返回总记录数*/
//  int selectCount(QuizProblem quizproblem);  
    
}