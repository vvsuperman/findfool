package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.CadProblem;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;



public interface CadProblemDao {
  
  @Insert("INSERT INTO cad_problem(cadid,problemid,useranswer,ctid)"
		+ " VALUES( #{cadid}, #{problemid}, #{useranswer},#{ctid})")
  void insertCadProblem(CadProblem cadProblem);
  
  //找到还未开始做的题目
  @Select("select * from cad_problem  where ctid=#{0} and useranswer =''")
  List<CadProblem> findFreshPros(int ctid);
  
  @Select("select * from cad_problem t1 where t1.ctid = #{0} and t1.problemid=#{1}")
  CadProblem findByPidAndIid(int ctid,int problemid);
  

  @Update("update cad_problem  set useranswer=#{useranswer} where problemid =#{problemid} and ctid = #{ctid}")
  void updateAnswerByIds(CadProblem cadProblem);
  
  
  @Update("update cad_problem  set solution_id=#{2} where ctid =#{0} and problemid=#{1}")
  void updateSolutionByIids(int ctid,int probemId,int solutionId);
  
  @Update("update cad_problem  set rightanswer= #{rightanswer},type=#{type},useranswer = #{useranswer}  where problemid =#{problemid} and ctid = #{ctid}")
  void updateProblemByIds(CadProblem cadProblem);
  
  @Select("select t1.problemid,t1.useranswer,t1.ctid as ctid,t2.problem_set_id as setid,t2.type,t1.solution_id as solutionId,t2.level "
  		+ "from cad_problem t1, problem t2 where t1.ctid=#{0} and t1.problemid = t2.problem_id order by t2.type,t2.problem_set_id")
  List<CadProblem> findProblemByCtid(int ctid);
  
  @Select("select count(*) from cad_problem where ctid=#{0}")
  int countProblems(int ctid);
 
  
  
  
  @Select("select sum(t2.score) from cad_problem t1,problem t2 where t1.problemid = t2.problem_id and t1.ctid=#{0} and t2.type=1")
  public int getTotalScore(int ctid);
  
  
 @Select(" select sum( case  when p.rightanswer =  t.useranswer then p.score else 0 end) as score from problem p,cad_problem t" 
  +" where  t.ctid = #{iid} AND t.problemid = p.problem_id AND p.type=1")
 public int getUserScore(Invite invite);
 
 @Select("select p.problem_id as qid, p.problem_set_id as setid,p.type as type,p.description as context,p.title as name,"
 		+"p.rightanswer as  rightanswer,t.useranswer as useranswer "
		+"from ojsite.problem p,ojsite.cad_problem t "
		+"where t.ctid =#{iid} AND t.problemid = p.problem_id "
		+"order by p.type")
 public List<Question> getUserQuestion(Invite invite);
 
 

 @Update("update cad_problem  set useranswer='0000' where ctid = #{ctid}")
 void clearProblem(Integer ctid);
 
 @Select("select sum(r.score) from problem_test_case p,resultinfo r " 
 +"where r.solution_id = #{0} and  p.test_case_id = r.test_case_id;")
 Integer sumProblemScore(int solutionId);
 
 @Select("select r.test_case_id,r.test_case as testCase,p.excepted_res as testCaseExpected, r.score,r.test_case_result "+ 
		 " from problem_test_case p,resultinfo r "+ 
		 " where  r.solution_id =#{0} and p.test_case_id = r.test_case_id;")
 List<ResultInfo> getProResult(int solutionId);

@Delete("delete from cad_problem where ctid =#{0} AND problemid = #{1}")
void deleteByIds(int ctid, Integer problemid);

@Select("select sum(score) from problem_test_case where problem_id=#{0}")
Integer sumProTotalScore(Integer problemid);
 
}




  

