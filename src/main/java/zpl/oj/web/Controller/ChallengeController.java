/**
 * 公开挑战赛控制器
 * 
 * @author Moon
 */
package zpl.oj.web.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.InviteDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.QuizProblemDao;
import zpl.oj.dao.RandomQuizDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.CadTest;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Labeltest;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.RandomQuizSet;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.InviteService;
import zpl.oj.service.LabelService;
import zpl.oj.service.imp.TuserService;
import zpl.oj.service.imp.CompanyService;
import zpl.oj.util.StringUtil;
import zpl.oj.util.Constant.ExamConstant;

import com.foolrank.response.json.SimpleChallenge;
import com.foolrank.util.RequestUtil;

@Controller
@RequestMapping("/challenge")
public class ChallengeController {
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private TestuserDao testuserDao;
	
	@Autowired
	private InviteDao inviteDao;
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private TuserProblemDao tuserProblemDao;
	
	@Autowired
	private TuserService tuserService;
	
	@Autowired
	private LabelService labelService;

	@Autowired
	private InviteService inviteService;
	
	
	@Autowired
	private RandomQuizDao randomQuizDao;
	
	
	
	@Autowired
	private QuizProblemDao quizProblemDao;
	
	//根据signedid获取testid
	@RequestMapping(value = "/checktest")
	@ResponseBody
	public ResponseBase checkTest(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();
		String signedkey = RequestUtil.getStringParam(params, "signedkey", "", true);
		if(signedkey.equals("")) {
			rb.setState(1);
			rb.setMessage("输入不得为空");
			return rb;
		}
		
		Quiz quiz = quizDao.getQuizByKey(signedkey);
		if(quiz == null) {
			rb.setState(1);
			rb.setMessage("无对应试卷");
			return rb;
		}
		
		rb.setState(0);
		rb.setMessage(tuserService.getTestInfo(quiz.getQuizid()));
		return rb;
	}

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase start(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();

		String email = RequestUtil.getStringParam(params, "email", "", true);
		if (email.equals("")) {
			rb.setState(10001);
			rb.setMessage("email不得为空");
			return rb;
		}
		String signedKey = RequestUtil.getStringParam(params, "signedKey",true);
		if(signedKey.equals("")){
			rb.setState(10002);
			rb.setMessage("signedKey不得为空");
			return rb;
		}
		
		Quiz quiz = quizDao.getQuizByKey(signedKey);
		if (quiz == null) {
			rb.setState(20001);
			rb.setMessage("挑战不存在或者不在进行");
			return rb;
		}
		
		
		String nowtime = StringUtil.nowDateTime();
		

		if(quiz.getStartTime()!="" && nowtime.compareTo(quiz.getStartTime())<0 ){
			rb.setState(20002);
			rb.setMessage("挑战赛未开始");
			return rb;
		}else if(quiz.getEndTime()!="" &&nowtime.compareTo( quiz.getEndTime())>0){
			rb.setState(20003);
			rb.setMessage("挑战赛已结束");
			return rb;
		}
		
		
		
		Invite invite;
	
		if (quiz.getParent() == 0) {

			invite = inviteDao.getInvitesByP(quiz.getQuizid(), email);

			if (invite == null) {
				int newquizId = genRandomQuiz(quiz);
				invite = genInviteRandom(email, newquizId, quiz.getQuizid());
				if (invite == null) {
					rb.setState(20004);
					rb.setMessage("您尚未注册和登录，无法进行挑战赛！");
					return rb;
				}
			}
		} else {

			invite = inviteDao.getInvites(quiz.getQuizid(), email);
			if (invite == null) {
				invite = genInvite(email, quiz.getQuizid());
				if (invite == null) {
					rb.setState(20004);
					rb.setMessage("您尚未注册和登录，无法进行挑战赛！");
					return rb;
				}
			}
		}
		
		if (invite.getState() == ExamConstant.INVITE_FINISH) {
			// 试卷已做完
			rb.setState(20005);
			rb.setMessage("您已完成该试卷");
			return rb;
		}
		
		//判读用户是否已经开始做题，若已开始，直接给出题目列表
		if(invite.getBegintime().equals("")==false){
			//用户已开始做题，直接返回tuserproblem的list
			List<TuserProblem> tuserProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
			Map<String, Object> rtMap=new HashMap<String, Object>();
			rtMap.put("problems", tuserProblems);
			rtMap.put("openCamera",invite.getOpenCamera());
			rb.setState(1);
			rb.setMessage(rtMap);
			return rb;
		}else{  
		    //未开始
			
		
			rb.setState(2);
			Map<String, Integer> message=new HashMap<String, Integer>();
			message.put("invitedid", invite.getIid());
			message.put("openCamera", invite.getOpenCamera());
		//	message.put("quizid", quiz.getQuizid());
			message.put("quizid", invite.getTestid());
			rb.setMessage(message);
			return rb;
		}

	}
	
