package zpl.oj.dao;

import org.apache.ibatis.annotations.Select;

import com.foolrank.model.CompanyModel;

public interface CompanyDao {

	@Select("SELECT * FROM company WHERE id=#{0} LIMIT 1")
	CompanyModel getById(int id);
}
