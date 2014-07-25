package zpl.oj.service.solution.inter.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.ResultInfoDao;
import zpl.oj.dao.SolutionRunDao;
import zpl.oj.dao.UserTestCaseDao;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.model.requestjson.RequestSolution;
import zpl.oj.model.solution.ReciveSolution;
import zpl.oj.model.solution.ReciveTestCases;
import zpl.oj.model.solution.ResultInfo;
import zpl.oj.model.solution.SolutionRun;
import zpl.oj.model.solution.UserTestCase;
import zpl.oj.service.solution.inter.SolutionRunService;

@Service
public class SolutionRunServiceImp implements SolutionRunService{

	@Autowired
	private SolutionRunDao solutionRunDao;
	
	@Autowired
	private UserTestCaseDao userTestCaseDao;
	
	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;
	
	@Autowired
	private ResultInfoDao resultInfoDao;
	
	@Override
	/** 
     * 事务处理必需抛出异常 spring 才会帮事务回滚 
     * @param SolutionRun 
     */  
	public int addSolutionRun(ReciveSolution rv) {
		SolutionRun sr = new SolutionRun();
		sr.setUser_id(rv.getUser_id());
		sr.setLanguage(rv.getLanguage());
		sr.setSolution(rv.getSolution());
		sr.setProblem_id(rv.getProblem_id());
		sr.setType(20);
		solutionRunDao.addSolutionRun(sr);
		
		int solution_id = solutionRunDao.getSolutionRunId(sr);
		for(ReciveTestCases args:rv.getUser_test_cases()){
			if("".equals(args.getValue()) || null == args.getValue())
				continue;
			UserTestCase u = new UserTestCase(solution_id,args.getValue());
			userTestCaseDao.addUserTestCase(u);
		}
		solutionRunDao.updateSolutionState(solution_id);

		return solution_id;
	}

	@Override
	public int getSolutionRunID(SolutionRun sr) {
		// TODO Auto-generated method stub
		return solutionRunDao.getSolutionRunId(sr);
	}

	@Override
	public List<ResultInfo> getResultInfoBySolutionId(int solution_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int submitSolution(RequestSolution request) {
		SolutionRun sr = new SolutionRun();
		sr.setUser_id(request.getUser().getUid());
		sr.setLanguage(request.getLanguage());
		sr.setProblem_id(request.getQid());
		List<QuestionTestCase> solutions= request.getAnswer();
		if(solutions != null){
			if(request.getType() != 1){
				sr.setSolution(solutions.get(0).getText());
				if(request.getType() == 2){
					//编程题
					sr.setType(0);
				}else{
					sr.setType(3);
				}
				solutionRunDao.addSolutionRun(sr);
			}else{
				sr.setType(3);
				solutionRunDao.addSolutionRun(sr);
				int solution_id = solutionRunDao.getSolutionRunId(sr);
				for(QuestionTestCase qtc: solutions){
					ProblemTestCase tc =problemTestCaseDao.getProblemTestCaseById(qtc.getCaseId());
					ResultInfo res = new ResultInfo();
					res.setTest_case_id(tc.getTestCaseId());
					res.setSolution_id(solution_id);
					res.setTest_case_result(tc.getExceptedRes());
					res.setScore(tc.getScore());
					resultInfoDao.insertResultInfo(res);
				}
			}
		}	
		return 0;
	}
}
