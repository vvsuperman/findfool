package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import zpl.oj.util.Constant.Examconstant;

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
	
	@Autowired
	public ProblemTestCaseDao problemTestCaseDao;
	
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
	


	public  List<TuserProblem> initialProblems(int testid, int tuid) {
		// TODO Auto-generated method stub
		
			
		List<Problem> listProblem = problemDao.getProblemByTestid(testid);
		for(Problem problem:listProblem){
			TuserProblem tuserProblem = tuserProblemDao.findByPidAndUid(tuid, problem.getProblemId());
			if(tuserProblem == null){
				tuserProblem = new TuserProblem();
				tuserProblem.setRightanswer(problem.getRightAnswer());
				tuserProblem.setProblemid(problem.getProblemId());
				tuserProblem.setTuid(tuid);
				tuserProblem.setType(problem.getType());
				tuserProblemDao.insertTuserProblem(tuserProblem); 
			}else{
				tuserProblem.setRightanswer(problem.getRightAnswer());
				tuserProblem.setType(problem.getType());
				tuserProblemDao.updateProblemByIds(tuserProblem); 
			}
		}
		return tuserProblemDao.findProblemByTestid(testid);
	}

	public  List<TuserProblem> findProblemByTestid(int testid) {
		// TODO Auto-generated method stub
		return tuserProblemDao.findProblemByTestid(testid);
	}


	//统计试题
	public Map getTestInfo(int testid) {
		// TODO Auto-generated method stub
		List<Problem> problems = problemDao.getProblemByTestid(testid);
		int option = 0;
		int essay = 0;
		int program = 0;
		for(Problem problem: problems){
			if(problem.getType() == Examconstant.OPTION){
				option++;
			}else if(problem.getType() == Examconstant.ESSAY){
				essay++;
			}else if(problem.getType() == Examconstant.PROGRAM){
				program++;
			}
		}
		Map rtMap = new HashMap<String, Integer>();
		rtMap.put("optionNum", option);
		rtMap.put("essayNum", essay);
		rtMap.put("programNum", program);
		
		return rtMap;
	}
	

	
	
}
