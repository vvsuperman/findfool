package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foolrank.model.CompanyModel;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.InviteDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Challenge;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.Invite;
import zpl.oj.model.request.User;
@Service
public class PersonalService {

	@Autowired
	private TestuserDao testuserDao;
	
	@Autowired
	private InviteDao inviteDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CompanyDao companyDao;

	@Autowired
	private QuizDao quizDao;
	

	public Map<String, Object> findAllList(String email) {
		
		List<Challenge> myChallenge = new ArrayList<Challenge>();
		Testuser testuser = testuserDao.findTuserByEmail(email);

		List<Invite> inviteList = inviteDao.getInviteByUid(testuser.getTuid());
		for (Invite invite : inviteList) {
			Challenge challenge = new Challenge();
			User user = userDao.getUserIdByUid(invite.getHrid());
			CompanyModel company = companyDao.getByUid(user.getCompanyId());
			Quiz quiz = quizDao.getQuiz(invite.getTestid());
			challenge.setChallengeScore(invite.getScore());
			if (company != null) {

				challenge.setChallengeCompany(company.getName());
			}
			challenge.setChallengeTime(invite.getFinishtime());
			if (quiz.getExtraInfo() != null) {

				challenge.setChallengeName(quiz.getExtraInfo());
			}

			myChallenge.add(challenge);

	}
		
		
		
		
		
		  Map<String,Object> map=new HashMap<String,Object>();
		  
		map.put("myChallenge", myChallenge);
		map.put("testuser",testuser);
		
		
		return map;
		
		
		
		
		
		
		
		
	}
}
