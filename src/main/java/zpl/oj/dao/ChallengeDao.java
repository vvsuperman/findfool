package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.foolrank.model.Challenge;

public interface ChallengeDao {

	@Select("SELECT * FROM challenge WHERE status=#{0} ORDER BY start_time ASC LIMIT #{1},#{2}")
	List<Challenge> getList(int status, int offset, int count);

	@Select("SELECT * FROM challenge WHERE company_id=#{0} ORDER BY start_time ASC LIMIT #{1},#{2}")
	List<Challenge> getList(int corporateId, int status, int offset, int count);
}
