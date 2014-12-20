package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;



public interface TuserProblemDao {
  
  @Insert("INSERT INTO testuser_problem(tuid,problemid,useranswer,rightanswer,type,invite_id)"
		+ " VALUES( #{tuid}, #{problemid}, #{useranswer}, #{rightanswer},#{type},#{setid},#{inviteId})")
  void insertTuserProblem(TuserProblem testuserProblem);
  

  @Update("update testuser_problem  set useranswer=#{useranswer} where problemid =#{problemid} and tuid = #{tuid}")
  void updateAnswerByIds(TuserProblem testuserProblem);
  
  @Update("update testuser_problem  set rightanswer= #{rightanswer},type=#{type} where problemid =#{problemid} and tuid = #{tuid}")
  void updateProblemByIds(TuserProblem testuserProblem);
  
  @Select("select t1.problemid,t1.useranswer,t1.invite_id,t1.useranswer from testuser_problem t1 where t1.invite_id=#{0} order by t1.type")
  List<TuserProblem> findProblemByInviteId(int inviteId);
  
  @Select("select t1.problemid,t1.useranswer,t1.type,t1.invite_id from testuser_problem t1 where t1.tuid = #{0} and t1.problemid=#{1}")
  TuserProblem findByPidAndUid(int tuid,int problemid);
  
  @Select("select sum(t2.score) from testuser_problem t1,problem t2 where t1.problemid = t2.problem_id and t1.invite_id=#{0}")
  public int getTotalScore(int inviteId);
  
  
 @Select(" select sum( case  when p.rightanswer =  t.useranswer then p.score else 0 end) as score from ojsite.problem p,ojsite.testuser_problem t" 
  +" where  t.invite_id = #{iid} AND t.problemid = p.problem_id")
 public int getUserScore(Invite invite);
 
 @Select("select p.problem_id as qid, p.problem_set_id as setid,p.type as type,p.description as context,p.title as name,"
 		+"p.rightanswer as  rightanswer,t.useranswer as useranswer "
		+"from ojsite.problem p,ojsite.testuser_problem t "
		+"where t.invite_id =#{iid} AND t.problemid = p.problem_id "
		+"order by setId,qid;")
 public List<Question> getUserQuestion(Invite invite);
 
 @Select("select tuid,username,email,company,degree,school,tel,blog from "
			+ "testuser where tuid = #{0}")
 public Testuser getTestUserById(int tuid);
}




  

