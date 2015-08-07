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
import com.qiniu.util.Auth;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.ImgUploadDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.CompanyService;
import zpl.oj.service.imp.PersonalService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;

@Controller
@RequestMapping("/personal")
public class PersonalController {
	
	@Autowired
	private TestuserDao testuserDao;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private PersonalService personalService;
	
	@RequestMapping(value = "/findAllList")
	@ResponseBody
	public ResponseBase findAllList(@RequestBody Map<String, String> params) {
		String email = params.get("email");
	     
	Map<String,Object>	 map  = personalService.findAllList(email);
	ResponseBase rb = new ResponseBase();
	rb.setMessage(map);
		return rb;

	}
	
//根据用户id修改用户信息
	@RequestMapping(value ="/modify")
	@ResponseBody
	public ResponseBase modify(@RequestBody Testuser testuser) {
		ResponseBase rb = new ResponseBase();
   
		if((testuser.getEmail()==null)&&(testuser.getUsername()==null)){
			rb.setState(1);
			rb.setMessage("邮箱不能为空或用户名不能为空");	
		}
		if(testuser.getTel()==null){
			rb.setState(2);
			rb.setMessage("手机号码不能为空");			
		}
	    testuserDao.updateTestuserById(testuser);
		System.out.println();
		return rb;

	}
	

	
	
}
