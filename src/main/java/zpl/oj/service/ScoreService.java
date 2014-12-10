package zpl.oj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.TuserProblem;

@Service
public class ScoreService {
	
	@Autowired
	private TuserProblemDao tuserProblemDao;
	@Autowired
	private ProblemDao problemDao;
	
    public int getUserScore(int inviteId){
		   
		   return 0;
		   
	}

	public int getTotalScore(Invite invite) {
		List<Problem> problems = problemDao.getProblemByTestid(invite.getTestid());
		List<TuserProblem> tProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
		
		return 0;
	}
}
