/**
 * 公开挑战赛控制器
 * 
 * @author Moon
 */
package zpl.oj.web.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foolrank.model.Challenge;
import com.foolrank.response.json.SimpleChallenge;

import zpl.oj.dao.ChallengeDao;
import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.util.StringUtil;

@Controller
@RequestMapping("/challenge")
public class ChallengeController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private CompanyDao companyDao;

	@RequestMapping(value = "/{signedId}")
	@ResponseBody
	public String index(@PathVariable("signedId") String signedId) {
		System.out.println(signedId);
		// return new ModelAndView("login");
		return signedId;
	}

	@RequestMapping(value = "/getListByStatus")
	@ResponseBody
	public ResponseBase getListByStatus(@RequestBody Map<String, String> params) {
		// 获取参数
		String strStatus = params.get("status");
		int status = strStatus == null ? 1 : Integer.parseInt(strStatus.trim());
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
			if (users!=null) {
				challenges = quizDao.getChallengeListByUsers(users, status, offset, pageSize);
			}
		} else {
			challenges = quizDao.getChallengeListByStatus(status, offset, pageSize);
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
		List<Challenge> challenges = new ArrayList<Challenge>();
		ResponseBase rb = new ResponseBase();
		rb.setMessage(challenges);

		return rb;
	}
}
