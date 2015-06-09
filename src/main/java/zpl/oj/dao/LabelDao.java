package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;

public interface LabelDao {

	//对label表的操作
	@Select("select * from label where type = 0")
	List<Integer> getSystemLabels();

	@Insert("INSERT INTO label(TYPE,NAME)"
			+ " VALUES (#{0}, #{1})")
	void insertNewLabel(int type,String labelname);

	@Select("select name from label where id = #{0}")
	String getLabelNameByLabelId(int labelid);
	
	@Select("select * from label where id = #{0}")
	Label getLabelById(int labelid);

	@Select("select * from label where name = #{0}")
	List<Label> getLabelByLabelName(String labelname);
	
	//对labeltest表的操作
	@Insert("INSERT INTO labeltest(TESTID,LABELID,VALUE)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertLabelToLabelTest(int testid, int labelid, int value);
	
	@Select("select * from labeltest where testid = #{0} and labelid = #{1}")
	List<Labeltest> getLabelTestByTestidAndLabelName(Integer testid,Integer labelid);
	
	@Select("select * from labeltest where testid = #{0}")
	List<Labeltest> getLabelsOfTest(int testid);
	
	@Update("update labeltest set value = #{2} where testid = #{0} and labelid = #{1}")
	void updateLabelValue(int testid,int labelid,int value);
	
	//对labeluser表的操作
	@Insert("INSERT INTO labeluser(testuserid,labelid,value)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertIntoLabelUser(int testuserid, int labelid, String value);
	
	@Select("select * from labeluser where testuserid = #{0} and labelid = #{1}")
	LabelUser getLabelUserByTidAndLid(Integer testuserid,Integer labelid);
	
	@Update("update labeluser set value = #{2} where testid = #{0} and labelid = #{1}")
	void updateLabelUser(int testid,int labelid,String value);
	
}
