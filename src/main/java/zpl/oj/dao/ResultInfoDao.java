package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zpl.oj.model.solution.ResultInfo;

public interface ResultInfoDao {

	@Select("select * from resultinfo where solution_id=#{solutionId}")
	public List<ResultInfo> getResultsBySolutionId(int solutionId);
}
