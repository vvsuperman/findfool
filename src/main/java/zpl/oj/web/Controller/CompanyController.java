package zpl.oj.web.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.foolrank.model.CompanyModel;
import com.foolrank.response.json.CompanyJson;
import com.foolrank.util.RequestUtil;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.CompanyService;
import zpl.oj.util.Constant.ExamConstant;

@Controller
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ImgUploadService imgUploadService;

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/getById")
	@ResponseBody
	public ResponseBase getById(@RequestBody Map<String, String> params) {
		String strCompanyId = params.get("cid");
		int companyId = strCompanyId == null ? 0 : Integer
				.parseInt(strCompanyId.trim());
		CompanyJson item = null;
		if (companyId > 0) {
			CompanyModel company = companyDao.getById(companyId);
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
		}
		ResponseBase rb = new ResponseBase();
		rb.setMessage(item);

		return rb;
	}
	


	// 根据公司id，查找该公司所有挑战赛
	@RequestMapping(value = "/findAllTest")
	@ResponseBody
	public ResponseBase findAllTest(@RequestBody Map<String, String> params) {
		String comid = params.get("comid");

		return null;
	}

	// 根据用户id返回公司信息
	@RequestMapping(value = "/getByUid")
	@ResponseBody
	public ResponseBase getByUid(@RequestBody Map<String, String> params) {
		String strUserId = params.get("uid");
		int userId = strUserId == null ? 0 : Integer.parseInt(strUserId.trim());
		ResponseBase rb = new ResponseBase();
		if (userId <= 0) {
			rb.setState(1);
			rb.setMessage("参数传递错误");
			return rb;
		}
		CompanyModel company = companyService.getByUid(userId);
		rb.setMessage(company);
		return rb;
	}

	// 创建新公司,第一步创建公司的方法
	@RequestMapping(value = "/create")
	@ResponseBody
	public ResponseBase create(@RequestBody Map<String, String> params) {
		String name = RequestUtil.getStringParam(params, "name", true);
		String tel = RequestUtil.getStringParam(params, "mobile", true);
		String address = RequestUtil.getStringParam(params, "address", true);
		String website = RequestUtil.getStringParam(params, "website", true);
		String description = RequestUtil.getStringParam(params, "description",
				true);
		CompanyModel company = new CompanyModel();
		company.setName(name);
		company.setAddress(address);
		company.setWebsite(website);
		company.setDescription(description);
		company.setTel(tel);
		companyDao.add(company);
		CompanyModel companymodel = companyService
				.findByName(company.getName());

		ResponseBase rb = new ResponseBase();
		rb.setMessage(companymodel);

		return rb;
	}
	
	

	@RequestMapping(value = "/updateimage")
	@ResponseBody
	public ResponseBase updateimage(@RequestBody Map<String, String> params) {
		int id = RequestUtil.getIntParam(params, "id", 0);

		String cover = RequestUtil.getStringParam(params, "cover", true);
		String logo = RequestUtil.getStringParam(params, "logo", true);
		String email = RequestUtil.getStringParam(params, "email", true);
		CompanyModel company = companyDao.getById(id);

		company.setId(id);
		company.setCover(cover);
		company.setLogo(logo);
		companyDao.updateimage(company);
		List<User> userlist = companyService.findUserByEmail(email);

		if (userlist != null) {
			for (User user : userlist) {
				companyService.updateUser(user, company.getId());

			}

		}
		ResponseBase rb = new ResponseBase();
		rb.setMessage(company.getId());

		return rb;
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public ResponseBase modify(@RequestBody Map<String, String> params) {
		int id = RequestUtil.getIntParam(params, "cid", 0);
		String name = RequestUtil.getStringParam(params, "name", true);
		String cover = RequestUtil.getStringParam(params, "cover", true);
		String logo = RequestUtil.getStringParam(params, "logo", true);
		String address = RequestUtil.getStringParam(params, "address", true);
		String tel = RequestUtil.getStringParam(params, "tel", true);
		String website = RequestUtil.getStringParam(params, "website", true);
		String description = RequestUtil.getStringParam(params, "description",
				true);
		CompanyModel company = new CompanyModel();
		company.setId(id);
		company.setName(name);
		// company.setCover(cover);
		// company.setLogo(logo);
		company.setAddress(address);
		company.setTel(tel);
		company.setWebsite(website);
		company.setDescription(description);
		companyDao.modify(company);
		ResponseBase rb = new ResponseBase();
		rb.setMessage(company.getId());

		return rb;
	}

	// 查找全部公司，返回所有公司信息，ID，名称，电话
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public ResponseBase findAll() {
		List<CompanyModel> companyList = companyService.findAll();

		ResponseBase rb = new ResponseBase();
		rb.setMessage(companyList);

		return rb;
	}

	// 查找全部公司，返回所有公司信息，ID，名称，电话
	@RequestMapping(value = "/findAllByName")
	@ResponseBody
	public ResponseBase findAllByName(@RequestBody Map<String, String> params) {
		String cname = RequestUtil.getStringParam(params, "cname", true);

		List<CompanyModel> companyList = companyService.findAllByName(cname);

		ResponseBase rb = new ResponseBase();
		rb.setMessage(companyList);

		return rb;
	}

	// 根据公司的名称返回公司的信息
	@RequestMapping(value = "/findByName")
	@ResponseBody
	public ResponseBase findByName(@RequestBody Map<String, String> params) {
		String cname = RequestUtil.getStringParam(params, "cname", true);

		CompanyModel company = companyService.findByName(cname);

		ResponseBase rb = new ResponseBase();
		rb.setMessage(company);

		return rb;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public ResponseBase cDelete(@RequestBody Map<String, String> params) {
		int id = RequestUtil.getIntParam(params, "id", 0);
		companyService.cDelete(id);

		return null;
	}

	// 关联用户 将公司关联到用户
	@RequestMapping(value = "/addUser")
	@ResponseBody
	public ResponseBase addUser(@RequestBody Map<String, Object> params) {
		int companyId = (int) params.get("companyId");

		List items = (List) params.get("item");
		for (Object item : items) {
			if (item == null) {
				continue;
			}
			String Item = (String) item;
			User user = userDao.getUserIdByEmail(Item);
			user.setCompanyId(companyId);
			userDao.updateUser(user);
		}
		System.out.println();
		ResponseBase rb = new ResponseBase();
		rb.setMessage("关联成功！");
		return rb;

	}

	@RequestMapping(value = "/uploadimg")
	@ResponseBody
	public ResponseBase uploadCompanyImg(
			@RequestParam MultipartFile[] file,
			// @RequestBody Map<String, String> params,
			@RequestHeader(value = "Authorization", required = false) String imgifo) {

		ResponseBase rb = new ResponseBase();
		String[] strArray = null;

		strArray = imgifo.split(",");
		String id = strArray[0];
		String f = strArray[1];
		int companyId = Integer.parseInt(id);
		int flag = Integer.parseInt(f);

		CompanyModel company = companyDao.getById(companyId);

		if (company == null) {
			rb.setState(1);
			rb.setMessage("图片插入错误，无法找到该公司");
			return rb;
		}

		if (file == null) {
			rb.setState(3);
			rb.setMessage("图片不可为空");
			return rb;
		}

		for (MultipartFile fileitem : file) {
			if (!fileitem.isEmpty()) {
				imgUploadService.saveCompanyImg(company, fileitem, flag);
			}

		}
		rb.setMessage(company);
		return rb;

	}

		
	
	

	
		
//		@RequestMapping(value = "/uploadimg")
//		@ResponseBody
//		public ResponseBase uploadCompanyImg(
//				@RequestParam MultipartFile[] file,
//			//	@RequestBody Map<String, String> params,
//				@RequestHeader (value="Authorization",required=false) String imgifo
//				) {
//		
//			ResponseBase rb = new ResponseBase();
//		  String[] strArray = null;   
//
//			  strArray = imgifo.split(",");
//			  String id=strArray[0];
//               String f=strArray[1];
//               int companyId=Integer.parseInt(id);
//               int flag = Integer.parseInt(f);
//               
//           System.out.println(flag);
//         
//		        CompanyModel company= companyDao.getById(companyId);
//
//			 if(company == null){
//					rb.setState(1);
//					rb.setMessage("图片插入错误，无法找到该公司");
//					return rb;
//				}
//
//				if(file == null){
//					rb.setState(3);
//					rb.setMessage("图片不可为空");
//					return rb;
//				}
//
//				 for (MultipartFile fileitem : file) {
//					 
//					 if(!fileitem.isEmpty()){
//			 imgUploadService.saveCompanyImg(company,fileitem,flag);
//				        } 
//					
//				}
//				return null; 
//			 
//=======
//		if (file == null) {
//			rb.setState(3);
//			rb.setMessage("图片不可为空");
//			return rb;
//>>>>>>> refs/heads/master
//		}
//
//<<<<<<< HEAD
//		
		//得到公司详情
		@RequestMapping(value = "/getcomTail")
		@ResponseBody
		public ResponseBase getcomTail(@RequestBody Map<String, String> params) {
			String strComid = params.get("comid");
			ResponseBase rb = new ResponseBase();

			int comid = strComid == null ? 0 : Integer
					.parseInt(strComid.trim());
			
			if(comid==0){
				rb.setState(1);
				return rb;
			}
	

	      Map<String,Object>    map	=companyService.getcomTail(comid);
			
          rb.setMessage(map);

	     
			return rb;
		}


	

	// 查询所有挑战赛已经公开的公司
	@RequestMapping(value = "/comListByType")
	@ResponseBody
	public ResponseBase comListByType() {
		ResponseBase rb = new ResponseBase();
		List<CompanyModel> companyList = companyService.findAll();
		for(int i=0;i<companyList.size();i++){
			CompanyModel companyModel=companyList.get(i);
  
			int comid = companyModel.getId();

			if (comid == 0) {
				rb.setState(1);
				return rb;
			}

			List<User> userList = userDao.getListByCompany(comid);
			if(userList.size()==0){
				
				companyList.remove(companyModel);
				i=i-1;
				continue;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<Quiz> quizList = new ArrayList<Quiz>();
			for (User user : userList) {
				// 做常量
				List<Quiz> quizListUse = userDao.getQuizByUid(user.getUid(),
						ExamConstant.QUIZ_TYPE_CHALLENGE);
				for (Quiz quiz : quizListUse) {
					quizList.add(quiz);
				}
			}

			if (quizList.size()==0) {
				companyList.remove(companyModel);
				i=i-1;
				continue;
			}
		
		String logoLocation= companyService.getImg(companyModel.getLogo());

		companyModel.setLogo(logoLocation);
		
		
		}
		
		rb.setMessage(companyList);
           return rb;
	}

	
	
	@RequestMapping(value = "/getList")
	@ResponseBody
	public ResponseBase getList() {
		ResponseBase rb = new ResponseBase();
		List<CompanyModel> companies = companyDao.getList(1, 0, 100);
		if (companies != null && companies.size() > 0) {
			List<CompanyJson> items = new ArrayList<CompanyJson>();
			for (CompanyModel company : companies) {
				CompanyJson item = new CompanyJson();
				item.setId(company.getId());
				item.setName(company.getName());
				item.setLogo(company.getLogo());
				item.setDescription(company.getName());
				items.add(item);
			}
			rb.setMessage(items);
		}
		return rb;
	}
}
