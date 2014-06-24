package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;

import zpl.oj.model.solution.UserTestCase;

public interface UserTestCaseDao {

	@Insert("INSERT INTO user_test_case(solution_id,args) VALUES(${solution_id},#{args})")
	public void addUserTestCase(UserTestCase u);
}
