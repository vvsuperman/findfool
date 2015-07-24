package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;

public interface TestuserDao {

	@Insert("INSERT INTO testuser( username,email,school,company,blog,age,tel,registerdate,lastlogindate,state,discipline,gratime,pwd)"
			+ " VALUES( #{username}, #{email}, #{school}, #{company},#{blog},#{age},#{tel},#{registerDate},#{lastLoginDate},#{state},#{discipline},#{gratime},#{pwd})")
	void insertTestuser(Testuser testuser);

	@Update("update testuser set username=#{username},email=#{email} ,school=#{school},company=#{company},blog=#{blog},age=#{age},tel=#{tel},discipline=#{discipline},gratime=#{gratime},"
			+ "registerdate = #{registerDate},lastlogindate=#{lastLoginDate},faceid=#{faceid}  where tuid =#{tuid}")
	void updateTestuserById(Testuser testuser);

	@Select("select * from testuser where email = #{0}")
	Testuser findTuserByEmail(String email);

	@Select("select * from testuser where tel = #{0}")
	Testuser findTuserByTel(String tel);

	@Select("select * from testuser where tuid = #{0}")
	Testuser findTestuserById(int tuid);

	@Update("update testuser set lastlogindate=now() where email = #{email}")
	void updateLoginDateByEmail(String email);

	@Update("update testuser set lastlogindate=now() where tuid = #{tuid}")
	void updateLoginDateByUid(int tuid);

	@Select("select pwd from testuser where email = #{0}")
	String findTuserByPwd(String email);

	

	
	@Update("update testuser set username=#{username},pwd=#{pwd},tel=#{tel} where email =#{email}")
	void updateTestuser(Testuser test1);

}