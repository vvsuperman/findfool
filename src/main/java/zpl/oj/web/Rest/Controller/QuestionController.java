package zpl.oj.web.Rest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.request.Question;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestAddQuestion;
import zpl.oj.model.requestjson.RequestQuestion;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.service.ProblemService;
import zpl.oj.service.security.inter.SecurityService;

@Controller
@RequestMapping("/question") 
public class QuestionController {
	
	@Autowired
	private ProblemService problemService;
	@Autowired
	private SecurityService securityService;
	
	
	@RequestMapping(value="/query")
	@ResponseBody
	public ResponseBase queryQuestionById(@RequestBody RequestQuestion request){
		ResponseBase rb = new ResponseBase();
		
		
		Question p = problemService.getProblemById(request.getQid());
		
		if(p == null){
			ResponseMessage msg = new ResponseMessage();
			msg.setMsg("no such question!!!");
			msg.setHandler_url("/error");
			rb.setState(0);		
			rb.setMessage(msg);
		}else{
			rb.setMessage(p);
			rb.setState(1);	
		}
		return rb;
	}
	
	
	@RequestMapping(value="/add")
	@ResponseBody
	public ResponseBase add(@RequestBody RequestAddQuestion request){
		ResponseBase rb = new ResponseBase();
		problemService.addProblem(request);
		User u = new User();

		ResponseMessage msg = new ResponseMessage();
		
		return rb;
	}
}
