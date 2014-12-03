package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;

public interface InviteDao {

	@Select("select IID,  TESTID,  HRID,  UID,   INVITETIME,  FINISHTIME,  SCORE,STATE,DURATION"
			+ " FROM INVITE WHERE IID = #{0}")
	  Invite getInviteById(int iid);
	  
	@Insert(" INSERT INTO INVITE(  TESTID,  HRID,   UID,   INVITETIME,  FINISHTIME,  SCORE,STATE,DURATION)"
			+ "  VALUES (#{testid},  #{hrid},  #{uid},  #{invitetime},  #{finishtime},  #{score},#{state},#{duration})")
	  void insertInvite(Invite invite);
	  
	 // void deleteInvite(AoneObjectDeleted objectDeleted);

	@Update("UPDATE INVITE set  TESTID = #{testid},  "
			+ " HRID = #{hrid},   UID = #{uid},   INVITETIME = #{invitetime},DURATION=#{duration},"
			+ " FINISHTIME = #{finishtime},  SCORE = #{score},STATE=#{state}  where IID = #{iid}")
	  void updateInvite(Invite invite);
	    
	@Select("SELECT  t1.IID,  t1.TESTID,   t1.HRID,   t1.UID,  t1.INVITETIME,  t1.FINISHTIME,  t1.SCORE ,t1.STATE,t1.DURATION   "
			+ "  FROM INVITE t1, testuser t2 WHERE t1.testid=#{0} and t1.uid = t2.tuid and t2.email=#{1}")
	  Invite getInvites(int testid,String email);  
	
	
	@Select("SELECT  t1.IID,  t1.TESTID,   t1.HRID,   t1.UID,  t1.INVITETIME,  t1.FINISHTIME,  t1.SCORE ,t1.STATE,t1.DURATION  "
			+ "  FROM INVITE t1 WHERE t1.testid=#{0} and t1.uid =#{1}")
	  Invite getInvitesByIds(int testid,int tuid);  
	  
}