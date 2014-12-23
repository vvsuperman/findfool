package zpl.oj.web.Rest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestChangeUserInfo;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.requestjson.RequestUserLogin;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseUserInfo;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;

@Controller
@RequestMapping("/user") 
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	

	
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
			msg.setMsg("failed login!  user:"+request.getEmail()+" error user or password");
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
		//默认等级
		u.setPrivilege(2);
		ResponseMessage msg = new ResponseMessage();
		boolean res = userService.addUser(u);
		
		if(res == false){
			msg.setMsg("failed register! because this email has been registed!");
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
			MailSenderInfo mailSenderInfo = new MailSenderInfo();
			mailSenderInfo.setFromAddress("yigongquan4mail@sina.com");
			mailSenderInfo.setToAddress(u.getEmail());
			mailSenderInfo.setSubject("欢迎新用户");
			mailSenderInfo.setContent("欢迎你们彩笔们~");
			SimpleMailSender.sendHtmlMail(mailSenderInfo);
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
			msg.setMsg("failed register! because this email has been registed!");
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
			ResponseUserInfo uinfo = new ResponseUserInfo();
			uinfo.setEmail(u.getEmail());
			uinfo.setCompany(u.getCompany());
			uinfo.setName(u.getFname());
			uinfo.setTel(u.getTel());
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(uinfo);
		}		
		return rb;
	}
	
	//查询用户的基本信息
	@RequestMapping(value="/setting/set")
	@ResponseBody
	public ResponseBase setUserInfo(@RequestBody RequestChangeUserInfo request){
		ResponseBase rb = new ResponseBase();
		User u = new User();
		
		
		u = userService.getUserById(request.getUser().getUid());
		ResponseMessage msg = new ResponseMessage();
		if(u == null ){
			msg.setMsg("failed login!  not found user:"+request.getUser().getUid());
			msg.setHandler_url("/error");
			rb.setState(0);		

		}else{
			u.setEmail(request.getEmail());
			u.setCompany(request.getCompany());
			u.setFname(request.getName());
			u.setTel(request.getTel());
			if(request.getPwd().equals(request.getNewPWD())){
				u.setPwd(request.getPwd());
			}
			rb.setState(1);
			userService.updateUser(u);
			msg.setMsg("update ok");
			msg.setHandler_url("#");
		}
		rb.setToken(securityService.computeToken(u));
		rb.setMessage(msg);
		return rb;
	}
}
