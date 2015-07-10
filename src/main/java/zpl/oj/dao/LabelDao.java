package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;
import zpl.oj.util.json.JsonLabel;

public interface LabelDao {

	// 对label表的操作
	@Select("select * from label where type = 0")
	List<Integer> getSystemLabels();

	@Insert("INSERT INTO label(TYPE,NAME)" + " VALUES (#{0}, #{1})")
	void insertNewLabel(int type, String labelname);

	@Select("select name from label where id = #{0}")
	String getLabelNameByLabelId(int labelid);

	@Select("select * from label where id = #{0}")
	Label getLabelById(int labelid);

	@Select("select * from label where name = #{0}")
	List<Label> getLabelByLabelName(String labelname);


	// 对labeltest表的操作
	@Insert("INSERT INTO labeltest(TESTID,LABELID,ISSELECTED)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertIntoLabelTest(int testid, int labelid, int isSelected);

	@Select("select * from labeltest where testid = #{0} and labelid = #{1}")
	List<Labeltest> getLabelTestByTestidAndLabelName(Integer testid,
			Integer labelid);

	@Select("select * from labeltest where testid = #{0}")
	List<Labeltest> getLabelsOfTest(int testid);

	@Update("update labeltest set isSelected = #{2} where testid = #{0} and labelid = #{1}")
	void updateLabelTest(int testid, int labelid, int isSelected);

	// 对labeluser表的操作
	@Insert("INSERT INTO labeluser(inviteid,labelid,value)"
			+ " VALUES (#{0}, #{1}, #{2})")
	void insertIntoLabelUser(int inviteid, int labelid, String value);

	@Select("select * from labeluser where inviteid = #{0} and labelid = #{1}")
	LabelUser getLabelUserByIidAndLid(Integer inviteid, Integer labelid);

	@Update("update labeluser set value = #{2} where inviteid = #{0} and labelid = #{1}")
	void updateLabelUser(int inviteid, int labelid, String value);

	@Select("select * from labeluser where inviteid = #{0}")
	List<LabelUser> getLabelUserByIid(Integer inviteid);

	@Select("select type,id from label where name = #{0}")
	Integer findLableType(String ln);

	


	
	@Select("select id from label where name = #{0}")
	Object findLableId(String ln);

	
	@Delete("delete from labeltest where labelid=#{0} and testid=#{1}")
	void deleteLable(Integer id, Object testid);

	//@Select("select * from labeltest t1,label t2 where t1.testid=#{0} and  t1.labelid<=9 and t1.labelid=t2.id")
	//List<label> getSystemLabels2(Integer testid);
}