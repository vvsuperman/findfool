package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.CadTest;
import zpl.oj.model.common.Candidate;

public interface CadTestDao {
	@Select("select * FROM cad_test WHERE ctid = #{0}")
	  CadTest getCadTestById(int ctid);
	  
	@Insert(" INSERT INTO cad_test(testid ,begintime,SCORE,pnums,nums,cadid)"
			+ " VALUES (#{testid},#{begintime},  #{score}, #{pnums},#{nums},#{cadid})")
	  void insertCadTest(CadTest cadTest);
	  
	 // void deleteInvite(AoneObjectDeleted objectDeleted);

	@Update("UPDATE cad_test set  TESTID = #{testid},  "
			+ " begintime = #{begintime},   score = #{score},pnums=#{pnums} ,nums=#{nums},state=#{state}  where ctid = #{ctid}")
	  void updateCadTest(CadTest cadTest);
	    
	@Select("SELECT *  FROM cad_test t1, candidate t2 WHERE t1.testid=#{0} and t1.cadid = t2.caid and t2.email=#{1}")
	  CadTest getCdByIds(int testid,String email);  

	
	//根据score计算排名
	@Select("select count(*) from cad_test where score > #{0} and testid=#{1}")
	int getRank(int score,int testid);
	
	//获得参与人数
	@Select("select count(*) from cad_test where testid=#{testid}")
	int getInvolve(int testid);
	
	
	//获取靠前的挑战者信息
	@Select("SELECT t1.score, t2.* from cad_test t1, candidate t2 where t1.cadid = t2.caid and t1.testid=#{0} order by t1.score desc limit 50")
	List<Candidate> getFrontCad(int testid);
}
