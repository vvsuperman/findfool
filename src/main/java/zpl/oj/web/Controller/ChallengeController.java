/**
 * 公开挑战赛控制器
 * 
 * @author Moon
 */
package zpl.oj.web.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.CadTest;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.util.StringUtil;
import zpl.oj.util.Constant.ExamConstant;

import com.foolrank.response.json.SimpleChallenge;
import com.foolrank.util.RequestUtil;

@Controller
@RequestMapping("/challenge")
public class ChallengeController {

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

	
	
	//根据signedid获取testid
	@RequestMapping(value = "/gettest")
	@ResponseBody
	public ResponseBase gettest(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();
		String signedkey = (String)params.get("signedkey");
		if(signedkey == null){
			rb.setState(1);
			rb.setMessage("输入不得为空");
			return rb;
		}
		
		Quiz quiz = quizDao.getQuizByKey(signedkey);
		if(quiz == null){
			rb.setState(1);
			rb.setMessage("无对应试卷");
			return rb;
		}
		
		rb.setState(0);
		rb.setMessage(quiz.getQuizid());
		return rb;
		
	}
	
	

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase start(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();

		String email = RequestUtil.getStringParam(params, "email", "", true);
		if (email.equals("")) {
			rb.setState(10001);
			rb.setMessage("email不为空");
			return rb;
		}
		int quizId = RequestUtil.getIntParam(params, "testid");
		if (quizId <= 0) {
			rb.setState(10002);
			rb.setMessage("挑战赛ID非法");
			return rb;
		}

		Quiz quiz = quizDao.getQuiz(quizId);
		if (quiz == null ||
			quiz.getType() != ExamConstant.QUIZ_TYPE_CHALLENGE ||
			quiz.getStatus() != ExamConstant.STATUS_RUNNING) {
			rb.setState(20001);
			rb.setMessage("挑战不存在或者不在进行");
			return rb;
		}

		// 生成invite
		Invite invite = inviteDao.getInvites(quizId, email);
		if (invite == null) {
			invite = genInvite(email, quizId);
		}
		if (invite.getState() == ExamConstant.INVITE_FINISH) {
			// 试卷已做完
			rb.setState(1);
			rb.setMessage("试卷已做完");
			return rb;
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("problems", tuserProblemDao.findProblemByInviteId(invite.getIid()));

		rb.setState(0);
		rb.setMessage(rtMap);
		return rb;
	}
	
	private Invite genInvite(String email, int quizId) {
		Testuser testuser = testuserDao.findTuserByEmail(email);
		if (testuser == null) {
			//
		}
		int tuId = testuser.getTuid();
		Invite invite = new Invite();
		invite.setUid(tuId);
		invite.setTestid(quizId);
		invite.setBegintime(StringUtil.nowDateTime());
		invite.setScore(0);
		invite.setState(ExamConstant.INVITE_PUB);
		inviteDao.add(invite);
		
		// 获取考题
		List<Problem> problemList = problemDao.getProblemByTestid(quizId);
		for (Problem problem : problemList) {
			TuserProblem tuserProblem = new TuserProblem();
			tuserProblem.setInviteId(invite.getIid());
			tuserProblem.setTuid(tuId);
			tuserProblem.setProblemid(problem.getProblemId());
			
			tuserProblemDao.insertTuserProblem(tuserProblem);
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
	
}
