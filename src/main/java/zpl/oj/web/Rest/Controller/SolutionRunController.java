package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.requestjson.RequestSolution;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
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
	
	@RequestMapping(value="/submit")
	@ResponseBody
	public ResponseBase submitSolution(@RequestBody RequestSolution request){
		ResponseMessage msg = new ResponseMessage();
		ResponseBase rs = new ResponseBase();
		
		
		msg.setMsg("ok");
		rs.setMessage(msg);
		rs.setState(1);
		return rs;
	}
	@RequestMapping(value="/run")
	@ResponseBody
	public ResponseBase getSolutions(@RequestBody ReciveSolution request){
		ResponseBase rb = new ResponseBase();
		ResponseMessage msg = new ResponseMessage();
		int solution_id = solutionRunService.addSolutionRun(request);
		msg.setMsg(""+solution_id);
		rb.setMessage(msg);
		rb.setState(1);
		return rb;
	}
	
	
	@RequestMapping(value="/query")
	@ResponseBody
	public ResponseBase getResult(@RequestBody ReciveOK request){
		ResponseBase rb = new ResponseBase();
		List<ResultInfo> rs = new ArrayList<>();
		int solutionId = request.getSolution_id();
		rs = resultInfoService.getResultInfoBySolutionId(solutionId);
		rb.setMessage(rs);
		return rb;
	}

}
