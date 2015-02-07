package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Logs;
import zpl.oj.model.responsejson.ResponseInvite;

public interface LogsDao {
	@Select("select * from logs where uid=#{uid} and hrid=#{hrid}")
	  Invite getLogById(int uid,int hrid);
	  
	@Insert("insert into logs(action,datatime,uid, hrid) values (#{action},#{datetime},#{uid},#{hrid})")
	  void insertLog(Logs logs);
	
}