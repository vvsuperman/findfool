package zpl.oj.web.Rest.Controller;

import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestDetail;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.model.requestjson.RequestTestMeta;
import zpl.oj.model.requestjson.RequestTestSubmit;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseQuizDetail;
import zpl.oj.model.responsejson.ResponseQuizs;
import zpl.oj.service.InviteService;
import zpl.oj.service.ProblemService;
import zpl.oj.service.QuizService;
import zpl.oj.service.imp.TuserService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;

@Controller
@RequestMapping("/testing")
public class TestingController {

	@Autowired
	private QuizService quizService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviteService inviteService;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private InviteDao inviteDao;
	
	@Autowired
	private DESService desService;
	
	@Autowired
	private TuserService testusrService;
	
	
	//根据quiz查出所有的problemid，按照题目类型排序并返回
	@RequestMapping(value = "/queryTestList")
	@ResponseBody
	public ResponseBase queryTestList(@RequestBody Quiz quiz) {
		ResponseBase rb = new ResponseBase();

		ResponseQuizDetail rqd = quizService.getQuizDetail(quiz.getQuizid());
		if (null == rqd) {
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("no such quiz");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
		} else {
			rb.setMessage(rqd);
			rb.setState(1);
		}

		return rb;
	}
	
	
	public boolean validateUser(Map params){
		int tuid = (int) params.get("tuid");
		int testid = (int) params.get("testid");
		
		if(inviteDao.getInvitesByIds(testid, tuid)==null){
			return false;
		}
		return true;
	}
	
	//用户点输入邮箱密码后判断，若已开始做，直接取返回试题列表;若还未开始，则用户需要填一些必需的信息
	@RequestMapping(value = "/starttest")
	@ResponseBody
	public ResponseBase startTest(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		if(validateUser(params) == false){
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("你没有权限做题");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
			return rb;
		}
		List problems = testusrService.startTest(params);
		if(problems != null){
			rb.setState(1);
			rb.setMessage(problems);
		}else{
			rb.setState(0);
		}
		return rb;
	}
	
	/*
	 *获取用户的试题列表 
	 * 
	 */
	@RequestMapping(value = "/fetchproblems")
	@ResponseBody
	public ResponseBase fetchProblems(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
			if(validateUser(params) == false){
				ResponseMessage rm = new ResponseMessage();
				rm.setMsg("你没有权限做题");
				rm.setHandler_url("/error");
				rb.setMessage(rm);
				rb.setState(0);
				return rb;
		}
			
		
	}
	
	
	//取一道试题
	@RequestMapping(value = "/getquestion")
	@ResponseBody
	public ResponseBase getQuestion(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		
		if(validateUser(params) == false){
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("你没有权限做题");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
			return rb;
		}
		int problemid = (int)params.get("problemid");
	
		Question question = problemService.getProblemById(problemid);
		rb.setMessage(question);
		rb.setState(1);
		return rb;
	}
	
	//提交试题答案
	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseBase submit(@RequestBody Map<String,Object> params){
		
		ResponseBase rb = new ResponseBase();
		if(validateUser(params) == false){
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("你没有权限做题");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
			return rb;
		}
		
		//提交用户的回答
		int problemid = (int)params.get("problemid");
		String useranswer = (String)params.get("useranswer");
		int tuid =(int)params.get("tuid");
		TuserProblem testuserProblem  = new TuserProblem();
		
		testusrService.updateProblem(testuserProblem);		
		rb.setState(1);
		return rb;
		
	}
	

}
