package zpl.oj.web.Rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.solution.RunTaskResult;

@Controller
@RequestMapping("/rest") 
public class RestController {

//	@Autowired
//	private UserTaskService userTaskService;
//	//璐熻矗寰楀埌浠诲姟
//	@RequestMapping(value="/task",method = RequestMethod.GET)
//	@ResponseBody
//	public ShowUserTask getTask(HttpServletRequest request){
//		//int taskid = Integer.parseInt(request.getParameter("taskid"));
//		ShowUserTask u = userTaskService.getUserTaskByUserAndTask(1, 1);
//		return u;
//	}
	
	//璐熻矗澶勭悊杩欎釜鎻愪氦鐨勪唬鐮�
	@RequestMapping(value="/run")
	@ResponseBody
	public RunTaskResult getSolutions(@RequestBody RunTaskResult request){
		String solution = request.getMessage();
		RunTaskResult rs = new RunTaskResult();
		rs.setTaskid(2);
		rs.setResult("ok");
		rs.setMessage("you won");
		return rs;
	}
}
