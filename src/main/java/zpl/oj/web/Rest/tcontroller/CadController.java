package zpl.oj.web.Rest.tcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.CandidateDao;
import zpl.oj.model.common.Candidate;
import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.imp.CadService;


//客户端做挑战赛的控制器

@Controller
@RequestMapping("/cad")
public class CadController {
	@Autowired
    CandidateDao cadDao;
	
	@Autowired
	private VerifyQuestionService verifyQuestionService;
	
	@Autowired
	private CadService cadService;

	
	//注册步骤1
	@RequestMapping(value = "/preregister", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase preRegister(@RequestBody Candidate cand) {
		ResponseBase rb = new ResponseBase();
		
		if(cand.getEmail()==null||cand.getTel()==null||cand.getPwd()==null){
			rb.setState(3);
			rb.setMessage("输入项不得为空，请重新输入");
		}
		
		
		if(cadDao.countUserByEmail(cand.getEmail())>0){
			rb.setState(1);
			rb.setMessage("email已注册，请直接登陆");
			return rb;
		}
		
		if(cadDao.findTuserByTel(cand.getTel())!=null){
			rb.setState(2);
			rb.setMessage("手机号码已被注册，");
			return rb;
		}
		
		cadDao.insertUser(cand);
		rb.setState(0);
		rb.setMessage(cand.getEmail());
		return rb;
		
	}
	
	//注册步骤2
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase register(@RequestBody Candidate cand) {
		ResponseBase rb = new ResponseBase();
		
		if(cand.getEmail()==null||cand.getUsername()==null||cand.getSchool()==null
				|| cand.getDiscipline() == null || cand.getDegree() == null|| cand.getGratime() == null){
			rb.setState(3);
			rb.setMessage("输入项不得为空，请重新输入");
			return rb;
		}
		
		Candidate cad = cadDao.findUserByEmail(cand.getEmail());
		cad.setUsername(cand.getUsername());
		cad.setSchool(cand.getSchool());
		cad.setDiscipline(cand.getDiscipline());
		cad.setDegree(cand.getDegree());
		cad.setGratime(cand.getGratime());
		
		cadDao.updateUserByEmail(cad);
		rb.setState(0);
		rb.setMessage(cand.getEmail());
		return rb;
		
	}
	
	//注册步骤3
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase login(@RequestBody Candidate cand) {
		ResponseBase rb = new ResponseBase();
		
		if(cand.getEmail()==null||cand.getPwd()==null){
			rb.setState(1);
			rb.setMessage("输入项不得为空，请重新输入");
			return rb;
		}
		
		Candidate cad = cadDao.findUserByEmail(cand.getEmail());
		if(cad.getPwd().equals(cand.getPwd())==false){
			rb.setState(2);
			rb.setMessage("用户名密码不匹配");
			return rb;
		}
		Map rtMap = new HashMap<String, Object>();
		rtMap.put("email",cand.getEmail());
		rb.setState(0);
		rb.setMessage(rtMap);
		return rb;
		
	}
	
	
	//获取用户信息
	@RequestMapping(value = "/getcadinfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase getCadInfo(@RequestBody Candidate cand) {
		ResponseBase rb = new ResponseBase();
		
		if(cand.getEmail()==null){
			rb.setState(1);
			rb.setMessage("email不得为空");
			return rb;
		}
		Candidate cad = cadDao.findUserByEmail(cand.getEmail());
		rb.setState(0);
		rb.setMessage(cad);
		return rb;
		
	}
	
	
	//修改基本信息
	@RequestMapping(value = "/modifycadinfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase modifyCadInfo(@RequestBody Candidate cand) {
		ResponseBase rb = new ResponseBase();
		
		if(cand.getEmail()==null||cand.getUsername()==null||cand.getSchool()==null
				|| cand.getDiscipline() == null || cand.getDegree() == null|| cand.getGratime() == null){
			rb.setState(1);
			rb.setMessage("输入项均不得为空，请重新输入");
			return rb;
		}
		cadDao.updateCadByEmail(cand);
		rb.setState(0);
		rb.setMessage("修改成功");
		return rb;
		
	}
		
	//修改密码
	@RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase modifyPwd(@RequestBody Map<String,String> map) {
		ResponseBase rb = new ResponseBase();
		
		String email = map.get("email");
		String pwd = map.get("pwd");
		String confirmpwd = map.get("confirmpwd");
		
		if(email==null||pwd==null||confirmpwd==null){
			rb.setState(1);
			rb.setMessage("输入项均不得为空，请重新输入");
			return rb;
		}
		
		Candidate  cad = cadDao.findUserByEmail(email);
		if(cad.getPwd().equals(pwd)==false){
			rb.setState(2);
			rb.setMessage("用户名，密码不匹配");
			return rb;
		}
		
		cadDao.updatePwdByEmail(confirmpwd, email);;
		rb.setState(0);
		rb.setMessage("修改成功");
		return rb;
		
	}
	
	
	// 重置密码申请,发送用户邮件
		@RequestMapping(value = "/setting/resetpwdapply")
		@ResponseBody
		public ResponseBase reSetPwdApply(@RequestBody Map map) {
			ResponseBase rb = new ResponseBase();
			String email = (String) map.get("email");
			String content = (String)map.get("content");
			String answer = (String)map.get("answer");
			
			VerifyQuestion vQuestion = verifyQuestionService.getVQuestByContent(content);
			if(vQuestion == null){
				rb.setState(1);
				rb.setMessage("验证问题不存在");
				return rb;
			}
			if(answer.equals(vQuestion.getAnswer()) == false){
				rb.setState(1);
				rb.setMessage("验证码错误");
				return rb;
			}
			
			
			if (email == null) {
				rb.setState(1);
				rb.setMessage("email为空");
				return rb;
			};
			Candidate cad = cadDao.findUserByEmail(email);
			if (cad == null) {
				rb.setState(1);
				rb.setMessage("email错误，用户不存在");
				return rb;
			}
		    cadService.resetPwdApply(email, cad);
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
			Candidate cad = cadDao.findTuserByUrl(url);
			if (cad == null) {
				rb.setState(1);
				rb.setMessage("错误的url地址");
				return rb;
			}
			rb.setState(0);
			rb.setMessage(cad.getEmail());
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

			if(email == null||pwd==null||confirmpwd==null){
				rb.setState(1);
				rb.setMessage("输入不得为空");
				return rb;
			}
			
			if(pwd.equals(confirmpwd)==false){
				rb.setState(2);
				rb.setMessage("两次密码不相同");
				return rb;
			}
			
			Candidate cad = cadDao.findUserByEmail(email);
			if(cad == null){
				rb.setState(2);
				rb.setMessage("用户不存在");
				return rb;
			}
			
			if (cad.getPwd().equals(pwd) == false) {
				rb.setState(3);
				rb.setMessage("原用户密码错误");
				return rb;
			}
			
			cadDao.updatePwdByEmail(confirmpwd, email);
			rb.setState(0);
			return rb;
		}


}
