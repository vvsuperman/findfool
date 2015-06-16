package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Candidate;



public interface CandidateDao {
	 
  @Insert("INSERT INTO Candidate( username,email,school,company,blog,age,tel,registerdate,lastlogindate,state,discipline,gratime,degree,pwd)"
			+ " VALUES( #{username}, #{email}, #{school}, #{company},#{blog},#{age},#{tel},#{registerDate},#{lastLoginDate},#{state},#{discipline},#{gratime},#{degree},#{pwd})")
  void insertUser(Candidate cad);
  
  @Update("update Candidate set username=#{username},email=#{email} ,school=#{school},company=#{company},blog=#{blog},age=#{age},tel=#{tel},gratime=#{gratime},discipline=#{discipline},"
  		+ "registerdate = #{registerDate},lastlogindate=#{lastLoginDate},faceid=#{faceid},degree=#{degree}  where tuid =#{tuid}")
  void updateUserById(Candidate cad);
  
  @Update("update Candidate set username=#{username},email=#{email} ,school=#{school},company=#{company},blog=#{blog},age=#{age},tel=#{tel},gratime=#{gratime},discipline=#{discipline},"
	  		+ "registerdate = #{registerDate},lastlogindate=#{lastLoginDate},faceid=#{faceid},degree=#{degree}  where email =#{email}")
  void updateUserByEmail(Candidate cad);
  
  @Update("update Candidate set username=#{username},school=#{school},company=#{company},blog=#{blog},age=#{age},gratime=#{gratime},discipline=#{discipline},"
	  		+ "registerdate = #{registerDate},lastlogindate=#{lastLoginDate},faceid=#{faceid},degree=#{degree}  where email =#{email}")
  void updateCadByEmail(Candidate cad);
  
  
  @Update("update Candidate set pwd=#{0}  where email =#{1}")
void updatePwdByEmail(String pwd,String email);
  
  @Select("select * from Candidate where email = #{0}")
  Candidate findUserByEmail(String email);
  
  @Select("select count(*) from Candidate where email = #{0}")
  int countUserByEmail(String email);
  
  @Select("select * from Candidate where email = #{0}")
  Candidate findUserByEmailPwd(String email,String pwd);
  
  @Select("select * from Candidate where tel = #{0}")
  Candidate findTuserByTel(String tel);
  
  @Select("select * from Candidate where tuid = #{0}")
  Candidate findTuserById(int tuid);
    
}