package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Labeltest;

public interface LabelService{

	/** 
	 * description:获取所有的系统标签，返回它们的id
	 */
	abstract List<Integer> getSystemLabels();

	/** 
	 * description:在labeltest表中插入一条test的label
	 */
	abstract void insertLabelToLabelTest(int testid,int labelid,int value);
	
	/** 
	 * description:根据labelid获取label的名字
	 */
	abstract String getLabelName(int labelid);
	
	/** 
	 * description:获取test的所有标签
	 */
	abstract List<Labeltest> getLabelsOfTest(int testid);
	
	/** 
	 * description:更新测试的标签是否选择
	 */
	void updateLabelValue(int testid,int labelid,int value);
}