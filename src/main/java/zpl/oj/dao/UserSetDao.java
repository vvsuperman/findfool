package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ProblemSet;


public interface UserSetDao {
	
	
	@Insert("INSERT INTO user_set(setid,uid) VALUES(#{0},#{1})")
	void insertUserSet(int setid,int uid);

	

}