	private Invite genInviteRandom(String email, int newquizId, Integer oldquizid) {
		Testuser testuser = testuserDao.findTuserByEmail(email);
		if(testuser==null){
			
			return null;
			
		}
		Quiz quiz = quizDao.getQuiz(newquizId);
		int tuId = testuser.getTuid();
		Invite invite = new Invite();
		invite.setUid(tuId);
		invite.setTestid(newquizId);
		invite.setScore(0);
		invite.setState(ExamConstant.INVITE_PUB);
		invite.setOpenCamera(quiz.getOpenCamera());
		invite.setStarttime(quiz.getStartTime());
		invite.setDeadtime(quiz.getEndTime());
		invite.setDuration(quiz.getTime()+"");
		invite.setState(ExamConstant.INVITE_PUB);
		invite.setHrid(quiz.getOwner());
		invite.setParentquiz(oldquizid);
		
		inviteDao.add(invite);
		
		// 生成考题
		List<Problem> problemList = problemDao.getProblemByTestid(newquizId);
		for (Problem problem : problemList) {
			TuserProblem tuserProblem = new TuserProblem();
			tuserProblem.setInviteId(invite.getIid());
			tuserProblem.setTuid(tuId);
			tuserProblem.setProblemid(problem.getProblemId());
			
			tuserProblemDao.insertTuserProblem(tuserProblem);
		}
		
		List<Labeltest> labeltests = labelService.getLabelsOfTest(oldquizid);
		for (Labeltest lt : labeltests) {
			Invite invite2 = inviteService.getInvites(newquizId,email);
			if (labelService.getLabelUserByIidAndLid(invite2.getIid(),lt.getLabelid()) == null) {
				labelService.insertIntoLabelUser(invite2.getIid(),lt.getLabelid(), "");
			}
		}
		return invite;
	}

	private Invite genInvite(String email, int quizId) {
		Testuser testuser = testuserDao.findTuserByEmail(email);
		if(testuser==null){
			
			return null;
			
		}
		
		
		
		Quiz quiz = quizDao.getQuiz(quizId);
		int tuId = testuser.getTuid();
		Invite invite = new Invite();
		invite.setUid(tuId);
		invite.setTestid(quizId);
		invite.setScore(0);
		invite.setParentquiz(quizId);
		invite.setState(ExamConstant.INVITE_PUB);
		invite.setOpenCamera(quiz.getOpenCamera());
		invite.setStarttime(quiz.getStartTime());
		invite.setDeadtime(quiz.getEndTime());
		invite.setDuration(quiz.getTime()+"");
		invite.setState(ExamConstant.INVITE_PUB);
		invite.setHrid(quiz.getOwner());
		
		inviteDao.add(invite);
		
		// 生成考题
		List<Problem> problemList = problemDao.getProblemByTestid(quizId);
		for (Problem problem : problemList) {
			TuserProblem tuserProblem = new TuserProblem();
			tuserProblem.setInviteId(invite.getIid());
			tuserProblem.setTuid(tuId);
			tuserProblem.setProblemid(problem.getProblemId());
			
			tuserProblemDao.insertTuserProblem(tuserProblem);
		}
		
		List<Labeltest> labeltests = labelService.getLabelsOfTest(quizId);
		for (Labeltest lt : labeltests) {
			Invite invite2 = inviteService.getInvites(quizId,email);
			if (labelService.getLabelUserByIidAndLid(invite2.getIid(),lt.getLabelid()) == null) {
				labelService.insertIntoLabelUser(invite2.getIid(),lt.getLabelid(), "");
			}
		}
		return invite;
	}

