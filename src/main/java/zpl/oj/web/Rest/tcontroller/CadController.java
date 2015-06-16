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
import zpl.oj.model.responsejson.ResponseBase;


//客户端做挑战赛的控制器

@Controller
@RequestMapping("/cad")
public class CadController {
	@Autowired
    CandidateDao cadDao;
	
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
		}
		
		if(cadDao.findTuserByTel(cand.getTel())!=null){
			rb.setState(2);
			rb.setMessage("手机号码已被注册，请直接登陆");
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
		String oldpwd = map.get("oldpwd");
		String newpwd = map.get("newpwd");
		
		if(email==null||oldpwd==null||newpwd==null){
			rb.setState(1);
			rb.setMessage("输入项均不得为空，请重新输入");
			return rb;
		}
		
		Candidate  cad = cadDao.findUserByEmail(email);
		if(cad.getPwd().equals(oldpwd)==false){
			rb.setState(2);
			rb.setMessage("用户名，密码不匹配");
			return rb;
		}
		
		cadDao.updatePwdByEmail(newpwd, email);;
		rb.setState(0);
		rb.setMessage("修改成功");
		return rb;
		
	}


}
