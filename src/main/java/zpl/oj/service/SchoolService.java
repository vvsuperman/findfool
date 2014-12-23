package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.School;

public interface SchoolService {
	
	public List<School> getAllSchools();
	
	public List<School> getSchoolsByName1(String name);
	
}
