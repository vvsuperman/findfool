package zpl.oj.dao;


import java.util.List;
import zpl.oj.model.common.School;
import org.apache.ibatis.annotations.Select;

public interface SchoolDao {

	@Select("select * from schools")
	List<School> getAllSchools();

	//根据汉字查找学校
	@Select("select * from schools where name1 like #{name}")
	List<School> getSchoolsByName1(String name);
	
}
