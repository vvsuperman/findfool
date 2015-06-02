package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ImgForDao;

public interface LabelDao {

	@Select("select * from label where type = 0")
	List<Integer> getSystemLabels();
	
	@Insert(" INSERT INTO labeltest(TESTID,LABELID,VALUE)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertLabelToLabelTest( int testid, int labelid, int value);
}
