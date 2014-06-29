package zpl.oj.dao.user;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.User;

public interface UserDao {

	@Insert("insert into user (fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date,last_login_date,yu,invited_num,state)"
			+ " values(#{fname},#{lname},#{email},#{company},${privilege},#{pwd},#{link},#{age},#{degree},#{school},Now(),Now(),${yu},${invitedNum},${state})")
	void addUser(User u);
	
	@Update("update user set "
			+ "fname='#{fname}',lname='#{lname}',company='#{company}',privilege=${privilege},"
			+ "pwd='#{pwd}',yu=${yu},invited_num=${invitedNum} "
			+ "where uid=${uid}")
	void updateUser(User u);
	
	@Update("update user set state=0 where uid = ${uid}")
	void deleteUserbyId(int uid);
	@Update("update user set state=0 where uid = #{email}")
	void deleteUserbyEmail(String email);
	
	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,yu,invited_num,state from "
			+ "user where email = #{email}")
	User getUserIdByEmail(String email);
	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,yu,invited_num,state from "
			+ "user where uid = #{uid}")
	User getUserIdByUid(int  uid);
	
	@Update("update user set last_login_date=now() where email = #{email}")
	void updateLoginDateByEmail(String email);
	@Update("update user set last_login_date=now() where uid = ${uid}")
	void updateLoginDateByUid(int uid);
	
	
}
