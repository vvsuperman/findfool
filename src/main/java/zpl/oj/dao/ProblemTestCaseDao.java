package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.ProblemTestCase;


public interface ProblemTestCaseDao {
	@Insert("INSERT INTO PROBLEM_TEST_CASE(PROBLEM_ID, SCORE, EXCEPTED_RES, ARGS, DETAIL)"
		+ " VALUES( #{problemId}, #{score}, #{exceptedRes}, #{args}, #{detail})")
  void insertProblemTestCase(ProblemTestCase problemTestCase);
	
  @Update("    UPDATE PROBLEM_TEST_CASE set"
  		+ " PROBLEM_ID = #{problemId}, "
  		+ "SCORE = #{score}, EXCEPTED_RES = #{exceptedRes}, ARGS = #{args}, DETAIL = #{detail}"
  		+ " where test_case_id = #{testCaseId}")
  void updateProblemTestCase(ProblemTestCase problemTestCase);
  
  @Select("select TEST_CASE_ID, PROBLEM_ID,  SCORE,  EXCEPTED_RES,  ARGS,  DETAIL FROM "
			+ "PROBLEM_TEST_CASE WHERE PROBLEM_ID = ${problemId}")
  List<ProblemTestCase> getProblemTestCases(int problemId);  
  

    
}
