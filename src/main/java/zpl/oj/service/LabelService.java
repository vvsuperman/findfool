package zpl.oj.service;

import java.util.List;

public interface LabelService{

	/** 
	 * description:获取所有的系统标签，返回它们的id
	 */
	abstract List<Integer> getSystemLabels();
	
	/** 
	 * description:在labeltest表中插入一条test的label
	 */
	abstract void insertLabelToLabelTest(int testid,int labelid,int value);
}