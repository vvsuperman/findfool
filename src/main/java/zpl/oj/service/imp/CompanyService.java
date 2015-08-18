
package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.User;
import zpl.oj.util.StringUtil;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;

import com.foolrank.model.CompanyModel;
import com.foolrank.response.json.CompanyJson;
import com.qiniu.util.Auth;




@Service
public class CompanyService {

	@Autowired
	public CompanyDao  companyDao;
	
	@Autowired
	private  UserDao  userDao;

	
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


	public Map<String, Object> getcomTail(int comid) {
		CompanyJson item = null;

		CompanyModel company = companyDao.getById(comid);
		if (company != null) {
			item = new CompanyJson();
			item.setId(company.getId());
			item.setName(company.getName());
			item.setCover(company.getCover());
			item.setAddress(company.getAddress());
			item.setTel(company.getTel());
			item.setLogo(company.getLogo());
			item.setWebsite(company.getWebsite());
			item.setDescription(company.getDescription());
		}

		List<User> userList = userDao.getListByCompany(comid);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Quiz> quizList = new ArrayList<Quiz>();
		String nowtime = StringUtil.nowDateTime();
		for (User user : userList) {
			// 做常量
			List<Quiz> quizListUse = userDao.getQuizByUid(user.getUid(),
					ExamConstant.QUIZ_TYPE_CHALLENGE);
			for (Quiz quiz : quizListUse) {
				if(quiz.getStartTime()!="" && nowtime.compareTo(quiz.getStartTime())<0 ){
				   quiz.setStatus(1);
				}else if(quiz.getEndTime()!="" &&nowtime.compareTo( quiz.getEndTime())>0){
				quiz.setStatus(3);
				}else{
					quiz.setStatus(2);
				}
						
				quizList.add(quiz);
			}

		}
		map.put("quizList", quizList);
		String imgCover = item.getCover();
		String imgLogo = item.getLogo();

		String coverLocation = getImg(imgCover);
		String logoLocation = getImg(imgLogo);

		item.setCoverLocation(coverLocation);
		item.setLogoLocation(logoLocation);
		map.put("company", item);
		return map;

	}


	public String getImg(String param) {
		String imgHome=(String)PropertiesUtil.getContextProperty("ImgUploadHome");
		String accessKey=(String)PropertiesUtil.getContextProperty("qiniuAccessKey");
		String secretKey=(String)PropertiesUtil.getContextProperty("qiniuSecretKey");	
		String 	paramLocation=imgHome+param;
			Auth auth=Auth.create(accessKey, secretKey);
			paramLocation=auth.privateDownloadUrl(paramLocation);
		return paramLocation;
	}



}


