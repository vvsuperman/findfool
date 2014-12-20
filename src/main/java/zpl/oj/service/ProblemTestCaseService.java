package zpl.oj.service;


import java.util.List;

import zpl.oj.model.common.ProblemTestCase;

public interface ProblemTestCaseService {

	void addProblemTestCase(ProblemTestCase ptc);
	
	
	List<ProblemTestCase> getProblemTestCases(int problemId);
}
