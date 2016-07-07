package zpl.oj.web.Rest.tcontroller;

//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.TestuserDao;
//import zpl.oj.model.common.Invite;
//import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.requestjson.RequestUserLogin;
//import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.imp.CadService;
import zpl.oj.service.imp.TuserService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.web.Rest.Controller.TestingController.JsonLable;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.sms.SMS;
import zpl.oj.util.sms.SmsApi_Send;

import com.google.gson.Gson;


//客户端做挑战赛的控制器

@Controller
@RequestMapping("/tuser")
public class TuserController {
	private static final String String = null;

	@Autowired
    TestuserDao tuserDao;
	
	@Autowired
	private SMS sms;
	
	@Autowired
	TuserService tuserService;
	
	@Autowired
	SecurityService   securityService;
	
	
	
	// 获取手机验证码
		@RequestMapping(value = "/getvertifycode", method = RequestMethod.POST)
		@ResponseBody
		public ResponseBase getVertifyCode(@RequestBody Map map1) {
			ResponseBase rb = new ResponseBase();
			String mobile = (String) map1.get("mobile");
			// 发送短信
			int max = 9999;
			int min = 1000;
			Random random = new Random();
			int s = random.nextInt(max) % (max - min + 1) + min;
			List<String> ls = new ArrayList<String>();
			ls.add(s + "");
			//sms.send(mobile, ls, ExamConstant.SMS_TEMPID_REMIND);
			SmsApi_Send.doSend(mobile, ExamConstant.SendCloud_SMS_VALID, ls);
			rb.setMessage(s);
			return rb;
		}

		
		@RequestMapping(value = "/confirm")
		@ResponseBody
		public ResponseBase userLogin(@RequestBody RequestUserLogin request) {
			ResponseBase rb = new ResponseBase();
			ResponseMessage msg = new ResponseMessage();
	
			Testuser u = tuserService.findTuserByEmail(request.getEmail());
			
			if (u== null) {
				msg.setMsg("用户不存在");
				rb.setState(1);
				rb.setMessage(msg);
				return rb;
			}
			else if((u.getPwd()!=null)&&(!u.getPwd().equals(request.getPwd()))) {
			
				msg.setMsg("错误的用户名或密码");
				msg.setHandler_url("/error");
				rb.setState(2);
				rb.setMessage(msg);
				return rb;
			}
			else {
				u = tuserService.userLogin(u.getTuid());
				
				rb.setMessage(u);
				rb.setState(0);
				rb.setToken(securityService.computeToken(u));
			
			
			return rb;
			}
		}
		

		
	
	
	//注册步骤
	@RequestMapping(value ="/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase register(@RequestBody Testuser tuser) {
		ResponseBase rb = new ResponseBase();
		Testuser tuser1=tuser;
		tuser1.setPrivilege(ExamConstant.ROLE_TEST);
		if(tuser1.getEmail()==null||tuser1.getTel()==null||tuser1.getPwd()==null){
			rb.setState(3);
			rb.setMessage("输入项不得为空，请重新输入");
			return rb;
		}
		//如果邮箱存在，就要返回这个对象，进行更新用户信息的操作
		Testuser tuser2=tuserDao.findTuserByEmail(tuser.getEmail());
		
			//邮箱不存在，进行插入操作
			if(tuser2==null){
				
				tuserDao.insertTestuser(tuser1);
				rb.setState(0);
				rb.setMessage(tuser1.getEmail());
				return rb;
			
			}
			
		
			if(((tuser2.getEmail())!=null)&&((tuser2.getPwd())!=null)){
				rb.setState(1);
				rb.setMessage("email已注册，请直接登陆");
				System.out.println("email已注册，请直接登陆");
				return rb;
			}
			else if(tuser2.getEmail()!=null){
				
				tuserService.updateTestuser(tuser1);
			
				rb.setState(0);
				rb.setMessage(tuser2.getEmail());
				return rb;
			}
			return rb;
	
		
	}



}
