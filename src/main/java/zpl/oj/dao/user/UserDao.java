package zpl.oj.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.User;

public interface UserDao {

	@Insert("insert into user (fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date,last_login_date,invited_left,invited_num,state,tel)"
			+ " values(#{fname},#{lname},#{email},#{company},${privilege},#{pwd},#{link},#{age},#{degree},#{school},Now(),Now(),${invited_left},${invitedNum},${state},#{tel})")
	void addUser(User u);

	@Update("update user set "
			+ "fname=#{fname},lname=#{lname},company=#{company},tel=#{tel},privilege=${privilege},"
			+ "pwd=#{pwd},invited_left=${invited_left},resetUrl=#{resetUrl},invited_num=${invitedNum},companyId=${companyId} "
			+ "where uid=${uid}")
	void updateUser(User u);

	@Update("update user set state=0 where uid = ${uid}")
	void deleteUserbyId(int uid);

	@Update("update user set state=0 where uid = #{email}")
	void deleteUserbyEmail(String email);

	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num,state,tel from "
			+ "user where email = #{email}")
	User getUserIdByEmail(String email);

	@Select("select uid,fname,lname,email,company,companyId,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num,state,tel from "
			+ "user where uid = #{uid}")
	User getUserIdByUid(int uid);

	@Select("select uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num,state,tel from "
			+ "user where resetUrl = #{url}")
	User getUserByUrl(String url);

	@Update("update user set last_login_date=now() where email = #{email}")
	void updateLoginDateByEmail(String email);

	@Update("update user set last_login_date=now() where uid = #{uid}")
	void updateLoginDateByUid(int uid);

	@Update("update user set pwd=#{0} where email = #{1}")
	void updatePwd(String pwd, String email);

	@Select("SELECT uid,fname,lname,email,company,privilege,pwd,link,age,degree,school,register_date as registerDate,last_login_date as lastLoginDate,invited_left,invited_num as invitedNum,state,tel,resetUrl,companyId as companyId FROM user WHERE companyId=#{0}")
	List<User> getListByCompany(int companyId);

	@Update("update user set companyId=#{companyId} where email = #{email}")
	void insertCompanyId(String item);

	
//	@Select("SELECT * FROM quiz WHERE owner=#{0} and type=1")
//	List<Quiz> getQuizByUid(int uid);
//	
	
	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS,DESCRIPTION ,start_time as startTime,end_time as endTime "
			+ "FROM QUIZ WHERE OWNER = #{0} and type=#{1}")
	List<Quiz> getQuizByUid(int uid,int quizTypeChallenge);


}
