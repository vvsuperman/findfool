package zpl.oj.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.TestuserDao;

import com.foolrank.model.CompanyModel;




@Service
public class CompanyService {

	@Autowired
	public CompanyDao  companyDao;

	
	//根据公司名称查找公司
	public CompanyModel findCompanyByName(String companyName) {
		
		return  companyDao.findCompanyByName(companyName);
	}


	//创建新公司
	public void createCompany(CompanyModel params) {
	
		companyDao.createCompany(params);
	}


	public void modifyCompany(CompanyModel params) {
		 companyDao.modifyCompany(params);
	}


	public CompanyModel getByUid(int userId) {
		// TODO Auto-generated method stub
		return companyDao.getByUid(userId);
	}


}
