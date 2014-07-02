package zpl.oj.service;

import zpl.oj.model.request.Question;
import zpl.oj.model.requestjson.RequestAddQuestion;

public interface ProblemService {

	//根据id查找problem
	Question getProblemById(int problemId);
	
	//增加一个problem
	int addProblem(RequestAddQuestion q);
}
