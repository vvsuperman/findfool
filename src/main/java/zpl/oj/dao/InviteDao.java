package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;

public interface InviteDao {

	@Select("select IID,  TESTID,  HRID,  UID,   INVITETIME,  FINISHTIME,  SCORE"
			+ " FROM INVITE WHERE TESTID = #{0}")
	  Invite getInvite(Long id);
	  
	@Insert(" INSERT INTO INVITE(  TESTID,  HRID,   UID,   INVITETIME,  FINISHTIME,  SCORE)"
			+ "  VALUES (#{testid},  #{hrid},  #{uid},  #{invitetime},  #{finishtime},  #{score})")
	  void insertInvite(Invite invite);
	  
	 // void deleteInvite(AoneObjectDeleted objectDeleted);

	@Update("UPDATE INVITE set  TESTID = #{testid},  "
			+ " HRID = #{hrid},   UID = #{uid},   INVITETIME = #{invitetime}, "
			+ " FINISHTIME = #{finishtime},  SCORE = #{score}  where IID = #{iid}")
	  void updateInvite(Invite invite);
	    
	@Select("SELECT  IID,  TESTID,   HRID,   UID,  INVITETIME,  FINISHTIME,  SCORE   "
			+ "  FROM INVITE WHERE testid=#{0}")
	  List<Invite> getInvites(int testid);  
	  
//	  /**主要用于分页，可返回一页的记录*/
//	  List<Invite> select(Invite invite);  
//	  
//	  /**主要用于分页，可返回总记录数*/
//	  int selectCount(Invite invite);  
	    
	}