package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Labeltest;

public interface LabelDao {

	@Select("select * from label where type = 0")
	List<Integer> getSystemLabels();
	
	@Insert(" INSERT INTO labeltest(TESTID,LABELID,VALUE)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertLabelToLabelTest(int testid, int labelid, int value);
	
	@Select("select name from label where id = #{0}")
	String getLabelName(int labelid);
	
	@Select("select * from labeltest where testid = #{0}")
	List<Labeltest> getLabelsOfTest(int testid);
	
	@Update("update labeltest set value = #{2} where testid = #{0} and labelid = # {1}")
	void updateLabelValue(int testid,int labelid,int value);
	
}
