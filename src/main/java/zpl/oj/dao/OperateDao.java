package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.foolrank.provider.QuizProvider;

public interface OperateDao {
	
	@Select("SELECT t2.api from role_api t1, api t2  WHERE t1.apiid = t2.apiid and t2.api=#{0} and t1.roleid=#{1}")
	String getUrlbyApi(String string,int role);



	@Insert("INSERT INTO api(api) VALUES(#{0})")
	void insertApi(String string);

	@Select("SELECT count(*) FROM api WHERE  api=#{0}")
	int countApi(String api);
	
	
	@Select("SELECT apiid FROM api WHERE  api=#{0}")
	int getApiidByApi(String string);


	@Insert("INSERT INTO role_api(roleid,apiid) VALUES(#{0},#{1})")
	void insertRoleapi(int roleid, int apiid);


}
