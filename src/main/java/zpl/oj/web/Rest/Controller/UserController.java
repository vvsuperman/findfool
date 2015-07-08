package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Candidate;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.requestjson.RequestUserLogin;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.sms.SMS;

import com.mingdao.sdk.Config;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SMS sms;

	@Autowired
	private SecurityService securityService;
	@Autowired
	private VerifyQuestionService verifyQuestionService;

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
		sms.send(mobile, ls, ExamConstant.SMS_TEMPID_REMIND);
		rb.setMessage(s);
		return rb;
	}

	@RequestMapping(value = "/oauthorlogin")
	@ResponseBody
	public ResponseBase oauthorLogin(@RequestBody Map map) {
		ResponseBase rb = new ResponseBase();
		String source = (String) map.get("source");

		if (ExamConstant.SOURCE_MD.equals(source)) {
			String code = (String) map.get("code");
			String token = Config.getAccessTokenByCode(code);
			// 访问api出错，直接返回
			if (token == null) {
				rb.setState(3);
				return rb;
			}
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> tokenMap = new HashMap<String, Object>();
			try {
				tokenMap = mapper.readValue(token, Map.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			token = (String) tokenMap.get("access_token");

			String user = Config.getUserInfo(token);
			if (user == null) {
				rb.setState(3);
				return rb;
			}
			Map<String, Object> userMap = new HashMap<String, Object>();
			try {
				userMap = mapper.readValue(user, Map.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			userMap = (Map) userMap.get("user");
			User u = userService.getUserByEmail((String) userMap.get("email"));
			// 用户已存在，登陆
			Map rtMap = new HashMap<String, Object>();
			if (u != null) {
				rb.setState(1);
				rtMap.put("user", u);
				rtMap.put("token", token);
				rb.setMessage(rtMap);
			} else {
				u = new User();
				u.setEmail((String) userMap.get("email"));
				u.setCompany((String) userMap.get("company"));
				u.setMdUid((String) userMap.get("id"));
				rtMap.put("user", u);
				rtMap.put("token", token);
				rb.setState(2);
				rb.setMessage(rtMap);
			}
		}
		return rb;
	}

	@RequestMapping(value = "/confirm")
	@ResponseBody
	public ResponseBase userLogin(@RequestBody RequestUserLogin request) {
		ResponseBase rb = new ResponseBase();
		User u = new User();

		ResponseMessage msg = new ResponseMessage();
		u = userService.getUserByEmail(request.getEmail());
		if (u == null) {
			msg.setMsg("usernotexist");
			rb.setState(0);
			rb.setMessage(msg);
		} else if (!u.getPwd().equals(request.getPwd())) {
			msg.setMsg("错误的用户名或密码");
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setMessage(msg);
		} else {
			u = userService.userLogin(u.getUid());
			rb.setMessage(u);
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
		}
		return rb;
	}

	@RequestMapping(value = "/add/hr")
	@ResponseBody
	public ResponseBase addHrUser(@RequestBody RequestUserLogin request) {
		ResponseBase rb = new ResponseBase();
		User u = new User();
		u.setFname(request.getName());
		u.setEmail(request.getEmail());
		u.setPwd(request.getPwd());
		u.setCompany(request.getCompany());
		u.setTel(request.getTel()); // 用户联系方式
		u.setMdUid(request.getMdUid()); // 明道id
		// 测试阶段先免费
		u.setInvited_left(100);
		// 默认等级
		u.setPrivilege(2);
		ResponseMessage msg = new ResponseMessage();
		boolean res = userService.addUser(u);

		if (res == false) {
			msg.setMsg("注册失败，邮箱已被注册");
			msg.setHandler_url("/error");
			rb.setMessage(msg);
			rb.setState(0);
		} else {
			u = userService.getUserByEmail(u.getEmail());
			userService.userLogin(u.getUid());
			msg.setMsg(new String() + u.getUid());
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(u);

		}

		return rb;
	}

	@RequestMapping(value = "/add/admin")
	@ResponseBody
	public ResponseBase addAdminUser(@RequestBody RequestUserLogin request) {
		ResponseBase rb = new ResponseBase();
		User u = new User();
		u.setFname(request.getName());
		u.setEmail(request.getEmail());
		u.setPwd(request.getPwd());
		// 默认等级
		u.setPrivilege(3);
		ResponseMessage msg = new ResponseMessage();
		boolean res = userService.addUser(u);

		if (res == false) {
			msg.setMsg("注册失败，邮箱已被注册");
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setMessage(msg);
		} else {
			u = userService.getUserByEmail(u.getEmail());
			userService.userLogin(u.getUid());
			msg.setMsg(new String() + u.getUid());
			rb.setState(1);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(u);
		}

		return rb;
	}

	@RequestMapping(value = "/getVerifyQtn")
	@ResponseBody
	public ResponseBase getVerifyQtn() {
		ResponseBase rb = new ResponseBase();
		int qtnCount = verifyQuestionService.getVerifyQuestionCount();
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		int index = rand.nextInt(qtnCount);
		// 获取第index个问题
		VerifyQuestion vq = verifyQuestionService.getVerifyQuestion(index);
		rb.setState(200);
		rb.setMessage(vq.getQuestion());
		return rb;
	}

	// 查询用户的基本信息
	@RequestMapping(value = "/setting/query")
	@ResponseBody
	public ResponseBase queryUserInfo(@RequestBody RequestUser request) {
		ResponseBase rb = new ResponseBase();
		User u = new User();

		u = userService.getUserById(request.getUid());
		if (u == null) {
			ResponseMessage msg = new ResponseMessage();
			msg.setMsg("failed login!  not found user:" + request.getUid());
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setToken(securityService.computeToken(u));
			rb.setMessage(msg);
		} else {
			User rtUser = new User();
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

	// 修改用户的基本信息
	@RequestMapping(value = "/setting/set")
	@ResponseBody
	public ResponseBase setUserInfo(@RequestBody User user) {
		ResponseBase rb = new ResponseBase();
		User u = new User();

		u = userService.getUserById(user.getUid());
		ResponseMessage msg = new ResponseMessage();
		if (u == null) {
			msg.setMsg("用户名不存在");
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setMessage(msg);
			return rb;
		} else {
			u.setCompany(user.getCompany());
			u.setTel(user.getTel());
			userService.updateUser(u);
			rb.setState(1);
			rb.setMessage(u);
			rb.setToken(securityService.computeToken(u));
			return rb;
		}

	}

	// 修改用户的基本信息
	@RequestMapping(value = "/setting/setpwd")
	@ResponseBody
	public ResponseBase setPwd(@RequestBody Map map) {
		String oldPwd = (String) map.get("oldPwd");
		String newPwd = (String) map.get("newPwd");
		int uid = (Integer) map.get("uid");
		User u = userService.getUserById(uid);
		ResponseBase rb = new ResponseBase();

		if (u.getPwd().equals(oldPwd) == false) {
			rb.setState(0);
			return rb;
		} else {
			u.setPwd(newPwd);
			userService.updateUser(u);
			rb.setState(1);
			return rb;
		}
	}

	// 重置密码申请,发送用户邮件
	@RequestMapping(value = "/setting/resetpwdapply")
	@ResponseBody
	public ResponseBase reSetPwdApply(@RequestBody Map map) {
		ResponseBase rb = new ResponseBase();
		String email = (String) map.get("email");
		String content = (String) map.get("content");
		String answer = (String) map.get("answer");

		VerifyQuestion vQuestion = verifyQuestionService
				.getVQuestByContent(content);
		if (vQuestion == null) {
			rb.setState(1);
			rb.setMessage("验证问题不存在");
			return rb;
		}
		if (answer.equals(vQuestion.getAnswer()) == false) {
			rb.setState(1);
			rb.setMessage("验证码错误");
			return rb;
		}

		if (email == null) {
			rb.setState(1);
			rb.setMessage("email为空");
			return rb;
		}
		;
		User user = userDao.getUserIdByEmail(email);
		if (user == null) {
			rb.setState(1);
			rb.setMessage("email错误，用户不存在");
			return rb;
		}
		userService.resetPwdApply(email, user);
		rb.setState(0);
		return rb;
	}

	// 验证url是否合法
	@RequestMapping(value = "/setting/checkurl")
	@ResponseBody
	public ResponseBase checkUrl(@RequestBody Map map) {
		ResponseBase rb = new ResponseBase();
		String url = (String) map.get("url");
		if (url == null) {
			rb.setState(1);
			rb.setMessage("param为空");
		}
		;
		User user = userDao.getUserByUrl(url);
		if (user == null) {
			rb.setState(1);
			rb.setMessage("错误的url地址");
			return rb;
		}
		rb.setState(0);
		rb.setMessage(user.getEmail());
		return rb;
	}

	// 重置密码
	@RequestMapping(value = "/setting/resetpwd")
	@ResponseBody
	public ResponseBase reSetPwd(@RequestBody Map map) {
		ResponseBase rb = new ResponseBase();
		String email = (String) map.get("email");
		String pwd = (String) map.get("pwd");
		String confirmpwd = (String) map.get("confirmpwd");

		if (email == null || pwd == null || confirmpwd == null) {
			rb.setState(1);
			rb.setMessage("输入不得为空");
			return rb;
		}

		if (pwd.equals(confirmpwd) == false) {
			rb.setState(2);
			rb.setMessage("两次密码不相同");
			return rb;
		}

		User user = userDao.getUserIdByEmail(email);
		if (user == null) {
			rb.setState(2);
			rb.setMessage("用户不存在");
			return rb;
		}

		if (user.getPwd().equals(pwd) == false) {
			rb.setState(3);
			rb.setMessage("原用户密码错误");
			return rb;
		}

		userDao.updatePwd(confirmpwd, email);
		rb.setState(0);
		return rb;
	}

	// 微信js sdk配置
	@RequestMapping(value = "/wxjsk/config")
	@ResponseBody
	public ResponseBase wxSDKConfig(@RequestBody Map map) {
		String url = (String) map.get("url");
		String ticket = JsSdk.getJsApiTicket();
		Map<String, String> data = JsSdk.sign(ticket, url);
		data.put("debug", "true");
		data.put(
				"jsApiList",
				"['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareWeibo']");
		ResponseBase rb = new ResponseBase();
		// Map data = new HashMap<String, String>();
		rb.setMessage(data);

		return rb;
	}
	
	
}
