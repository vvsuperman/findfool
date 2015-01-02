package zpl.oj.service.solution.inter;

import java.util.List;

import zpl.oj.model.solution.ResultInfo;

public interface ResultInfoService {

	public List<ResultInfo> getResultInfoBySolutionId(int solutionId);
}
