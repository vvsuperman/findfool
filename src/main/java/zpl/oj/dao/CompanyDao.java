package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Testuser;
import org.apache.ibatis.annotations.Options;

import com.foolrank.model.CompanyModel;

public interface CompanyDao {

	@Insert("INSERT INTO company(name,cover,logo,address,tel,website,description) VALUES (#{name},#{cover},#{logo},#{address},#{tel},#{website},#{description})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int add(CompanyModel company);

	@Select("SELECT * FROM company WHERE id=#{0} LIMIT 1")
	CompanyModel getById(int id);

	@Select("SELECT * FROM company WHERE name=#{0}")
	CompanyModel findCompanyByName(String companyName);

	@Insert("INSERT INTO company(  name,cover,logo,address,tel,website,description)"
			+ " VALUES( #{name}, #{cover}, #{logo}, #{address},#{tel},#{website},#{description})")
	void createCompany(CompanyModel params);

	
	@Update("update company set name=#{name},cover=#{cover} ,logo=#{logo},address=#{address},tel=#{tel},website=#{website},description=#{description} where id =#{id}")
	void modifyCompany(CompanyModel params);

	@Select("SELECT * FROM company WHERE id=#{0} LIMIT 1")
	CompanyModel getByUid(int userId);
	
	@Update("UPDATE company SET name=#{name},cover=#{cover},logo=#{logo},address=#{address},tel=#{tel},website=#{website},description=#{description} WHERE id=#{id}")
	void modify(CompanyModel company);
}


