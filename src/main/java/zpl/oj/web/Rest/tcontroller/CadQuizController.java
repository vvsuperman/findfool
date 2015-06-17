package zpl.oj.web.Rest.tcontroller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.CadTestDao;
import zpl.oj.model.common.CadTest;
import zpl.oj.model.request.Question;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.imp.CadQuizService;
import zpl.oj.util.Constant.ExamConstant;

@Controller
@RequestMapping("/cadquiz")
public class CadQuizController {
	
	
	
	
	@Autowired
	private CadQuizService cadQuizService;
	
	@Autowired
	private CadTestDao CadTestDao;
	
	//生成试题，并取一道题
	@RequestMapping(value = "/starttest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase startTest(@RequestBody Map<String, Object> map) {
		ResponseBase rb = new ResponseBase();
		
		String email = (String)map.get("email");
		Integer testid = (Integer)map.get("testid");
		//生成ctid，及cadproblem
		CadTest cadTest = cadQuizService.startQuiz(email,testid);
		if(cadTest.getState() == ExamConstant.INVITE_FINISH){
			//试卷已做完
			rb.setState(1);
			rb.setMessage("试卷已做完");
			return rb;
		}
		
		Question question = cadQuizService.getProblem(cadTest.getCtid());
		if(question == null){
			//试卷已做完
			rb.setState(2);
			rb.setMessage("该试卷无题");
			return rb;
		}
		Map<String,Object> rtMap = new HashMap<String, Object>();
		Map cadInfo = cadQuizService.getExtraInfo(cadTest);
		rtMap.put("question",question);
		rtMap.put("cadInfo",cadInfo);
		
		rb.setState(0);
		rb.setMessage(rtMap);
		return rb;
	}
	
	
	//随机取一道题
		@RequestMapping(value = "/getquestion", method = RequestMethod.POST)
		@ResponseBody
		public ResponseBase getQuestion(@RequestBody Map<String, Object> map) {
			ResponseBase rb = new ResponseBase();
			
			String email = (String)map.get("email");
			Integer testid = (Integer)map.get("testid");
			//生产invite，及cadproblem
			int ctid = CadTestDao.getCdByIds(testid, email).getCtid();
			
			Question question = cadQuizService.getProblem(ctid);
			if(question == null){
				//试卷已做完
				rb.setState(2);
				rb.setMessage("该试卷无题");
				return rb;
			}
			rb.setState(0);
			rb.setMessage(question);
			return rb;
		}
		
		
		//答题:计算结果并返回下一题
		@RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
		@ResponseBody
		public ResponseBase answerQuestion(@RequestBody Map<String, Object> map) {
			ResponseBase rb = new ResponseBase();
			int testid = (Integer)map.get("testid");
			String email = (String)map.get("email");
			CadTest cadTest =  CadTestDao.getCdByIds(testid, email);
			int problemid =(Integer)map.get("problemid");
			String useranswer =(String)map.get("useranswer");
			Map rtMap = cadQuizService.answerQuestion(cadTest,problemid,useranswer);
			if(rtMap.get("question") == null){//试题已做完
				rb.setState(1);
			}else{
				rb.setState(0);
			}
			
			rb.setMessage(rtMap);
			
			return rb;
		}
		
		
		//返回本测试的信息：排名 
		@RequestMapping(value = "/preparetest", method = RequestMethod.POST)
		@ResponseBody
		public ResponseBase prepareTest(@RequestBody Map<String, Object> map) {
			ResponseBase rb = new ResponseBase();
			int testid =(Integer)map.get("testid");
			String email = (String)map.get("email");
			Map rtMap = cadQuizService.prepareTest(testid,email);
			rb.setState(0);
			rb.setMessage(rtMap);
			return rb;
		}
		
		

}