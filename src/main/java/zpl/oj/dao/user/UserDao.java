package zpl.oj.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.request.User;

public interface UserDao {

	@Insert("insert into user (fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date,last_login_date,invited_left,invited_num,state,tel)"
			+ " values(#{fname},#{lname},#{email},#{company},${privilege},#{pwd},#{link},#{age},#{degree},#{school},Now(),Now(),${invited_left},${invitedNum},${state},#{tel})")
	void addUser(User u);
	
	@Update("update user set "
			+ "fname=#{fname},lname=#{lname},company=#{company},tel=#{tel},privilege=${privilege},"
			+ "pwd=#{pwd},invited_left=${invited_left},invited_num=${invitedNum} "
			+ "where uid=${uid}")
	void updateUser(User u);
	
	@Update("update user set state=0 where uid = ${uid}")
	void deleteUserbyId(int uid);
	@Update("update user set state=0 where uid = #{email}")
	void deleteUserbyEmail(String email);
	
	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num,state,tel from "
			+ "user where email = #{email}")
	User getUserIdByEmail(String email);
	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num,state,tel from "
			+ "user where uid = #{uid}")
	User getUserIdByUid(int  uid);
	
	@Update("update user set last_login_date=now() where email = #{email}")
	void updateLoginDateByEmail(String email);
	@Update("update user set last_login_date=now() where uid = #{uid}")
	void updateLoginDateByUid(int uid);
	
	@Update("update user set pwd=#{pwd} where email = #{email}")
	void updatePwd(String pwd,String email);
	
	
}
