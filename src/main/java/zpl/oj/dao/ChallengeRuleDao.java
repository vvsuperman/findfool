package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ChallengeRule;

public interface ChallengeRuleDao {
	
	@Select("select * from challenge_rule where domainid = #{0}")
   List< ChallengeRule> findCRById(int domainid);
	
	@Select("select * from challenge_rule where testid = #{0}")
	ChallengeRule findCRByTestid (int testid);

}
