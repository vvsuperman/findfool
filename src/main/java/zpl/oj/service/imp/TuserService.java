package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.*;
import zpl.oj.model.request.User;
import zpl.oj.util.Constant.ExamConstant;

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
		Testuser tuser =testuserDao.findTuserByEmail(testuser.getEmail()); 
		if(tuser!=null){
			//该邮箱的用户曾经做过题，执行更新操作
			return tuser.getTuid(); 
		}else{
			testuserDao.insertTestuser(testuser);
			return testuserDao.findTuserByEmail(testuser.getEmail()).getTuid();
		}
				
	}
	


	public  List<TuserProblem> initialProblems(int testid, int tuid,int inviteId) {
		// TODO Auto-generated method stub
		//若对同一个用户发送了同一套测试，则试题不能插入，必须更新	
				List<Problem> listProblem = problemDao.getProblemByTestid(testid);
				Set idSet = new HashSet<Integer>();
				for(Problem problem:listProblem){
					//要将试题中删除了的试题从用户需要做的试题中删除
					idSet.add(problem.getProblemId());
					TuserProblem tuserProblem = tuserProblemDao.findByPidAndIid(inviteId, problem.getProblemId());
					if(tuserProblem == null){
						tuserProblem = new TuserProblem();
						tuserProblem.setInviteId(inviteId);
						tuserProblem.setProblemid(problem.getProblemId());
						tuserProblem.setTuid(tuid);
						tuserProblemDao.insertTuserProblem(tuserProblem); 
					}else{
						tuserProblem.setUseranswer("");
						tuserProblemDao.updateProblemByIds(tuserProblem); 
					}
				}
		
		
		
		List<TuserProblem> tProblems =  tuserProblemDao.findProblemByInviteId(inviteId);
		//若该用户试题已被删除
				for(TuserProblem tProblem:tProblems){
					if(idSet.contains(tProblem.getProblemid())==false){
						tuserProblemDao.deleteByIds(inviteId,tProblem.getProblemid());
					}
				}
		return tProblems;
	}

	


	//统计试题
	public Map getTestInfo(int testid) {
		// TODO Auto-generated method stub
		List<Problem> problems = problemDao.getProblemByTestid(testid);
		int option = 0;
		int essay = 0;
		int program = 0;
		for(Problem problem: problems){
			if(problem.getType() == ExamConstant.OPTION){
				option++;
			}else if(problem.getType() == ExamConstant.ESSAY){
				essay++;
			}else if(problem.getType() == ExamConstant.PROGRAM){
				program++;
			}
		}
		Map rtMap = new HashMap<String, Integer>();
		rtMap.put("optionNum", option);
		rtMap.put("essayNum", essay);
		rtMap.put("programNum", program);
		rtMap.put("testid",testid);
		
		return rtMap;
	}


	/*
	 * 清除试题，将试题的useranswer置空
	 * */
	public List<TuserProblem> clearProblems(Integer iid) {
		// TODO Auto-generated method stub
		tuserProblemDao.clearProblem(iid);
		return tuserProblemDao.findProblemByInviteId(iid);
	}

/**
 * 获取客户端登录邮箱
 */

	public Testuser findTuserByEmail(String email) { 
		  return testuserDao.findTuserByEmail(email);

	}


	public Testuser getTestuserById(int tuid) {
		return testuserDao.findTestuserById(tuid);
	}
	
	

public Testuser userLogin(int tuid) {
		testuserDao.updateLoginDateByUid(tuid);
		return getTestuserById(tuid);
	
}



public void updateTestuser(Testuser test1) {
	testuserDao.updateTestuser(test1);
}



/**
 * 获取客户端登录用户密码
 * @param pwd
 * @return
 */


	

	
	
}
