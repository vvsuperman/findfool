package zpl.oj.dao;


import java.util.List;

import zpl.oj.model.common.School;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface SchoolDao {

	@Select("select * from schools")
	List<School> getAllSchools();
	
	@Select("select max(code) from schools")
	int getMaxCode();

	//根据汉字查找学校
	@Select("select * from schools where name = #{0}")
	School getSchoolByName(String name);
	
	//根据汉字查找学校
	@Select("select * from schools where name like concat('%',#{0},'%')")
	List<School> getSchoolsByName(String name);
	
	//添加新的学校
	@Insert("insert into schools (code,name,pinyin,alp) values (#{1},#{0},'','')")
	boolean addSchoolByName(String name,int code);
	
}
