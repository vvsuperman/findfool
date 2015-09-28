package zpl.oj.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.common.RandomQuizSet;


public interface RandomQuizDao {

	@Insert("INSERT INTO randomquizset(testid,level,problem_set_id,num)"
			+ "VALUES(#{testid},#{level},#{problemSetId},#{num})")
	void add(RandomQuizSet randomQuiz);

	
	@Select("SELECT randomid,testid,num,level,problem_set_id as problemSetId FROM randomquizset WHERE testid=#{0}  ORDER BY level ASC")
	List<RandomQuizSet> getListByTestid(int testid);

	@Select("select sum(num) from randomquizset where testid = #{0}")
	int countPnumInRQ(Integer quizid);

	@Select("SELECT randomid,testid,num,level,problem_set_id as problemSetId FROM randomquizset WHERE testid=#{0} and problem_set_id=#{1} and level=#{2}")
	RandomQuizSet getRandomQuizByPsid(Integer quizid,Integer problemSetId, int minlevel);

	@Update("UPDATE randomquizset SET testid=#{testid},level=#{level},problem_set_id=#{problemSetId},num=#{num} WHERE randomid=#{randomid}")
	void modify(RandomQuizSet randomQuiz);

}