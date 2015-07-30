package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.model.request.User;

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


	public List<CompanyModel> findAll() {
		// TODO Auto-generated method stub
		return companyDao.findAll();
	}


	public List<CompanyModel> findAllByName(String cname) {
		// TODO Auto-generated method stub
		return companyDao.findAllByName(cname);
	}


	public void cDelete(int id) {
	
		companyDao.cDelete(id);
	}


	public CompanyModel findByName(String cname) {
		// TODO Auto-generated method stub
		return companyDao.findByName(cname);
	}


	public List<User> findUserByEmail(String email) {
		return  companyDao.findUserByEmail(email);
		
	}


	public void updateUser(User user, int id) {
		companyDao.updateUser(user,id);
		
	}




}
