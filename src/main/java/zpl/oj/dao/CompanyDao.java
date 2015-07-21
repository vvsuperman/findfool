package zpl.oj.dao;

import org.apache.ibatis.annotations.Select;

import com.foolrank.model.CompanyInfo;

public interface CompanyDao {

	@Select("SELECT * FROM company_info WHERE company_id=#{0} LIMIT 1")
	CompanyInfo getInfoById(int companyId);
}
