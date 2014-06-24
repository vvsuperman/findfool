package zpl.oj.service.solution.inter.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.SolutionRunDao;
import zpl.oj.dao.UserTestCaseDao;
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
	

}
