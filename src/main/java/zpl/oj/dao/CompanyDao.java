package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;

import org.apache.ibatis.annotations.Options;

import com.foolrank.model.CompanyModel;

public interface CompanyDao {

//	@Insert("INSERT INTO company(name,cover,logo,address,tel,website,description) VALUES (#{name},#{cover},#{logo},#{address},#{tel},#{website},#{description})")
//	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
//	int add(CompanyModel company);

	

	@Insert("INSERT INTO company(name,address,tel,website,description) VALUES (#{name},#{address},#{tel},#{website},#{description})")
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

	
	@Select("SELECT * FROM company")
	List<CompanyModel> findAll();

	
	@Select("SELECT * FROM company WHERE name LIKE CONCAT(CONCAT('%', #{0}), '%')")
	List<CompanyModel> findAllByName(String cname);

	
	@Delete("DELETE from company where id=#{id}")
	void cDelete(int id);

	
	@Update("update company set cover=#{cover} ,logo=#{logo} where id =#{id}")
	void updateimage(CompanyModel company);

	@Select("SELECT * FROM company WHERE id=#{id}")
	void show(CompanyModel company);

	@Select("SELECT * FROM company WHERE name=#{cname}")
	CompanyModel findByName(String cname);

	@Select("SELECT * FROM user WHERE email=#{0}")
	List<User> findUserByEmail(String email);

	
	@Update("update user set companyId=#{1}  where id =#{id}")
	void updateUser(User user, int id);
	
	@Select("SELECT * FROM company WHERE status=#{0} LIMIT #{1},#{2}")
	List<CompanyModel> getList(int status, int offset, int count);
}
