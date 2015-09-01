package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.foolrank.provider.QuizProvider;

public interface OperateDao {
	
	@Select("SELECT api FROM api WHERE  api=#{0}")
	String getUrlbyApi(String string);



	@Insert("INSERT INTO api(api) VALUES(#{0})")
	void insertApi(String string);


	@Select("SELECT apiid FROM api WHERE  api=#{0}")
	int getApiidByApi(String string);


	@Insert("INSERT INTO role_api(roleid,apiid) VALUES(#{0},#{1})")
	void insertRoleapi(int roleid, int apiid);


}
