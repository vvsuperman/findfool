package zpl.oj.web.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import zpl.oj.util.json.JsonImage;

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

		ResponseBase rb = new ResponseBase();

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
		if (companyDao.findByName(name) != null) {
			rb.setState(1);
			rb.setMessage("公司已存在");
			return rb;
		}

		companyDao.add(company);
		CompanyModel companymodel = companyService
				.findByName(company.getName());

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
		CompanyModel company = companyDao.getById(id);
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
		for (CompanyModel company : companyList) {
			String logoLocation = companyService.getImg(company.getLogo());
			company.setLogo(logoLocation);
		}
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
		
		if(id==null){
			rb.setState(4);
			rb.setMessage("公司id为空，不能插入图片！");
			return rb;
			
			
		}
		
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
		rb.setState(0);
		rb.setMessage(company);
		return rb;

	}
	
	@RequestMapping(value = "/uploadCompanyImageTail")
	@ResponseBody
	public JsonImage uploadCompanyImageTail(
		//	@RequestParam MultipartFile[] file,
			@RequestParam("file") MultipartFile[] file
		
			
			) {

		ResponseBase rb = new ResponseBase();
		String  urltrue="";
		for (MultipartFile fileitem : file) {
			if (!fileitem.isEmpty()) {
		  urltrue	  =	imgUploadService.uploadCompanyImageTail(fileitem);
			}

		}

 JsonImage  jsonImage=new JsonImage();
       jsonImage.setSuccess("true");
       jsonImage.setFile_path(urltrue);
       jsonImage.setMsg("你懂得");
		   
		return jsonImage;

	}

	// 得到公司详情
	@RequestMapping(value = "/getcomTail")
	@ResponseBody
	public ResponseBase getcomTail(@RequestBody Map<String, String> params) {
		String strComid = params.get("comid");
		ResponseBase rb = new ResponseBase();

		int comid = strComid == null ? 0 : Integer.parseInt(strComid.trim());

		if (comid == 0) {
			rb.setState(1);
			return rb;
		}
		Map<String, Object> map = companyService.getcomTail(comid);

		rb.setMessage(map);

		return rb;
	}

	// 查询所有挑战赛已经公开的公司
	@RequestMapping(value = "/comListByType")
	@ResponseBody
	public ResponseBase comListByType() {
		ResponseBase rb = new ResponseBase();
		List<CompanyModel> companyList = companyService.findAll();
		for (int i = 0; i < companyList.size(); i++) {
			CompanyModel companyModel = companyList.get(i);

			int comid = companyModel.getId();

			if (comid == 0) {
				rb.setState(1);
				return rb;
			}

			List<User> userList = userDao.getListByCompany(comid);
			if (userList.size() == 0) {

				companyList.remove(companyModel);
				i = i - 1;
				continue;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			List<Quiz> quizList = new ArrayList<Quiz>();
			//hr存在好几个
			for (User user : userList) {
				// 做常量
				List<Quiz> quizList1 = userDao.getQuizByUid(user.getUid(),
						ExamConstant.QUIZ_TYPE_CHALLENGE);
				quizList.addAll(quizList1);
			}
			if (quizList.size() == 0) {
				companyList.remove(companyModel);
				i = i - 1;
				continue;
			}
			// 判断是否测试已开始
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			Date date = new Date();
			String str = df.format(date);

			int[] companyflag = { -1, -1, -1 };
			
			/*
			 * companyflag作为数组标记位，进行循环，对所有挑战赛的标记位进行赋值
			 * companyflag[0]表示已经结束的比赛，companyflag[1]表示已经开始的挑战赛
			 * companyflag[2]表示还未开始的挑战赛，
			 * 相应字段分别与常量相对应！
			 * 
			 */
			for (Quiz quiz : quizList) {
				if (quiz.getStartTime().equals("")
						|| quiz.getEndTime().equals("")) {
					companyflag[1] = ExamConstant.COMPANY_START;
					break;
				}
			
				int number1 = str.compareTo(quiz.getStartTime());
				int number2 = str.compareTo(quiz.getEndTime());
				if ((number1 > 0) && (number2 < 0)) {
					companyflag[1] = ExamConstant.COMPANY_START;
					break;
				}
				if (number1 < 0) {
					companyflag[2] = ExamConstant.COMPANY_NERVER;
				}
				if (number2 > 0) {
					companyflag[0] = ExamConstant.COMPANY_OVER;
				}
			}
			
			//根据优先级进行赋值，优先级分别是：已经开始，即将开始，已经结束；
			if (companyflag[1] == ExamConstant.COMPANY_START) {
				companyModel.setInvitestate(ExamConstant.COMPANY_START);
			} else if (companyflag[2] == ExamConstant.COMPANY_NERVER) {
				companyModel.setInvitestate(ExamConstant.COMPANY_NERVER);

			} else {
				companyModel.setInvitestate(ExamConstant.COMPANY_OVER);

			}

			String logoLocation = companyService.getImg(companyModel.getLogo());

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
