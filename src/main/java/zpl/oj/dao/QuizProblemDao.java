package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.QuizProblem;


public interface QuizProblemDao {

	@Select("select TPID,  QUIZID,  PROBLEMID,  DATE,  LANG "
			+ " FROM QUIZPROBLEM WHERE PROBLEMID = #{0}")
  List<QuizProblem> getQuizProblemsByProblemId(int id);
  
	@Select("SELECT TPID,   QUIZID,   PROBLEMID,   DATE,   LANG    "
			+ "  FROM QUIZPROBLEM WHERE QUIZID=#{0}")
  List<QuizProblem> getQuizProblemsByQuizId(int quizId);  
	
	
  @Select("SELECT TPID,   QUIZID,   PROBLEMID,   DATE,   LANG    "
			+ "  FROM QUIZPROBLEM WHERE QUIZID=#{quizid} AND PROBLEMID=#{problemid}")
  List<QuizProblem> getQuizProblemsByQuizProblem(QuizProblem quizproblem);  
	
  @Insert("INSERT INTO QUIZPROBLEM( QUIZID,  PROBLEMID,  DATE,  LANG)"
			+ " VALUES( #{quizid}, #{problemid}, #{date}, #{lang})")
  void insertQuizproblem(QuizProblem quizproblem);
	
	
  //若测试已发出，则不更新试题	
  @Update("UPDATE QUIZPROBLEM SET PROBLEMID = #{problemId} WHERE"
  		+ "QUIZID NOT IN (SELECT TESTID FROM INVITE ) AND QUIZID=#{quizid} AND PROBLEMID=#{oldProblemId}")
  void updateByQuizproblem(int quizId, int oldProblemId,int problemId);
	
	
  
 // void deleteQuizproblem();
  
  //void updateQuizproblem(QuizProblem quizproblem);
    

  
//  /**主要用于分页，可返回一页的记录*/
//  List<QuizProblem> select(QuizProblem quizproblem);  
//  
//  /**主要用于分页，可返回总记录数*/
//  int selectCount(QuizProblem quizproblem);  
    
}