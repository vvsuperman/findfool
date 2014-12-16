package zpl.oj.web.Rest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.requestjson.RequestSearch;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseSearchResult;
import zpl.oj.service.ProblemService;
import zpl.oj.service.SetService;

@Controller
@RequestMapping("/search") 
public class SearchController {

	@Autowired
	private ProblemService problemService;
	@Autowired
	private SetService setService;

	@RequestMapping(value="/sets")
	@ResponseBody
	public ResponseBase searchSite(@RequestBody RequestUser request){
		ResponseBase rb = new ResponseBase();
		List<ProblemSet> sets = setService.getSets();
		rb.setState(1);
		rb.setMessage(sets);
		return rb;
	}
	
	@RequestMapping(value="/site")
	@ResponseBody
	public ResponseBase searchSite(@RequestBody RequestSearch request){
		ResponseBase rb = new ResponseBase();
		request.setBelong(0);
		ResponseSearchResult res = null;
		if(request.getKeyword() == null || "".equals(request.getKeyword())){
			res = problemService.getQuestionBySetId(request);
		}else{
			res = problemService.getQuestionByTag(request);
		}
		rb.setState(1);
		rb.setMessage(res);
		return rb;
	}
	
	@RequestMapping(value="/my")
	@ResponseBody
	public ResponseBase searchMy(@RequestBody RequestSearch request){
		ResponseBase rb = new ResponseBase();
		request.setBelong(request.getUser().getUid());
		ResponseSearchResult res = null;
		if(request.getKeyword() == null || "".equals(request.getKeyword())){
			res = problemService.getQuestionByUser(request);
		}else{
			res = problemService.getQuestionByTag(request);
		}
		rb.setState(1);
		rb.setMessage(res);
		return rb;
	}
	
}
