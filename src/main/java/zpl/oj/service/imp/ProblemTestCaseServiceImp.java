package zpl.oj.service.imp;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.service.ProblemTestCaseService;

@Service
public class ProblemTestCaseServiceImp implements ProblemTestCaseService {

	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;

	@Override
	public void addProblemTestCase(ProblemTestCase ptc) {

		problemTestCaseDao.insertProblemTestCase(ptc);
	}

	@Override
	public List<ProblemTestCase> getProblemTestCases(int problemId) {
		return problemTestCaseDao.getProblemTestCases(problemId);
	}
	

}
