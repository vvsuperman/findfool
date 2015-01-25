package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.School;

public interface SchoolService {

	public List<School> getAllSchools();
	
	/*
	 * 获取code字段的最大值
	 */
	public int getMaxCode();

	public School getSchoolByName(String name);
	
	public List<School> getSchoolsByName(String name);
	
	/*
	 * 将名字为name的学校添加到数据库中
	 */
	public boolean addSchoolByName(String name);
	
}
