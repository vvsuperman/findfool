package zpl.oj.web.Rest.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;
import zpl.oj.util.timer.InviteReminder;

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
		Map rtMap = new HashMap<String,Object>();
		//需做合法性判断，异常等等
		String email= (String) params.get("email");
		int testid = Integer.parseInt((String)params.get("testid"));
		Invite invite = inviteDao.getInvites(testid, email);
		
		//用户邀请不合法
		if(invite==null){
			rtMap.put("msg", "非法用户");
			return rtMap;
		}else if(invite.getState() == ExamConstant.INVITE_FINISH){
			rtMap.put("msg", "试题已截至");
			return rtMap;
		}
		
		int tuid = invite.getUid();
		rtMap.put("tuid", tuid);
		return rtMap;
	}
	
	/*
	 *检查url是否合法
	 * 
	 * */
	 
	@RequestMapping(value = "/checkurl")
	@ResponseBody
	public ResponseBase checkUrl(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		Map rtMap = validateUser(params);
		String msg = (String) validateUser(params).get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		rb.setState(1);
		return rb;
	}
	
	/*
	 * 登陆，判断用户合法性
	 * 判断用户是否已经开始做题，返回试题列表
	 * 未开始，返回用户信息
	 * 
	 * */
	
	@RequestMapping(value = "/login")
	@ResponseBody
	public ResponseBase login(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		int testid = Integer.parseInt((String)params.get("testid"));
		String email = (String)params.get("email");
		
		Testuser tmpuser = tuserDao.findTestuserByName(email);
		//无此用户
		if(tmpuser==null){
			rb.setState(0);
			return rb;
		}else{
			//判读用户是否已经开始做题，若已开始，直接给出题目列表
			Invite invite = inviteDao.getInvites(testid, email);
			String nowDate = df.format(new Date());
			
			if(nowDate.compareTo(invite.getBegintime())>0){
					//用户已开始做题，直接返回tuserproblem的list
					List<TuserProblem> tuserProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
					rb.setState(1);
					rb.setMessage(tuserProblems);
					return rb;
			}else{  
			//未开始
				rb.setState(2);
				return rb;
		//	}
		}
	}
}
	

	//提交用户信息
	@RequestMapping(value = "/submituserinfo")
	@ResponseBody
	public ResponseBase submitUserInfo(@RequestBody Map<String, Object> params){
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if(tuid ==null){
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		int testid = Integer.parseInt((String)params.get("testid"));
		String email = (String)params.get("email");
		Invite invite = inviteDao.getInvites(testid, email);
		
		
		Map userMap = (Map) params.get("tuser");
		Testuser tuser = tuserDao.findTestuserByName(email);
		tuser.setUsername((String)userMap.get("username"));
		tuser.setSchool((String)userMap.get("school"));
		tuser.setBlog((String)userMap.get("blog"));
		tuser.setTel((String)userMap.get("tel"));
		tuser.setTuid(tuid);
		tuserDao.updateTestuserById(tuser);
		//计算该试题的信息，选择题有X道，简答题有X道,时间为多长，等等
		Map rtMap = tuserService.getTestInfo(testid);
		rtMap.put("duration", invite.getDuration());
		rb.setMessage(rtMap);
		rb.setState(1);
		return rb;
	}
	
	/*
	 *开始做题，初始化试题列表
	 *
	 * */
	 
	@RequestMapping(value = "/starttest")
	@ResponseBody
	public ResponseBase startTest(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		String msg = (String)map.get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		
		int testid = Integer.parseInt((String)params.get("testid"));
		String email = (String)params.get("email");
		Integer tuid = (Integer) map.get("tuid");
		Invite invite = inviteDao.getInvites(testid, email);
		Date date = new Date();
		invite.setBegintime(df.format(date));
		//建立定时器，到时间后将邀请置为无效
		new InviteReminder(Integer.parseInt(invite.getDuration()), invite.getIid(),inviteDao);
		inviteDao.updateInvite(invite);
		
		int inviteId = invite.getIid();
		List<TuserProblem> tProblems = tuserService.initialProblems(testid,tuid,inviteId);
		rb.setState(1);
		rb.setMessage(tProblems);
		return rb;
	
	}
	
	
	
	/*参数
	 * testid
	 * email
	 * useranswer
	 * problemid
	 * nowProblemId	 
	 * 提交试题答案,并取下一道题
	 * */
	@RequestMapping(value = "/submit")
	@ResponseBody
	public ResponseBase submit(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		Map map =  validateUser(params);
		
		String msg = (String)map.get("msg");
		if(msg !=null){
			rb.setMessage(msg);
			rb.setState(0);
			return rb;
		}
		
		//提交用户的答案
		if(params.get("problemid")!=null){
			int problemid = (int)params.get("problemid");
			String useranswer = (String)params.get("useranswer");
			String email = (String)params.get("email");
			int tuid = tuserDao.findTestuserByName(email).getTuid();
			
			
			TuserProblem testuserProblem  = new TuserProblem();
			testuserProblem.setProblemid(problemid);
			testuserProblem.setUseranswer(useranswer);
			testuserProblem.setTuid(tuid);
			tuserProblemDao.updateAnswerByIds(testuserProblem);	
		}
		
		
		//取下一道题，对选项排序，确保答案能够匹配上
		int nowProblmeId = (int)params.get("nowProblemId");
		Question question = problemService.getProblemById(nowProblmeId);
		MyCompare comp = new MyCompare();  
        // 执行排序  
        Collections.sort(question.getAnswer(),comp); 
        //将正确选项置0
		for(QuestionTestCase qs:question.getAnswer()){
			qs.setIsright("");
		}
		rb.setState(1);
		rb.setMessage(question);
		return rb;
	}
	
	
	public class MyCompare implements Comparator<Object>{  
	    public int compare(Object o0, Object o1) {  
	    	QuestionTestCase case1 = (QuestionTestCase) o0;  
	    	QuestionTestCase case2 = (QuestionTestCase) o1;  
	        if (case1.getCaseId() > case2.getCaseId()) {  
	            return 1; // 第一个大于第二个  
	        } else if (case1.getCaseId() < case2.getCaseId()) {  
	            return -1;// 第一个小于第二个  
	        } else {  
	            return 0; // 等于  
	        }  
	    }  
	}  
	
	/*
	 * 完成测试
	 * 用户完成测试，将invite状态置0
	 * */
	@RequestMapping(value = "/finishtest")
	@ResponseBody
	public ResponseBase finishTest(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		
		String email= (String) params.get("email");
		int testid = Integer.parseInt((String)params.get("testid"));
		Invite invite = inviteDao.getInvites(testid, email);
		//用户邀请不合法
		if(invite==null){
			rb.setState(0);
			rb.setMessage("非法访问");
			return rb;
		}else if(invite.getState() == 0){
			rb.setState(0);
			rb.setMessage("试题已截至");
			return rb;
		}
		invite.setState(0);
		inviteDao.updateInvite(invite);
		rb.setState(1);
		return rb;
	}
}
