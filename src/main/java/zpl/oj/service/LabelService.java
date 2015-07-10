package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;
import zpl.oj.util.json.JsonLabel;

public interface LabelService{

	
	//对label表的操作
	/** 
	 * description:获取所有的系统标签，返回它们的id
	 */
	abstract List<Integer> getSystemLabels();
	//abstract List<JsonLabel> getSystemLabels2(Integer testid);

	/** 
	 * description:向labe表中加入新的label
	 */
	void insertNewLabel(int type,String labelname);
	
	/** 
	 * description:根据labelid获取label的名字
	 */
	abstract String getLabelNameByLabelId(int labelid);
	
	/** 
	 * description:根据labelname获取label
	 */
	abstract Label getLabelByLabelName(String labelname);

	/** 
	 * description:根据labelname获取label
	 */
	abstract Label getLabelById(Integer labelid);

	/** 
	 * description:更新测试的标签是否选择
	 */
	void updateLabelTest(int testid,int labelid,int isSelected);

	/** 
	 * description:判断用户自定义的新标签是否已经被其他用户添加过了
	 */
	boolean isLableExist(String labelname);

	//对labeltest表的操作
	/** 
	 * description:获取test的所有标签
	 */
	abstract List<Labeltest> getLabelsOfTest(int testid);
	
	/** 
	 * description:在labeltest表中插入一条test的label
	 */
	abstract void insertIntoLabelTest(int testid,int labelid,int isSelected);
	
	/** 
	 * description:判断用户自定义的新标签是否已经被添加到test设置中
	 */
	abstract boolean isLableTestExist(Integer testid,Integer labelid);

	//对labeluser表的操作
	abstract void insertIntoLabelUser(Integer inviteid, int labelid, String value);
	
	abstract LabelUser getLabelUserByIidAndLid(Integer inviteid,Integer labelid);
	
	abstract List<LabelUser> getLabelUserByIid(Integer inviteid);
	
	abstract void updateLabelUser(int testid,int labelid,String value);

	List<JsonLabel> getTestLabels(Integer testid);

	abstract Integer findLableType(String ln);

	



	abstract Object findLableId(String ln);



	abstract void deleteLable(Integer id, Object testid);

	//abstract List<JsonLabel> getTestLabelsSeclected(Integer testid);
}