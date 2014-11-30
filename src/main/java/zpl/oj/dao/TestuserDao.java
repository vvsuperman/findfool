package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Testuser;



public interface TestuserDao {
	 
  @Insert("INSERT INTO testuser( username,email,school,company,blog,age,pwd,tel,registerdate,logindate,state)"
			+ " VALUES( #{username}, #{email}, #{school}, #{company},#{blog},#{age},#{pwd},#{tel},#{registerdate},#{logindate},#{state})")
  void insertTestuser(Testuser testuser);
  
  @Update("update testuser set username=#{username}, school=#{school},company=#{company},blog=#{blog},age=#{age},pwd=#{pwd},tel=#{tel} where ")
  void updateTestuser(Testuser testuser);
  
  @Select("select * from testuser where usermane = #{0}")
  Testuser findTestuserByName(String username);
    
}