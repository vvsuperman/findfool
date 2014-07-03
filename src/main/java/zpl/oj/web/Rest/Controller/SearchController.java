package zpl.oj.web.Rest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.requestjson.RequestSearch;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseSearchResult;
import zpl.oj.service.ProblemService;

@Controller
@RequestMapping("/search") 
public class SearchController {

	@Autowired
	private ProblemService problemService;
	@RequestMapping(value="/site")
	@ResponseBody
	public ResponseBase searchSite(@RequestBody RequestSearch request){
		ResponseBase rb = new ResponseBase();
		request.setBelong(0);
		ResponseSearchResult res = problemService.getQuestionByTag(request);
		rb.setState(1);
		rb.setMessage(res);
		return rb;
	}
	
	@RequestMapping(value="/my")
	@ResponseBody
	public ResponseBase searchMy(@RequestBody RequestSearch request){
		ResponseBase rb = new ResponseBase();
		request.setBelong(request.getUser().getUid());
		ResponseSearchResult res = problemService.getQuestionByTag(request);
		rb.setState(1);
		rb.setMessage(res);
		return rb;
	}
	
}
