package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.solution.ReciveOK;
import zpl.oj.model.solution.ReciveSolution;
import zpl.oj.model.solution.ResultInfo;
import zpl.oj.service.solution.inter.ResultInfoService;
import zpl.oj.service.solution.inter.SolutionRunService;

@Controller
@RequestMapping("/solution") 
public class SolutionRunController {
	
	@Autowired
	private SolutionRunService solutionRunService;
	@Autowired
	private ResultInfoService resultInfoService;
	@RequestMapping(value="/run")
	@ResponseBody
	public ReciveOK getSolutions(@RequestBody ReciveSolution request){

		int solution_id = solutionRunService.addSolutionRun(request);
		ReciveOK rs = new ReciveOK();
		rs.setSolution_id(solution_id);
		return rs;
	}
	
	
	@RequestMapping(value="/query")
	@ResponseBody
	public List<ResultInfo> getResult(@RequestBody ReciveOK request){
		List<ResultInfo> rs = new ArrayList<>();
		int solutionId = request.getSolution_id();
		rs = resultInfoService.getResultInfoBySolutionId(solutionId);
		return rs;
	}

}