	@RequestMapping(value = "/getListByStatus")
	@ResponseBody
	public ResponseBase getListByStatus(@RequestBody Map<String, String> params) {
		// 获取参数
		String strStatus = params.get("status");
		int status = strStatus == null ? 1 : Integer.parseInt(strStatus.trim());
		if (status < 1) {
			status = 1;
		}
		String strCompanyId = params.get("cid");
		int companyId = strCompanyId == null ? 0 : Integer
				.parseInt(strCompanyId.trim());
		String strPage = params.get("p");
		int page = strPage == null ? 1 : Integer.parseInt(strPage.trim());
		if (page <= 0) {
			page = 1;
		}
		int pageSize = 10;
		int offset = (page - 1) * pageSize;
		// 进行查询
		List<Quiz> challenges = null;
		if (companyId > 0) {
			List<User> users = userDao.getListByCompany(companyId);
			if (users != null) {
				challenges = quizDao.getChallengeListByUsers(users, status,
						offset, pageSize);
			}
		} else {
			challenges = quizDao.getChallengeListByStatus(status, offset,
					pageSize);
		}
		List<SimpleChallenge> result = null;
		if (challenges != null && challenges.size() > 0) {
			result = new ArrayList<SimpleChallenge>();
			for (Quiz challenge : challenges) {
				SimpleChallenge item = new SimpleChallenge();
				item.setId(challenge.getQuizid());
				item.setName(challenge.getName());
				item.setLogo(challenge.getLogo());
				item.setDescription(challenge.getDescription());
				item.setKey(challenge.getSignedKey());
				item.setStartTime(StringUtil.toDateTimeString(challenge
						.getStartTime()));
				item.setEndTime(StringUtil.toDateTimeString(challenge
						.getEndTime()));
				result.add(item);
			}
		}

		ResponseBase rb = new ResponseBase();
		rb.setMessage(result);

		return rb;
	}

	@RequestMapping(value = "/getListByType")
	@ResponseBody
	public ResponseBase getListByType(@RequestBody Map<String, String> params) {
		int corporateId = Integer.parseInt(params.get("cid"));
		// List<Challenge> challenges = new ArrayList<Challenge>();
		ResponseBase rb = new ResponseBase();
		// rb.setMessage(challenges);

		return rb;
	}
	
	
    //Fr挑战赛详情
	@RequestMapping(value = "/getFrChallage")
	@ResponseBody
	public ResponseBase getFrChallage(@RequestBody Map<String, String> params) {
		// 获得fr全部挑战赛
		List<Quiz> frQuizList = quizDao
				.getQuizByType(ExamConstant.QUIZ_TYPE_FRCHALLENGE);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Date date = new Date();
		String str = df.format(date);
		List<Quiz> frQuizBegin = new ArrayList<Quiz>();
		List<Quiz> frQuizOver = new ArrayList<Quiz>();
		List<Quiz> frQuizNaver = new ArrayList<Quiz>();

		for (Quiz quiz : frQuizList) {
			//测试开始时间和结束时间为空
			if(quiz.getStartTime().equals("") || quiz.getEndTime().equals("")){
				frQuizBegin.add(quiz);
			}
				
			quiz.setLogo( companyService.getImg(quiz.getLogo()));
			
			int number1 = str.compareTo(quiz.getStartTime());
			int number2 = str.compareTo(quiz.getEndTime());

			if (number1 < 0) {
				frQuizNaver.add(quiz);
			} else if (number2 >0) {
				frQuizOver.add(quiz);
			} else if ((number1 > 0) && (number2 <0)) {
				frQuizBegin.add(quiz);
			}

		}

		// 返回 0 表示时间日期相同
		// 返回 1 表示日期1>日期2
		// 返回 -1 表示日期1<日期2

		Map<String, Object> map = new HashMap<String, Object>();
		ResponseBase rb = new ResponseBase();
		map.put("frQuizNaver", frQuizNaver);
		map.put("frQuizBegin", frQuizBegin);
		map.put("frQuizOver", frQuizOver);

		rb.setMessage(map);

		return rb;
	}

	
	//挑战赛分类查询方法
	
	private List  getFrCByName() {
		
		return null;
	}
	
	// 根据randomquiz的要求生成quiz
	public int  genRandomQuiz(Quiz quiz) {

		// 根据类型和难度和数量找到试题
		// 生成新的quiz ，并制定parent为原来的quiz
		// 更新invite testid更新
	       int	testid=quiz.getQuizid();
		Quiz newquiz = quizDao.getQuiz(testid);
		//Quiz newquiz = new Quiz();
		//Quiz newquiz = new Quiz();
		int parent=newquiz.getQuizid();
		newquiz.setQuizid(null);
		newquiz.setParent(parent);
		 quizDao.insertQuizRerurn(newquiz);
		 int quizid =newquiz.getQuizid();
		//invite.setParentquiz(testid);
		//invite.setTestid(quizid);
		//inviteDao.updateInvite(invite);

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

		return  quizid;

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
