package zpl.oj.service.solution.inter;

import java.util.List;

import zpl.oj.model.requestjson.RequestSolution;
import zpl.oj.model.solution.ReciveSolution;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.model.solution.SolutionRun;

public interface SolutionRunService {

	public int submitSolution(RequestSolution request);
	
	public int addSolutionRun(ReciveSolution sr);
	
	public int getSolutionRunID(SolutionRun sr);
	
	public List<ResultInfo> getResultInfoBySolutionId(int solution_id);
	
}
