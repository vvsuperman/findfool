package zpl.oj.web.Rest.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
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
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Autowired
	private InviteDao inviteDao;
	
	@Autowired
	private DESService desService;
	
	@Autowired
	private TuserService tuserService;
	
	@Autowired
	private TestuserDao tuserDao;
	
	@Autowired
	private TuserProblemDao tuserProblemDao;
	
	public Map validateUser(Map params){
		Map rtMap = new HashMap<String,String>();
		
		String email= (String) params.get("email");
		int testid = (int) params.get("testid");
		Invite invite = inviteDao.getInvites(testid, email);
		
		//用户邀请不合法
		if(invite==null){
			rtMap.put("msg", "非法用户");
			return rtMap;
		}else if(invite.getState() == 0){
			rtMap.put("msg", "试题已截至");
			return rtMap;
		}
		//用户试题时间已截至
		Date date = new Date();
		if(df.format(date).compareTo(invite.getFinishtime())>0){
			rtMap.put("msg", "试题已截至");
			return rtMap;
		}
		int tuid = invite.getUid();
		rtMap.put("tuid", tuid);
		return rtMap;
	}
	
	/*
	 * 开始做题，用户点输入邮箱密码后触发，若已开始做
	 *   1) 判断试题是否结束
	 *   2）若未结束，返回试题列表;
	 * 若还未开始，则用户需要填一些必需的信息
	 * 
	 * */
	 
	@RequestMapping(value = "/starttest")
	@ResponseBody
	public ResponseBase startTest(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		String msg = (String) validateUser(params).get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		
		int testid = (int)params.get("testid");
		String email = (String)params.get("email");
		
		Invite invite = inviteDao.getInvites(testid, email);
		Date date = new Date();
		
		if(invite.getBegintime().equals("")){
			//用户还未开始做题
			invite.setBegintime(df.format(date));
			//生成完成时间
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.MINUTE,Integer.parseInt(invite.getDuration()));
			invite.setFinishtime(df.format(cal.getTime()));
			Testuser testuser=tuserDao.findTestuserByName(email);
			//返回用户信息供前台填写
			rb.setMessage(testuser);
			rb.setState(1);
			return rb;
		
		}else{
				//用户已开始做题，直接返回tuserproblem的list
				List<TuserProblem> tuserProblems = tuserService.findProblemByTestid(testid);
				rb.setState(1);
				rb.setMessage(tuserProblems);
				return rb;
			}
		}
	
	
	/*
	 *生成，并获取用户的试题列表 
	 * 
	 */
	@RequestMapping(value = "/fetchproblems")
	@ResponseBody
	public ResponseBase fetchProblems(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		String tuid = (String) validateUser(params).get("tuid");
		if(tuid ==null){
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		List<TuserProblem> tProblems = tuserService.fetchTProblems(Integer.parseInt((String)params.get("testid")),Integer.parseInt(tuid));
		rb.setState(1);
		rb.setMessage(tProblems);
		return rb;
	}
	
	
	//取一道试题
	@RequestMapping(value = "/getquestion")
	@ResponseBody
	public ResponseBase getQuestion(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		String msg = (String) validateUser(params).get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		int problemid = (int)params.get("problemid");
		Question question = problemService.getProblemById(problemid);
		//将正确选项置0
		for(QuestionTestCase qs:question.getAnswer()){
			qs.setIsright("");
		}
		rb.setMessage(question);
		rb.setState(1);
		return rb;
	}
	
	//提交试题答案
	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseBase submit(@RequestBody Map<String,Object> params){
		
		ResponseBase rb = new ResponseBase();
		String msg = (String) validateUser(params).get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		//提交用户的回答
		int problemid = (int)params.get("problemid");
		String useranswer = (String)params.get("useranswer");
		String email =(String)params.get("email");
		TuserProblem testuserProblem  = new TuserProblem();
		testuserProblem.setProblemid(problemid);
		testuserProblem.setUseranswer(useranswer);
		tuserProblemDao.updateTProblemByEmail(testuserProblem);		
		rb.setState(1);
		return rb;
	}
	
	//提交用户信息
	@RequestMapping(value = "/submituserinfo")
	@ResponseBody
	public ResponseBase submitUserInfo(@RequestBody Map<String, Object> params){
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		String tuid = (String) map.get("tuid");
		if(tuid ==null){
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		
		Testuser tuser = (Testuser) params.get("tuser");
		tuserDao.updateTestuserById(tuser);
		rb.setState(1);
		return rb;
	}
	

}
