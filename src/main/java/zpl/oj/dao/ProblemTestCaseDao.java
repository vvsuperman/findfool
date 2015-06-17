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
  
  @Select("select TEST_CASE_ID as testCaseId, PROBLEM_ID as problemId,  SCORE,  EXCEPTED_RES as exceptedRes,  ARGS,  DETAIL FROM "
			+ "PROBLEM_TEST_CASE WHERE PROBLEM_ID = #{0} order by TEST_CASE_ID")
  List<ProblemTestCase> getProblemTestCases(int problemId);  
  
  @Select("select TEST_CASE_ID as testCaseId, PROBLEM_ID as problemId,  SCORE,  EXCEPTED_RES as exceptedRes,  ARGS,  DETAIL FROM "
			+ "PROBLEM_TEST_CASE WHERE PROBLEM_ID = #{0} ")
List<ProblemTestCase> getProTestCasesRandom(int problemId);  
  
  @Select("select TEST_CASE_ID as testCaseId, PROBLEM_ID as problemId,  SCORE,  EXCEPTED_RES as exceptedRes,  ARGS,  DETAIL FROM "
			+ "PROBLEM_TEST_CASE WHERE TEST_CASE_ID = #{0}")
  ProblemTestCase getProblemTestCaseById(Integer testCaseId);
  
  @Select("select TEST_CASE_ID as testCaseId, PROBLEM_ID as problemId,  SCORE,  EXCEPTED_RES as exceptedRes,  ARGS,  DETAIL FROM "
			+ "PROBLEM_TEST_CASE WHERE args = #{0}")
ProblemTestCase getProblemTestCaseByContent(String args);

    
}
