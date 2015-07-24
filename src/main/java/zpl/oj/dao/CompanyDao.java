package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.foolrank.model.CompanyModel;

public interface CompanyDao {

	@Insert("INSERT INTO company(name,cover,logo,address,tel,website,description)"
			+ " VALUES (#{name},#{cover},#{logo},#{address},#{tel},#{website},#{description})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int add(CompanyModel company);
	
	@Select("SELECT * FROM company WHERE id=#{0} LIMIT 1")
	CompanyModel getById(int id);
}
