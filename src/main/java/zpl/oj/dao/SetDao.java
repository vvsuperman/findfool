package zpl.oj.dao;

import java.util.List;
import java.util.Set;

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
	
//	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem"
//			+ "  FROM SETS WHERE DOMAIN_ID=#{0} and state=1")
//	List<ProblemSet> getSetByDomainId(int domainId);



	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem"
			+ "  FROM SETS WHERE DOMAIN_ID=#{0} and state=1 and PRIVILEGE<=#{1}")
	List<ProblemSet> getSetByDomainIdAndprivilege(int domainId, int privilege);


	//返回组
	@Select("select groupid FROM SETS WHERE problem_set_id=#{0} and state=1")
	int getGroupBySetid(int setid);


	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem,groupid"
			+ "  FROM SETS where groupid=#{0} and state=1")
	List<ProblemSet> getSetByGroupid(int groupId);


	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem,groupid"
			+ "  FROM SETS where privilege=#{0}")
	List<ProblemSet> getSetsByPrivilege(int privilege);

	@Select("select PROBLEM_SET_ID as problemSetId, NAME, DATE, OWNER,DOMAIN_ID AS domainId,COMMENT,content,faceproblem,groupid"
			+ "  FROM SETS where problem_set_id=#{0}")
	ProblemSet getSetsBtSetId(Integer problemSetId);

}