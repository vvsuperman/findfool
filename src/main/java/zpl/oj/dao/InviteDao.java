package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;
import zpl.oj.model.responsejson.ResponseInvite;

public interface InviteDao {

	@Select("select * FROM INVITE WHERE IID = #{0}")
	  Invite getInviteById(int iid);

	@Insert(" INSERT INTO INVITE(  TESTID,  HRID,   UID,   INVITETIME,begintime,  FINISHTIME,  SCORE,totalscore,STATE,DURATION,pwd,starttime,deadtime,openCamera,system,parentquiz)"
			+ " VALUES (#{testid}, #{hrid}, #{uid}, #{invitetime},#{begintime}, #{finishtime}, #{score}, #{totalScore}, #{state}, #{duration},#{pwd},#{starttime},#{deadtime},#{openCamera},#{system},#{parentquiz})")
	  void insertInvite(Invite invite);
	
	@Insert("INSERT INTO INVITE(TESTID,HRID,UID,INVITETIME,begintime,FINISHTIME,SCORE,totalscore,STATE,DURATION,pwd,starttime,deadtime,openCamera,system,parentquiz)"
			+ " VALUES (#{testid}, #{hrid}, #{uid}, #{invitetime},#{begintime}, #{finishtime}, #{score}, #{totalScore}, #{state}, #{duration},#{pwd},#{starttime},#{deadtime},#{openCamera},#{system},#{parentquiz})")
	@Options(useGeneratedKeys = true, keyProperty = "iid", keyColumn = "iid")
	int add(Invite invite);
	  
	 // void deleteInvite(AoneObjectDeleted objectDeleted);

	@Update("UPDATE INVITE set  TESTID = #{testid},  "
			+ " HRID = #{hrid},   UID = #{uid},begintime=#{begintime},   INVITETIME = #{invitetime},DURATION=#{duration},"
			+ " FINISHTIME = #{finishtime},  SCORE = #{score},totalscore=#{totalScore} ,STATE=#{state},pwd=#{pwd},openCamera=#{openCamera},starttime=#{starttime},deadtime=#{deadtime},system=#{system},parentquiz=#{parentquiz} where IID = #{iid}")
	  void updateInvite(Invite invite);

	    
	@Select("SELECT *  FROM INVITE t1, testuser t2 WHERE t1.testid=#{0} and t1.uid = t2.tuid and t2.email=#{1}")
	  Invite getInvites(int testid,String email);  
	
	
	@Select("SELECT  *  FROM INVITE t1 WHERE t1.testid=#{0} and t1.uid =#{1}")
	  Invite getInvitesByIds(int testid,int tuid);

	@Select("select * from invite where testid =#{0}")
	List<Invite> getInviteByTid(Integer testid);  
	
	
	/*
	 * 返回测试中和测试完成的测试，倒序排列
	 * */
	@Select("select t2.email,t2.username,t1.iid,t1.testid,t1.uid ,t1.invitetime,t1.begintime,t1.starttime,t1.finishtime,t1.duration,t1.score,t1.totalscore,t1.state,t1.pwd,t1.parentquiz "
			+ "from invite t1,testuser t2 "
			+ "where  t1.uid= t2.tuid and t1.testid =#{0} and t1.state=#{1} order by t1.state,t1.starttime,t1.inviteTime desc")
	List<ResponseInvite> getOrderInviteByTid(Integer testid, Integer state); 
	
	//某一个测试的发送人数
	@Select("select count(*) from invite where testid=#{0}")
	int countInvites(Integer testid);	
	
	//某一个测试的完成人数
	@Select("select count(*) from invite where testid=#{0} and state=2") 
	int countInviteFinished(Integer testid);
	
	@Select("select count(*) from invite where testid=#{0} and state=#{1}") 
	int countInviteByState(Integer testid, int state);

	
	@Select("select * FROM INVITE WHERE uid = #{0}")
	List<Invite> getInviteByUid(int tuid);

	
	@Select("select * from invite where testid =#{0} order by score desc")
	List<Invite> getInviteByTidOrderByScore(Integer testid);
	
	
	@Select("select count(*) from invite where testid = #{0} and score > (select score from invite where uid = #{1} and testid= #{0})")
	int getRankByTidUid(Integer testid,Integer userid);

	
	@Select("select * FROM INVITE WHERE uid = #{0} and testid = #{1}")
	Invite getInviteByTestidAndUser(int tuid, int testid);

	
	@Select("select * FROM INVITE WHERE uid = #{0} and parentquiz = #{1}")
	Invite getInviteByTestidAndParent(int tuid, int testid);

	
	@Select("select t2.username,t2.email,t1.iid,t1.testid,t1.parentquiz,t1.uid ,t1.invitetime,t1.begintime,t1.starttime,t1.finishtime,t1.duration,t1.score,t1.totalscore,t1.state,t1.pwd,t1.parentquiz "
			+ "from invite t1,testuser t2 "
			+ "where  t1.uid= t2.tuid  and t1.state=#{0} and t1.parentquiz =#{1} order by t1.state,t1.starttime,t1.inviteTime desc")
	List<ResponseInvite> getOrderInviteByStateAndP(Integer state, int parentquiz);

	
	@Select("select count(*) from invite where parentquiz=#{0}")
	int countInvitesByP(Integer quizid);

	@Select("select count(*) from invite where parentquiz=#{0} and state=2") 
	int countInviteFinishedByP(Integer quizid);
	
	@Select("SELECT *  FROM INVITE t1, testuser t2 WHERE t1.parentquiz=#{0} and t1.uid = t2.tuid and t2.email=#{1}")
	Invite getInvitesByP(Integer quizid, String email);

	
	@Select("select * from invite where parentquiz =#{0}")
	List<Invite> getInviteByParentQuiz(int parentQuizId);

	@Select("select count(*) from invite where parentquiz = #{0} and score > (select score from invite where parentquiz = #{0} and uid =#{1} and testid = #{2}) ")
	int getRankByParent(Integer parentquiz,Integer uid,Integer testid);
	
	
}