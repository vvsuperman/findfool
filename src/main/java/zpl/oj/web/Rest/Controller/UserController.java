package zpl.oj.web.Rest.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingdao.sdk.Config;

import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestChangeUserInfo;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.requestjson.RequestUserLogin;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseUserInfo;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;

@Controller
@RequestMapping("/user") 
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private VerifyQuestionService verifyQuestionService;

	
	@RequestMapping(value="/oauthorlogin")
	@ResponseBody
	public ResponseBase oauthorLogin(@RequestBody Map map){
		ResponseBase rb = new ResponseBase();
		String source = (String)map.get("source");
		
		
		if(ExamConstant.SOURCE_MD.equals(source)){
			String code =(String)map.get("code");
			String token = Config.getAccessTokenByCode(code);
			//访问api出错，直接返回
			if(token == null){
				rb.setState(3);
				return rb;
			}
			ObjectMapper mapper = new ObjectMapper(); 
			Map<String,Object> tokenMap = new HashMap<String, Object>();
			try {
				tokenMap= mapper.readValue(token, Map.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			token = (String)tokenMap.get("access_token");
			
			String user = Config.getUserInfo(token);
			if(user == null){
				rb.setState(3);
				return rb;
			}
			Map<String,Object> userMap = new HashMap<String, Object>();
			try {
				userMap= mapper.readValue(user, Map.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			userMap = (Map)userMap.get("user");
			User u = userService.getUserByEmail((String)userMap.get("email"));
			//用户已存在，登陆
			Map rtMap = new HashMap<String, Object>();
			if(u != null){
				rb.setState(1);
				rtMap.put("user", u);
				rtMap.put("token", token);
				rb.setMessage(rtMap);
			}else{
				u = new User();
				u.setEmail((String)userMap.get("email"));
				u.setCompany((String)userMap.get("company"));
				rtMap.put("user", u);
				rtMap.put("token", token);
				rb.setState(2);
				rb.setMessage(rtMap);
			}
		}
		return rb;
	}
	
	
	@RequestMapping(value="/confirm")
	@ResponseBody
	public ResponseBase userLogin(@RequestBody RequestUserLogin request){
		ResponseBase rb = new ResponseBase();
		User u = new User();

		ResponseMessage msg = new ResponseMessage();
		u = userService.getUserByEmail(request.getEmail());
		if(u== null){		
			msg.setMsg("usernotexist");
			rb.setState(0);
			rb.setMessage(msg);
		}
		else if(!u.getPwd().equals(request.getPwd())){
			msg.setMsg("错误的用户名或密码");
			msg.setHandler_url("/error");
			rb.setState(0);		
			rb.setMessage(msg);
		}else{
			u = userService.userLogin(u.getUid());
			rb.setMessage(u);
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
		}
		return rb;
	}
	
	
	@RequestMapping(value="/add/hr")
	@ResponseBody
	public ResponseBase addHrUser(@RequestBody RequestUserLogin request){
		ResponseBase rb = new ResponseBase();
		User u = new User();
		u.setFname(request.getName());
		u.setEmail(request.getEmail());
		u.setPwd(request.getPwd());
		u.setCompany(request.getCompany());
		//测试阶段先免费
		u.setInvited_left(100);
		//默认等级
		u.setPrivilege(2);
		ResponseMessage msg = new ResponseMessage();
		boolean res = userService.addUser(u);
		
		if(res == false){
			msg.setMsg("注册失败，邮箱已被注册");
			msg.setHandler_url("/error");
			rb.setMessage(msg);
			rb.setState(0);			
		}else{
			u = userService.getUserByEmail(u.getEmail());
			userService.userLogin(u.getUid());
			msg.setMsg(new String()+u.getUid());
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(u);
			//发送邮件
//			MailSenderInfo mailSenderInfo = new MailSenderInfo();
//			mailSenderInfo.setFromAddress("yigongquan4mail@sina.com");
//			mailSenderInfo.setToAddress(u.getEmail());
//			mailSenderInfo.setSubject("欢迎新用户");
//			mailSenderInfo.setContent("欢迎你们彩笔们~");
//			SimpleMailSender.sendHtmlMail(mailSenderInfo);
		}
		
		
		return rb;
	}
	
	@RequestMapping(value="/add/admin")
	@ResponseBody
	public ResponseBase addAdminUser(@RequestBody RequestUserLogin request){
		ResponseBase rb = new ResponseBase();
		User u = new User();
		u.setFname(request.getName());
		u.setEmail(request.getEmail());
		u.setPwd(request.getPwd());
		//默认等级
		u.setPrivilege(3);
		ResponseMessage msg = new ResponseMessage();
		boolean res = userService.addUser(u);
		
		if(res == false){
			msg.setMsg("注册失败，邮箱已被注册");
			msg.setHandler_url("/error");
			rb.setState(0);			
			rb.setMessage(msg);
		}else{
			u = userService.getUserByEmail(u.getEmail());
			userService.userLogin(u.getUid());
			msg.setMsg(new String()+u.getUid());
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(u);
		}
		
		
		return rb;
	}
	
	@RequestMapping(value="/getVerifyQtn")
	@ResponseBody
	public ResponseBase getVerifyQtn(){
		ResponseBase rb = new ResponseBase();
		int qtnCount=verifyQuestionService.getVerifyQuestionCount();
		Random rand=new Random();
		rand.setSeed(System.currentTimeMillis());
		int index=rand.nextInt(qtnCount);
		//获取第index个问题
		VerifyQuestion vq=verifyQuestionService.getVerifyQuestion(index);
		rb.setState(200);
		rb.setMessage(vq);
		return rb;
	}
	
	//查询用户的基本信息
	@RequestMapping(value="/setting/query")
	@ResponseBody
	public ResponseBase queryUserInfo(@RequestBody RequestUser request){
		ResponseBase rb = new ResponseBase();
		User u = new User();
		
		u = userService.getUserById(request.getUid());
		if(u == null ){
			ResponseMessage msg = new ResponseMessage();
			msg.setMsg("failed login!  not found user:"+request.getUid());
			msg.setHandler_url("/error");
			rb.setState(0);		
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(msg);
		}else{
			User rtUser  = new User();
			rtUser.setEmail(u.getEmail());
			rtUser.setCompany(u.getCompany());
			rtUser.setFname(u.getFname());
			rtUser.setTel(u.getTel());
			rtUser.setInvited_left(u.getInvited_left());
			
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(rtUser);
		}		
		return rb;
	}
	
	//修改用户的基本信息
	@RequestMapping(value="/setting/set")
	@ResponseBody
	public ResponseBase setUserInfo(@RequestBody User user){
		ResponseBase rb = new ResponseBase();
		User u = new User();
		
		u = userService.getUserById(user.getUid());
		ResponseMessage msg = new ResponseMessage();
		if(u == null ){
			msg.setMsg("用户名不存在");
			msg.setHandler_url("/error");
			rb.setState(0);	
			rb.setMessage(msg);
			return rb;
		}else{
			u.setCompany(user.getCompany());
			u.setTel(user.getTel());
			userService.updateUser(u);
			rb.setState(1);
			rb.setMessage(u);
			rb.setToken(securityService.computeToken(u));
			return rb;
		}
		
	}
	
	//修改用户的基本信息
		@RequestMapping(value="/setting/setpwd")
		@ResponseBody
		public ResponseBase setPwd(@RequestBody Map map){
			String oldPwd = (String)map.get("oldPwd");
			String newPwd = (String)map.get("newPwd");
			int uid = (Integer)map.get("uid");
			User u = userService.getUserById(uid);
			ResponseBase rb = new ResponseBase();
			
			if(u.getPwd().equals(oldPwd)==false){
				rb.setState(0);
				return rb;
			}else{
				u.setPwd(newPwd);
			    userService.updateUser(u);
			    rb.setState(1);
			    return rb;
			}
			
			
		}
	
	
	//修改用户的密码
}
