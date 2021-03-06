package zpl.oj.web.Rest.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.LogTakeQuizDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.QuizProblemDao;
import zpl.oj.dao.RandomQuizDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;
import zpl.oj.model.common.LogTakeQuiz;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizEmail;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.RandomQuizSet;
import zpl.oj.model.common.School;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.InviteService;
import zpl.oj.service.LabelService;
import zpl.oj.service.ProblemService;
import zpl.oj.service.QuizEmailService;
import zpl.oj.service.QuizService;
import zpl.oj.service.SchoolService;
//import zpl.oj.service.imp.LogService;
import zpl.oj.service.imp.TuserService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;
import zpl.oj.util.des.DESService;
import zpl.oj.util.timer.InviteReminder;

import com.google.gson.Gson;

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
	private SchoolService schoolService;
	@Autowired
	private LogTakeQuizDao logTakeQuizDao;
	
	@Autowired
	private LabelService labelService;
	
	@Autowired
	private ImgUploadService imgUploadService;
	
	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private TestuserDao testuserDao;
	
	
	@Autowired
	private QuizProblemDao quizProblemDao;
	
	@Autowired
	private 	RandomQuizDao randomQuizDao;

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
	
	@Autowired
	private ProblemDao problemDao;
	
	
	@Autowired
	private QuizEmailService quizEmailService;
	
	@Autowired
	private SecurityService securityService;
	
	
