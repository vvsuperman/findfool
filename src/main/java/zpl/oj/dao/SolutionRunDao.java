package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.solution.SolutionRun;

public interface SolutionRunDao {
	//增加任务
	@Insert(" 		INSERT	INTO solution_run(user_id,test_id,problem_id,solution,language,date,type)	VALUES(${user_id},${testid},${problem_id},#{solution},${language},NOW(),${type})")
	void addSolutionRun(SolutionRun u);
	
	@Update(" UPDATE solution_run set type=0 where solution_id=#{solution_id}")
	void updateSolutionState(int solution_id);
	//get newest solutionrun id
	@Select("select solution_id from solution_run where user_id=${user_id} order by date DESC limit 1")
	Integer getSolutionRunId(SolutionRun u);
}
