package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.solution.ResultInfo;

public interface ResultInfoDao {

	@Select("select * from resultinfo where solution_id=#{solutionId}")
	public List<ResultInfo> getResultsBySolutionId(int solutionId);
	
	@Insert("insert into resultinfo(test_case_id,solution_id,cost_time,cost_mem,test_case_result,test_case,score)"
			+ " values(#{test_case_id},#{solution_id},#{cost_time},#{cost_mem},#{test_case_result},#{test_case},#{score})")
	public void insertResultInfo(ResultInfo res);
}
