package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.*;

@Service
public class TuserService {
	@Autowired
	public TestuserDao testuserDao;

	@Autowired
	public TuserProblemDao testuserProblemDao;
	
	@Autowired
	public InviteDao inviteDao;
	
	@Autowired
	public ProblemDao problemDao;
	
	@Autowired	
	public TuserProblemDao tuserProblemDao;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public int updateUser(Testuser testuser) {
		// TODO Auto-generated method stub
		Testuser tuser =testuserDao.findTestuserByName(testuser.getEmail()); 
		if(tuser!=null){
			//该邮箱的用户曾经做过题，执行更新操作
			testuserDao.updateTestuserById(testuser);
			return tuser.getTuid(); 
		}else{
			testuserDao.insertTestuser(testuser);
			return testuserDao.findTestuserByName(testuser.getEmail()).getTuid();
		}
				
	}
	


	public  List<TuserProblem> fetchTProblems(int testid, int tuid) {
		// TODO Auto-generated method stub
		
			
		List<Problem> listProblem = problemDao.getProblemByTestid(testid);
		for(Problem problem:listProblem){
			TuserProblem tuserProblem = new TuserProblem();
			tuserProblem.setRightanswer(problem.getRightAnswer());
			tuserProblem.setProblemid(problem.getProblemId());
			tuserProblem.setTuid(tuid);
			tuserProblem.setType(problem.getType());
			tuserProblemDao.insertTuserProblem(tuserProblem);
		}
		
		return tuserProblemDao.findProblemByTestid(testid);
	}

	public  List<TuserProblem> findProblemByTestid(int testid) {
		// TODO Auto-generated method stub
		return tuserProblemDao.findProblemByTestid(testid);
	}
	

	
	
}
