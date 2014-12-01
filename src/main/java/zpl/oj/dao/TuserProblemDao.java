package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.TuserProblem;



public interface TuserProblemDao {
  
  @Insert("INSERT INTO testuser_problem(tuid,problemid,useranser,rightanswer)"
		+ " VALUES( #{tuid}, #{problmeid}, #{useranswer}, #{rightanswer})")
  void insertTuserProblem(TuserProblem testuserProblem);
  

  @Update("update testuser_problem set problemid=#{problemid},useranser=#{useranswer},rightanswer=#{rightanswer} where tuid =#{tuid}")
  void updateTuserProblem(TuserProblem testuserProblem);
  
  @Select("select t1.problemid,t1.useranswer from testuser_problem t1, invite t2 where t1.tuid = t2.uid and t2.testid = #{0}")
  List<TuserProblem> findProblemByTestid(int testid);
  
  
  

}




  

