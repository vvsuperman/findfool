package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ProblemSet;


public interface SetDao {

	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE,DOMAIN_ID AS domainId, OWNER,COMMENT,content"
			+ "  FROM SETS  WHERE problem_set_id = #{0} and state=1")
	ProblemSet getSet(int id);
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE,DOMAIN_ID AS domainId, OWNER,COMMENT,content"
			+ "  FROM SETS  WHERE name = #{0} and state=1")
	ProblemSet getSetByName(String name);

	@Insert("INSERT INTO SETS(NAME,  DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,state,content)"
			+ "VALUES(#{name}, #{date},#{owner},#{domanId},#{comment},1,#{content})")
	void insertSet(ProblemSet set);
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content"
			+ "  FROM SETS where state=1")
	List<ProblemSet> getSets();
	
	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content"
			+ "  FROM SETS WHERE DOMAIN_ID=#{0} and state=1")
	List<ProblemSet> getSetByDomainId(int domainId);

}