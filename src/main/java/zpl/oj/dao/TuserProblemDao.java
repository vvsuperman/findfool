package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.TuserProblem;



public interface TuserProblemDao {
  
  @Insert("INSERT INTO testuser_problem(tuid,problemid,useranswer,rightanswer,type,set_id,invite_id)"
		+ " VALUES( #{tuid}, #{problemid}, #{useranswer}, #{rightanswer},#{type},#{setid},#{inviteId})")
  void insertTuserProblem(TuserProblem testuserProblem);
  

  @Update("update testuser_problem  set useranswer=#{useranswer} where problemid =#{problemid} and tuid = #{tuid}")
  void updateAnswerByIds(TuserProblem testuserProblem);
  
  @Update("update testuser_problem  set rightanswer= #{rightanswer},type=#{type},set_id=#{setid} where problemid =#{problemid} and tuid = #{tuid}")
  void updateProblemByIds(TuserProblem testuserProblem);
  
  @Select("select t1.problemid,t1.useranswer,t1.type,t1.set_id,t1.invitie_id from testuser_problem t1 where t1.invite=#{0} order by t1.type")
  List<TuserProblem> findProblemByInviteId(int inviteId);
  
  @Select("select t1.problemid,t1.useranswer,t1.type,t1.set_id,t1.invite_id from testuser_problem t1 where t1.tuid = #{0} and t1.problemid=#{1}")
  TuserProblem findByPidAndUid(int tuid,int problemid);
  
  @Select("select count(t2.score) from testuser_problem t1,problem t2 where t1.problemid = t2.problmeid and t1.invite_id=#{0}")
  public int getTotalScore(int inviteId);
  
}




  

