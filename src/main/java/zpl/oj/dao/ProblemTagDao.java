package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface ProblemTagDao {

	@Select("select b.context from tagproblem as a,tag as b"
			+ " where a.problemid=#{0} and a.tagid = b.tagid")
	List<String> getTagByProblemId(int problemId);
	
	@Select("select a.problemid form tagproblem as a,tag as b"
			+ " where a.tagid = b.tagid and b.context like %#{0}%")
	List<Integer> getProblemIdbyTag(String tag);
	
	@Insert("insert tagproblem (tagid,problemid) values(#{0},#{1})")
	void insertTagProblem(Integer tagid,Integer problemid);
	
	@Insert("insert tag (context) values(#{context})")
	void insertTag(String context);
	
	@Select("select tagid from tag where context=#{context}")
	Integer getTagId(String context);
	
}
