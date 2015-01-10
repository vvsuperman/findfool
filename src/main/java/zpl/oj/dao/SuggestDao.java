package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.common.Suggest;


public interface SuggestDao {

	@Insert("INSERT INTO SUGGEST(CONTENT,SUGGEST_TIME,uid) VALUES(#{content},#{suggestTime},#{uid})")
	void insertSuggest(Suggest suggest);
	
	@Select("select s.content,s.suggest_time as suggestTime,s.uid,u.email from suggest s,user u where s.id = u.uid ")
	List<Suggest> getSetByName();
}