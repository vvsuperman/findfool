package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.LogTakeQuiz;
import zpl.oj.model.common.Problem;

public interface LogTakeQuizDao {
	
	@Insert("INSERT INTO log_takequiz( iid, problemid,  time,num) VALUES(#{iid},  #{problemid},  #{time},#{num})")
	  void saveQuizLog(LogTakeQuiz logTakeQuiz);	  
	
	
	@Select("select * from log_takequiz where iid =#{0}")
	List<LogTakeQuiz> getQuizLogByIid(int iid);

}