//	@Autowired
//	private LogService logService;

	public Map validateUser(Map params) {
		Map rtMap = new HashMap<String, Object>();
		// 需做合法性判断，异常等等
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		String email = (String) params.get("email");
		                          
		     Quiz quiz=    quizDao.getQuiz(testid);
		    Testuser  testuser=  testuserDao.findTuserByEmail(email);
		    Invite invite;
		     if(quiz.getParent()==0 ){ 
		      invite    = inviteDao.getInviteByTestidAndUser(testuser.getTuid(),testid);  
		      if(invite==null){
		    	   invite=inviteDao.getInviteByTestidAndParent(testuser.getTuid(),testid);
		    	  
		      };
		     }
		     else {
		     invite= inviteDao.getInvites(testid, email);
		     }
		// 用户邀请不合法
		if (invite == null) {
			rtMap.put("msg", "非法用户");
			return rtMap;
		} else if (invite.getState() == ExamConstant.INVITE_FINISH) {
			rtMap.put("msg", "1");
			return rtMap;
		}

		int tuid = invite.getUid();
		rtMap.put("tuid", tuid);
		rtMap.put("invite", invite);
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
		
		Integer tuid = (Integer) rtMap.get("tuid");
		if (tuid == null) {
			rb.setMessage(rtMap.get("msg"));
			rb.setState(4);
			return rb;
		}
		
		Testuser tuser = tuserDao.findTestuserById(tuid);
		rb.setToken(securityService.computeToken(tuser));
		
		int testid = Integer.parseInt((String)params.get("testid"));
		String email = (String)params.get("email");
	    Quiz quiz=quizDao.getQuiz(testid);
	     Invite  invite= inviteDao.getInvites(testid, email);

	     if(invite==null){
	    	 
	    	 invite = inviteDao.getInvitesByP(testid, email);
	     }
		String nowDate = df.format(new Date());
		if(invite.getStarttime().equals("")==false && nowDate.compareTo(invite.getStarttime())<0){
			rb.setMessage("试题尚未开始");
			rb.setState(2);
			return rb;
		}
		
		else if(invite.getStarttime().equals("")==false && nowDate.compareTo(invite.getDeadtime())>0){
			rb.setMessage("试题已截至");
			rb.setState(3);
			return rb;
		}
		rb.setState(1);
		return rb;
	}
	
	
	@RequestMapping(value = "/checkstate")
	@ResponseBody
	public ResponseBase checkState(@RequestBody Map<Object,Object> params){
		ResponseBase rb = new ResponseBase();

		int testid = (Integer)params.get("testid");
		String email = (String)params.get("email");
		Invite invite = inviteDao.getInvites(testid, email);
		Testuser testuser=testuserDao.findTuserByEmail(email);
		Invite invite2=inviteDao.getInviteByTestidAndParent(testuser.getTuid(), testid);
		String nowDate = df.format(new Date());
		Quiz  quiz=quizDao.getQuiz(testid);
		if(quiz.getStartTime()!=null && nowDate.compareTo(quiz.getStartTime())<0){
			rb.setMessage("试题尚未开始");
			rb.setState(2);
			return rb;
		}
		
		else if(quiz.getEndTime()!=null && nowDate.compareTo(quiz.getEndTime())>0){
			rb.setMessage("试题已截至");
			rb.setState(3);
			return rb;
		} else if(invite==null&&invite2==null){
			rb.setState(1);
			return rb;
		}
		else if (invite!=null&&invite.getState() == ExamConstant.INVITE_FINISH) {
					rb.setMessage("试题已经完成");
					rb.setState(0);
				 
				return rb;
			}else if(invite2!=null&&invite2.getState() == ExamConstant.INVITE_FINISH) {
				rb.setMessage("试题已经完成");
				rb.setState(0);
			 
			return rb;
		}	
		rb.setState(1);
		return rb;
	}
			
	
	@RequestMapping(value = "/getLabels")
	@ResponseBody
	public ResponseBase getLabels(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		String email = (String)params.get("email");	
		
		Invite invite = inviteService.getInvites(testid, email);
		List<Labeltest> labelList;
	if(invite.getTestid()!=invite.getParentquiz()){
		 labelList=labelService.getLabelsOfTest(invite.getParentquiz());
	}else{
	   labelList=labelService.getLabelsOfTest(testid);

	}
		
		List<JsonLable> jsonLables=new ArrayList<TestingController.JsonLable>();
		for(Labeltest lt:labelList){
			if(lt.getIsSelected()==0) continue;
			JsonLable jsonLable=new JsonLable();
			Label label=labelService.getLabelById(lt.getLabelid());
			if(label==null){
				rb.setState(0);
				rb.setMessage("标签不存在！");
				return rb;
			}
			jsonLable.setLabelid(lt.getLabelid());
			jsonLable.setLabelname(label.getName());
			LabelUser labelUser=labelService.getLabelUserByIidAndLid(invite.getIid(), lt.getLabelid());
			if(labelUser==null){
				rb.setState(0);
				rb.setMessage("labeluser不存在！");
				return rb;				
			}
			jsonLable.setValue(labelUser.getValue());
			jsonLables.add(jsonLable);
		}
		
		rb.setState(1);
		rb.setMessage(jsonLables);
		return rb;
	}
	
	public class JsonLable{
		private Integer labelid;
		private String labelname;
		private String value;
		public Integer getLabelid() {
			return labelid;
		}
		public void setLabelid(Integer labelid) {
			this.labelid = labelid;
		}
		public String getLabelname() {
			return labelname;
		}
		public void setLabelname(String labelname) {
			this.labelname = labelname;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	/*
	 * 登陆，判断用户合法性
	 * 判断用户是否已经开始做题，返回试题列表
	 * 未开始，返回用户信息
	 * 
	 * */
	
	@RequestMapping(value = "/checkpwd")
	@ResponseBody
	public ResponseBase login(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		
		Map map = validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if (tuid == null) {
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		
		int testid = Integer.parseInt((String)params.get("testid"));
		String email = (String)params.get("email");
		String pwd = (String)params.get("pwd");
		 int	newTestId=testid;

		Invite invite = inviteDao.getInvites(testid, email);
		if(invite==null){
			invite=inviteDao.getInvitesByP(testid, email);
		}

		//用户名、密码不匹配
		if(invite.getPwd().equals(pwd)==false){
			rb.setState(0);
			rb.setMessage(2);
			return rb;
		}
		
		
		// 判断该测试是否为随机，如果是随机调用生成试题方法，如果不是，跳过

		Quiz quiz = quizDao.getQuiz(testid);
//		if (invite.getParentquiz() == 0) {   by fw, 0928 bug?
		if(quiz.getParent() ==0 ){	
			genRandomQuiz(invite);
			Invite invite2 = inviteDao.getInvitesByP(testid, email);
			newTestId=invite2.getTestid();
//如果已经生成随机试题，就不再生成
		//	newTestId =302;;
		} else if ((invite.getParentquiz() != 0) && (invite.getParentquiz()!=invite.getTestid())) {
		 	newTestId = invite.getTestid();
		}
		
		//判读用户是否已经开始做题，若已开始，直接给出题目列表
		if(invite.getBegintime().equals("")==false){
			//用户已开始做题，直接返回tuserproblem的list
			List<TuserProblem> tuserProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
			Map<String, Object> message=new HashMap<String, Object>();
		
			message.put("openCamera", invite.getOpenCamera());
		
			message.put("tuserProblems", tuserProblems);
			message.put("newTestId", newTestId);
			rb.setMessage(message);
			rb.setState(1);
			return rb;
		}else{  
		//未开始
			rb.setState(2);
			Map<String, Integer> message=new HashMap<String, Integer>();
			message.put("invitedid", invite.getIid());
			message.put("openCamera", invite.getOpenCamera());
			message.put("newTestId", newTestId);

			rb.setMessage(message);
			return rb;
		}
	}
	/*
	 * 根据传递的参数获取学校信息
	 */

	@RequestMapping(value = "/getSchools",method = RequestMethod.GET)
	@ResponseBody
	public ResponseBase getSchools(@RequestParam String name,String email,String testid) {
		ResponseBase rb = new ResponseBase();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("email", email);
		params.put("testid", testid);
		Map map = validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if (tuid == null) {
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		List<School> schools=schoolService.getSchoolsByName(name);
		rb.setState(200);
		rb.setMessage(schools);
		return rb;
	}

	
	
	//得到试题详情，（如果用户没有开启摄像头和标签，直接跳转试卷详情也可以得到信息）
	@RequestMapping(value = "/getTestTail")
	@ResponseBody
	public ResponseBase getTestTail(@RequestBody Map<String, Object> params) {
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if (tuid == null) {
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		//int testid = Integer.parseInt((String) params.get("testid"));
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		String email = (String) params.get("email");
		Invite invite = inviteDao.getInvites(testid, email);
	
		Map rtMap = tuserService.getTestInfo(testid);
		rtMap.put("duration", invite.getDuration());
		rb.setMessage(rtMap);
		rb.setState(1);
		return rb;
	}

	/*
	 * 根据传递的参数获取学校信息
	 */

	// 提交用户信息
	@RequestMapping(value = "/submituserinfo")
	@ResponseBody
	public ResponseBase submitUserInfo(@RequestBody Map<String, Object> params) {
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if (tuid == null) {
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		//int testid = Integer.parseInt((String) params.get("testid"));
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		String email = (String) params.get("email");
		Invite invite = inviteDao.getInvites(testid, email);

//		Map userMap = (Map) params.get("tuser");
//		Testuser tuser = tuserDao.findTestuserByName(email);
//		tuser.setUsername((String) userMap.get("username"));
//		tuser.setSchool((String) userMap.get("school"));
//		tuser.setBlog((String) userMap.get("blog"));
//		tuser.setTel((String) userMap.get("tel"));
//		tuser.setTuid(tuid);
//		tuserDao.updateTestuserById(tuser);
		// 计算该试题的信息，选择题有X道，简答题有X道,时间为多长，等等
		String userInfo=params.get("userInfo").toString();
		if(userInfo.length()>2){
			userInfo=userInfo.substring(2,userInfo.length()-2);
			String[] infos=userInfo.split("\\}, \\{");
			Gson gson=new Gson();
			for(int i=0;i<infos.length;i++){
				infos[i]="{"+infos[i]+"}";
				JsonLable label=gson.fromJson(infos[i], JsonLable.class);
				labelService.updateLabelUser(invite.getIid(), label.getLabelid(), label.getValue());
			}
		}
		
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
		
		
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		
		String email = (String)params.get("email");
		Integer tuid = (Integer) map.get("tuid");
		Invite invite = inviteDao.getInvites(testid, email);
		
		//对某一test、用户对发送第一次测试
		List<TuserProblem> tProblems=null;
		invite.setState(ExamConstant.INVITE_PROGRESS);
		
		//初始化开始时间
		if(invite.getBegintime().equals("")==true){
			tProblems = tuserService.initialProblems(testid,tuid,invite.getIid());
			Date date = new Date();
			invite.setBegintime(df.format(date));
			//建立定时器，到时间后将邀请置为无效
			new InviteReminder(Integer.parseInt(invite.getDuration()), invite.getIid(),inviteDao);
			inviteDao.updateInvite(invite);
		}else{
			tProblems =  tuserProblemDao.findProblemByInviteId(invite.getIid());
		}
		
		Map rtMap = new HashMap<String, Object>();
		rtMap.put("invite", invite);
		rtMap.put("problems", tProblems);
		rb.setState(1);
		rb.setMessage(rtMap);
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
	public ResponseBase submit(@RequestBody Map<String, Object> params) {
		ResponseBase rb = new ResponseBase();
		Map map = validateUser(params);

		Integer tuid = (Integer) map.get("tuid");
		Integer problemLength = (Integer) params.get("problemLength");
		if(tuid ==null){
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
	     int questionType=(int)params.get("questionType");
		Invite invite = (Invite)map.get("invite");
		
		if(questionType==4){
//			int problemid = (int)params.get("problemid");
//			String useranswer = (String)params.get("useranswer");
//			TuserProblem testuserProblem=	tuserProblemDao.findByPidAndIid(invite.getIid(),problemid);
//			
//			TuserProblem testuserProblem  = new TuserProblem();
//			testuserProblem.setProblemid(problemid);
//			testuserProblem.setUseranswer(useranswer);
//			testuserProblem.setInviteId(invite.getIid());
		//	tuserProblemDao.updateAnswerByIds(testuserProblem);	
			
		}else if(params.get("problemid")!=null){
			int problemid = (int)params.get("problemid");
			String useranswer = (String)params.get("useranswer");
			
			TuserProblem testuserProblem  = new TuserProblem();
			testuserProblem.setProblemid(problemid);
			testuserProblem.setUseranswer(useranswer);
			testuserProblem.setInviteId(invite.getIid());
			tuserProblemDao.updateAnswerByIds(testuserProblem);	
//			logService.updateTuserAction(invite, index);
		}
		
		
		//取下一道题，对选项排序，确保答案能够匹配上
		int nowProblmeId = (int)params.get("nowProblemId");
		Question question = problemService.getProblemById(nowProblmeId);
		TuserProblem tProblem = tuserProblemDao.findByPidAndIid(invite.getIid(), nowProblmeId);
		question.setUseranswer(tProblem.getUseranswer());
		question.setRightanswer("");
		MyCompare comp = new MyCompare();  
        // 执行排序  
        Collections.sort(question.getAnswer(),comp); 
        //将正确选项置0
		for(QuestionTestCase qs:question.getAnswer()){
			qs.setIsright("");
		}
		//存用户的答题记录
		if(problemLength>=((int)params.get("index"))){
		LogTakeQuiz log = new LogTakeQuiz();
		log.setIid(invite.getIid());
		log.setProblemid(nowProblmeId);
		log.setTime(new Date());
		log.setNum((int)params.get("index"));
		logTakeQuizDao.saveQuizLog(log);
		}
		
		rb.setState(1);
		rb.setMessage(question);
		return rb;
	}
	
	
	/*
	 * 参数
	 *获取一道题
	 * */
	@RequestMapping(value = "/fetchProblem")
	@ResponseBody
	public ResponseBase fetchQuestion(@RequestBody Map<String,Object> params){
		ResponseBase rb = new ResponseBase();
		Map map =  validateUser(params);
		Integer tuid = (Integer) map.get("tuid");
		if(tuid ==null){
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		
		//取一道题，对选项排序，确保答案能够匹配上
		Invite invite = (Invite)map.get("invite");
		int problmeId = (int)params.get("problemId");
		Question question = problemService.getProblemById(problmeId);
		TuserProblem tProblem = tuserProblemDao.findByPidAndIid(invite.getIid(), problmeId);
		question.setUseranswer(tProblem.getUseranswer());
		
		MyCompare comp = new MyCompare();  
        // 执行排序  
        Collections.sort(question.getAnswer(),comp); 
        //将正确选项置0
        question.setRightanswer("");
		for(QuestionTestCase qs:question.getAnswer()){
			qs.setIsright("");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//存用户的答题记录
		LogTakeQuiz log = new LogTakeQuiz();
		log.setIid(invite.getIid());
		log.setProblemid(problmeId);
		log.setTime(new Date());
		log.setNum((int)params.get("index"));
		logTakeQuizDao.saveQuizLog(log);
		
		//记录取题的log
		
		
		
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
	public ResponseBase finishTest(@RequestBody Map<String, Object> params) {
		ResponseBase rb = new ResponseBase();

		String email = (String) params.get("email");
		//int testid = Integer.parseInt((String) params.get("testid"));
		String idString=params.get("testid").toString();
		int testid = Integer.parseInt(idString);
		Invite invite = inviteDao.getInvites(testid, email);
		// 用户邀请不合法
		if (invite == null) {
			rb.setState(1);
			rb.setMessage("非法访问");
			return rb;
		} else if (invite.getState() == ExamConstant.INVITE_FINISH) {
			rb.setState(1);
			rb.setMessage("试题已截至");
			return rb;
		}
		invite.setState(ExamConstant.INVITE_FINISH);
		inviteDao.updateInvite(invite);
		
		
		//存用户的答题记录
		//LogTakeQuiz log = new LogTakeQuiz();
		//log.setIid(invite.getIid());
		//log.setTime(new Date());
		//log.setNum(-1); //-1表示答题完成
		//logTakeQuizDao.saveQuizLog(log);
				
		
		//用戶完成测试后，将公开链接发送到测试所设置的邮箱
		List<QuizEmail> emailList=quizEmailService.getEmailsByQuizId(testid);
		for(QuizEmail e:emailList){
			quizEmailService.sendMail(e, invite);
		}
		
		//生成用户的分数,先不做吧
		
		
		//如果是来自yida的信息，则将做题信息返回
		User user = userService.getUserByEmail(email);	
		if(user.getUserSource() == ExamConstant.SOURCE_YIDA){
			String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
			String url = desService.encode(invite.getIid().toString()+"|"+invite.getTestid().toString()+"|"+invite.getUid().toString());
			String targetUrl = baseurl+"/#/publicReport/list/"+url;			
			sendToYida(user, 0, targetUrl);
		}		
		
		rb.setState(0);
		return rb;
	}
	
	
	//发送测试结果到yida软件
	public void sendToYida(User user, int score, String scoreUrl){
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
      
        
        
        try {
            URL realUrl = new URL(ExamConstant.YIDA_URL);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Method", "JOB.INTERVIEWPROCESS.UPDATE");
            conn.setRequestProperty("Company",BASE64.encodeBASE64(user.getCompany().getBytes()) );
            conn.setRequestProperty("CompanyName",BASE64.encodeBASE64(user.getCompanyName().getBytes()) );
            conn.setRequestProperty("Email",BASE64.encodeBASE64( user.getEmail().getBytes()));
            conn.setRequestProperty("Phone", BASE64.encodeBASE64( user.getTel().getBytes()));
            conn.setRequestProperty("LoginName", BASE64.encodeBASE64( user.getFname().getBytes()));
            
            conn.setRequestProperty("EvaluationResult", BASE64.encodeBASE64(scoreUrl.getBytes()));
//            conn.setRequestProperty("score",BASE64.encodeBASE64( (score+"").getBytes()));
            conn.setRequestProperty("Password",BASE64.encodeBASE64( user.getPwd().getBytes()));
            
            System.out.print("begin send......");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("发送成功"+result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
      
    }    
	
	
	public static void main(String[] args){
		TestingController tc = new TestingController();
		User user = new User();
		user.setCompany("foolrank");
		user.setTel("13761229484");
		user.setFname("sh003");
		user.setEmail("772012319@qq.com");
		user.setPwd("93763935");
		user.setCompanyName("演示公司");
		
		
		
		tc.sendToYida(user, 260, "www.foolrank.com");
	}
	
	
	
	
	
	
	// 设计题添加文件
		@RequestMapping(value = "/uploadimg")
		@ResponseBody
		public ResponseBase uploadCompanyImg(
				@RequestParam MultipartFile[] file,
				// @RequestBody Map<String, String> params,
				@RequestHeader(value = "Authorization", required = false) String arg) {

			ResponseBase rb = new ResponseBase();
		
			   String   args[]=arg.split(",");
			
			   
			   Integer testid = Integer.parseInt(args[0]);
			   String email=args[1];
				Invite invite = inviteDao.getInvites(testid, email);
				int tuid = invite.getUid();
                
				if(tuid ==0){
					rb.setMessage("试题id不能为空");
					rb.setState(0);
					return rb;
				}
				
				if (file == null) {
					rb.setState(2);
					rb.setMessage("文件为空");
					return rb;
				}
				int problemid=Integer.parseInt(args[2]);
				
				String  temail=email.replace(".","/");
				
				//提交用户的答案
				if(file!=null){
					TuserProblem testuserProblem  = new TuserProblem();
					testuserProblem.setProblemid(problemid);
					testuserProblem.setEmail(temail);
				
					testuserProblem.setInviteId(invite.getIid());
					for (MultipartFile fileitem : file) {
						if (!fileitem.isEmpty()) {
							imgUploadService.saveTestingFile(testuserProblem, fileitem);
						}
					}	
				}
				return rb;
				
				

		}
		
		
	// 根据randomquiz的要求生成quiz
	public void genRandomQuiz(Invite invite) {

		// 根据类型和难度和数量找到试题
		// 生成新的quiz ，并制定parent为原来的quiz
		// 更新invite testid更新
	       int	testid=invite.getTestid();
		Quiz newquiz = quizDao.getQuiz(testid);
		//Quiz newquiz = new Quiz();
		//Quiz newquiz = new Quiz();
		int parent=newquiz.getQuizid();
		newquiz.setQuizid(null);
		newquiz.setParent(parent);
		 quizDao.insertQuizRerurn(newquiz);
		 int quizid=newquiz.getQuizid();
		invite.setParentquiz(testid);
		invite.setTestid(quizid);
		inviteDao.updateInvite(invite);

		// 从problem中按照规则选出题，并插入到quizproblem中；

		List<RandomQuizSet> randomList = randomQuizDao.getListByTestid(testid);

		for (RandomQuizSet randomQuizSet : randomList) {
			List<Integer> list = problemDao.getPBySetidAndLevel(
					randomQuizSet.getProblemSetId(), randomQuizSet.getLevel());
			if (randomQuizSet.getNum() < list.size()) {
				int[] random = randomCommon(0, list.size(),
						randomQuizSet.getNum());
				for (int i = 0; i < random.length; i++) {
					QuizProblem quizProblem = new QuizProblem();
					quizProblem.setProblemid(list.get(random[i]));
					quizProblem.setQuizid(quizid);
					quizProblemDao.insertQuizproblem(quizProblem);

				}

			}
		}

		return ;

	}


	public static int[] randomCommon(int min, int max, int n) {
		if (n > (max - min + 1) || max < min) {
			return null;
		}
		int[] result = new int[n];
		int count = 0;
		while (count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if (num == result[j]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result[count] = num;
				count++;
			}
		}
		return result;
	}
	

}
