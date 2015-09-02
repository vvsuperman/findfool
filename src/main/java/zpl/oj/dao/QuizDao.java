package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizTemplete;
import zpl.oj.model.request.User;

import com.foolrank.provider.QuizProvider;

public interface QuizDao {
	
	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS, type, status "
			+ "FROM QUIZ WHERE signed_key = #{0}")
	Quiz getQuizByKey(String signedkey);
	


	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS, type, status , signed_key as signedKey, start_time as startTime, end_time as endTime ,openCamera, logo, description "
			+ "FROM QUIZ WHERE QUIZID = #{0}")
	Quiz getQuiz(int tid);

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE name = #{0}")
	Quiz getQuizByName(String name);

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE NAME=#{0} and OWNER = #{1} ORDER BY QUIZID DESC limit 1")
	Quiz getQuizByOwnerAndName(String name, int uid);

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO,  UUID, EMAILS "
			+ "FROM QUIZ WHERE UUID = #{0} ORDER BY QUIZID DanESC limit 1")
	Quiz getNewestQuizByUuid(int uuid);

	@Select("select QUIZID, OWNER, NAME, DATE, TIME, EXTRA_INFO as extraInfo,  UUID, EMAILS "
			+ "FROM QUIZ WHERE OWNER = #{0} ORDER BY QUIZID DESC limit 1")
	Quiz getNewestQuizByOwner(int owner);

	@Insert("INSERT INTO QUIZ( OWNER,  NAME,  DATE,  TIME,  EXTRA_INFO,  UUID,  EMAILS)"
			+ " VALUES("
			+ "#{owner},#{name},#{date},#{time},#{extraInfo},#{uuid},#{emails})")
	void insertQuiz(Quiz quiz);

	void deleteQuiz(Quiz quiz);

	@Update("UPDATE QUIZ set OWNER = #{owner},   NAME = #{name},  "
			+ "DATE = #{date},  TIME = #{time},  EXTRA_INFO = #{extraInfo}, start_time=#{startTime},end_time=#{endTime},openCamera=#{openCamera},"
			+ "UUID = #{uuid},  EMAILS = #{emails}, logo = #{logo}, DESCRIPTION=#{description}, signed_key=#{signedKey} ,type=#{type}"
			+ " where QUIZID = #{quizid}")
	void updateQuiz(Quiz quiz);

	@Select("SELECT QUIZID,  OWNER, NAME,  DATE,  TIME,  EXTRA_INFO as extraInfo,  UUID,  EMAILS,signed_key as singedKey "
			+ " FROM quiz WHERE OWNER=#{0}  order by date desc ")
	List<Quiz> getQuizs(int owner);

	@Select("select * from quiz_templete where quizTName = #{quizName}")
	QuizTemplete getQuizTByName(String quizName);

	@SelectProvider(type = QuizProvider.class, method = "getChallengeListByUsers")
	List<Quiz> getChallengeListByUsers(@Param("users") List<User> userList, @Param("status") int status, @Param("offset") int offset, @Param("count") int count);

	@Select("SELECT quizid,owner,name,date,time,extra_info as extraInfo,uuid,emails,type,logo,description,start_time as startTime,end_time as endTime,signed_key as signedKey,create_time as createTime,status FROM quiz WHERE type=1 AND status=#{0} ORDER BY start_time ASC LIMIT #{1},#{2}")
	List<Quiz> getChallengeListByStatus(int status, int offset, int count);

	
	@Select("SELECT quizid,owner,name,date,time,extra_info as extraInfo,uuid,emails,type,logo,description,start_time as startTime,end_time as endTime,signed_key as signedKey,create_time as createTime,status FROM quiz WHERE type=#{0}  ORDER BY start_time ASC")
	List<Quiz> getQuizByType(int quizTypeFrchallenge);
	
	@Select("SELECT quizid,owner,name,date,time,extra_info as extraInfo,uuid,emails,type,logo,description,start_time as startTime,end_time as endTime,signed_key as signedKey,create_time as createTime,status FROM quiz WHERE type=1 AND signed_key=#{0}")
	Quiz getChallengeBySignedKey(String signedKey);


	
	@Update("UPDATE QUIZ set OWNER = #{owner},   NAME = #{name},  "
			+ "DATE = #{date},  TIME = #{time},  EXTRA_INFO = #{extraInfo}, "
			+ "UUID = #{uuid},  EMAILS = #{emails}, signed_key=#{signedKey},"
			+"start_time=#{startTime},end_time=#{endTime},openCamera=#{openCamera}"
			+ " where QUIZID = #{quizid}")
	void saveTime(Quiz quiz);
}
