package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ProblemSet;


public interface SetDao {

	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE,DOMAIN_ID AS domainId, OWNER,COMMENT,content,faceproblem"
			+ "  FROM SETS  WHERE problem_set_id = #{0} and state=1")
	ProblemSet getSet(int id);
	
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE,DOMAIN_ID AS domainId, OWNER,COMMENT,content,faceproblem"
			+ "  FROM SETS  WHERE owner = #{0}")
	List<ProblemSet> getSetByOwn(int uid);
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE,DOMAIN_ID AS domainId, OWNER,COMMENT,content,faceproblem"
			+ "  FROM SETS  WHERE name = #{0} and state=1")
	ProblemSet getSetByName(String name);

	@Insert("INSERT INTO SETS(NAME,  DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,state,content,faceproblem)"
			+ "VALUES(#{name}, #{date},#{owner},#{domanId},#{comment},1,#{content},#{facdproblem})")
	void insertSet(ProblemSet set);
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem"
			+ "  FROM SETS where state=1")
	List<ProblemSet> getSets();
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem"
			+ "  FROM SETS WHERE DOMAIN_ID=#{0} and state=1")
	List<ProblemSet> getSetByDomainId(int domainId);

}