package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.foolrank.model.CompanyModel;

public interface CompanyDao {

	@Insert("INSERT INTO company(name,cover,logo,address,tel,website,description) VALUES (#{name},#{cover},#{logo},#{address},#{tel},#{website},#{description})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int add(CompanyModel company);

	@Select("SELECT * FROM company WHERE id=#{0} LIMIT 1")
	CompanyModel getById(int id);

	@Update("UPDATE company SET name=#{name},cover=#{cover},logo=#{logo},address=#{address},tel=#{tel},website=#{website},description=#{description} WHERE id=#{id}")
	void modify(CompanyModel company);
}
