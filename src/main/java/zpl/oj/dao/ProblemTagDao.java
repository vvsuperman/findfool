package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

public interface ProblemTagDao {

	@Select("select b.context from tagproblem as a,tag as b"
			+ " where a.problemid=#{0} and a.tagid = b.tagid")
	List<String> getTagByProblemId(Integer problemId);
	
	@Select("select a.problemid form tagproblem as a,tag as b,problem as c"
			+ " where a.tagid = b.tagid and c.isdelete=0 and b.context like %#{0}%")
	List<Integer> getProblemIdbyTag(String tag);
	
	@SelectProvider(type = ProblemTagDaoSQL.class,method="getCountProblemIdbyTagSiteSQL")
	Integer getCountProblemIdbyTagSite(@Param("tag") String tag,@Param("type") Integer type,@Param("set") Integer set);
	
	@SelectProvider(type = ProblemTagDaoSQL.class,method="getProblemIdbyTagSiteSQL")
	List<Integer> getProblemIdbyTagSite(@Param("tag") String tag,@Param("type") Integer type,@Param("set") Integer set);

	@SelectProvider(type = ProblemTagDaoSQL.class,method="getCountProblemIdbyTagUserSQL")
	Integer getCountProblemIdbyTagUser(@Param("tag") String tag,@Param("type") Integer type,@Param("id") Integer uid);
	
	@SelectProvider(type = ProblemTagDaoSQL.class,method="getProblemIdbyTagUserSQL")
	List<Integer> getProblemIdbyTagUser(@Param("tag") String tag,@Param("type") Integer type,@Param("id") Integer uid);
	
	@Insert("insert tagproblem (tagid,problemid) values(#{0},#{1})")
	void insertTagProblem(Integer tagid,Integer problemid);
	
	@Insert("insert tag (context) values(#{context})")
	void insertTag(String context);
	
	@Select("select tagid from tag where context=#{context}")
	Integer getTagId(String context);
	
}
