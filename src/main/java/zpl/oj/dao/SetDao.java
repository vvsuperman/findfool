package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.Set;

public interface SetDao {

	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER "
			+ "  FROM SETS  WHERE problem_set_id = #{0}")
	Set getSet(int id);

	@Insert("INSERT INTO SETS(NAME,  DATE, OWNER)"
			+ "VALUES(#{name}, #{date},#{owner})")
	void insertSet(Set set);
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER "
			+ "  FROM SETS")
	List<Set> getSets();

}