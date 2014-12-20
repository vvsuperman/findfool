package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Tag;
import zpl.oj.model.responsejson.ResponseInvite;

public interface TagDao {

	@Insert("insert tag (context) values(#{context})")
	void insertTag(String context);
	
	@Select("select tagid from tag where context=#{context}")
	Tag getTagByContext(String context);
	  
	
}