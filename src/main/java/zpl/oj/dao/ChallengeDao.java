package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.Quiz;

public interface ChallengeDao {

	@Select("SELECT * FROM challenge WHERE status=#{0} ORDER BY start_time ASC LIMIT #{1},#{2}")
	List<Quiz> getListByStatus(int status, int offset, int count);

	@Select("SELECT * FROM challenge WHERE company_id=#{0} AND status=#{1} ORDER BY start_time ASC LIMIT #{2},#{3}")
	List<Quiz> getListByCompany(int companyId, int status, int offset, int count);
}
