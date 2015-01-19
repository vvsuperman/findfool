package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;



public interface TuserProblemDao {
  
  @Insert("INSERT INTO testuser_problem(tuid,problemid,useranswer,rightanswer,type,invite_id)"
		+ " VALUES( #{tuid}, #{problemid}, #{useranswer}, #{rightanswer},#{type},#{inviteId})")
  void insertTuserProblem(TuserProblem testuserProblem);
  

  @Update("update testuser_problem  set useranswer=#{useranswer} where problemid =#{problemid} and invite_id = #{inviteId}")
  void updateAnswerByIds(TuserProblem testuserProblem);
  
  
  @Update("update testuser_problem  set solution_id=#{2} where invite_id =#{0} and problemid=#{1}")
  void updateSolutionByIids(int inviteId,int probemId,int solutionId);
  
  @Update("update testuser_problem  set rightanswer= #{rightanswer},type=#{type},useranswer = #{useranswer}  where problemid =#{problemid} and invite_id = #{inviteId}")
  void updateProblemByIds(TuserProblem testuserProblem);
  
  @Select("select t1.problemid,t1.useranswer,t1.invite_id,t2.rightanswer,t2.problem_set_id as setid,t2.type,t1.solution_id as solutionId,t2.level "
  		+ "from testuser_problem t1, problem t2 where t1.invite_id=#{0} and t1.problemid = t2.problem_id order by t2.type,t2.problem_set_id")
  List<TuserProblem> findProblemByInviteId(int inviteId);
  
  @Select("select t1.problemid,t1.useranswer,t1.type,t1.invite_id,t1.solution_id as solutionId from testuser_problem t1 where t1.invite_id = #{0} and t1.problemid=#{1}")
  TuserProblem findByPidAndIid(int inviteId,int problemid);
  
  @Select("select sum(t2.score) from testuser_problem t1,problem t2 where t1.problemid = t2.problem_id and t1.invite_id=#{0} and t2.type=1")
  public int getTotalScore(int inviteId);
  
  
 @Select(" select sum( case  when p.rightanswer =  t.useranswer then p.score else 0 end) as score from problem p,testuser_problem t" 
  +" where  t.invite_id = #{iid} AND t.problemid = p.problem_id AND p.type=1")
 public int getUserScore(Invite invite);
 
 @Select("select p.problem_id as qid, p.problem_set_id as setid,p.type as type,p.description as context,p.title as name,"
 		+"p.rightanswer as  rightanswer,t.useranswer as useranswer "
		+"from ojsite.problem p,ojsite.testuser_problem t "
		+"where t.invite_id =#{iid} AND t.problemid = p.problem_id "
		+"order by p.type")
 public List<Question> getUserQuestion(Invite invite);
 
 @Select("select tuid,username,email,company,degree,school,tel,blog from "
			+ "testuser where tuid = #{0}")
 public Testuser getTestUserById(int tuid);

 @Update("update testuser_problem  set useranswer='0000' where invite_id = #{inviteId}")
 void clearProblem(Integer inviteId);
 
 @Select("select sum(r.score) from problem_test_case p,resultinfo r " 
 +"where r.solution_id = #{0} and  p.test_case_id = r.test_case_id;")
 Integer sumProblemScore(int solutionId);
 
 @Select("select r.test_case_id,r.test_case as testCase,p.excepted_res as testCaseExpected, r.score "+ 
		 " from problem_test_case p,resultinfo r "+ 
		 " where  r.solution_id =#{0} and p.test_case_id = r.test_case_id;")
 List<ResultInfo> getProResult(int solutionId);

@Delete("delete from testuser_problem where  invite_id =#{0} AND problemid = #{1}")
void deleteByIds(int inviteId, Integer problemid);

@Select("select sum(score) from problem_test_case where problem_id=#{0}")
Integer sumProTotalScore(Integer problemid);
 
}




  

