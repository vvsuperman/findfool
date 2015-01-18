package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.requestjson.RequestSolution;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.solution.ReciveOK;
import zpl.oj.model.solution.ReciveSolution;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.service.solution.inter.ResultInfoService;
import zpl.oj.service.solution.inter.SolutionRunService;
import zpl.oj.util.Constant.ExamConstant;

@Controller
@RequestMapping("/solution") 
public class SolutionRunController {
	
	@Autowired
	private SolutionRunService solutionRunService;
	@Autowired
	private ResultInfoService resultInfoService;
	@Autowired
	private TuserProblemDao tuserProblemDao;
	@Autowired
	private InviteDao inviteDao;
	
	public Map validateUser(String email , int testId) {
		Map rtMap = new HashMap<String, Object>();
		// 需做合法性判断，异常等等
		
		Invite invite = inviteDao.getInvites(testId, email);

		// 用户邀请不合法
		if (invite == null) {
			rtMap.put("msg", "非法用户");
			return rtMap;
		} else if (invite.getState() == ExamConstant.INVITE_FINISH) {
			rtMap.put("msg", "1");
			return rtMap;
		}

		int tuid = invite.getUid();
		rtMap.put("tuid", tuid);
		rtMap.put("invite", invite);
		return rtMap;
	}
	

	@RequestMapping(value="/run")
	@ResponseBody
	public ResponseBase getSolutions(@RequestBody ReciveSolution request){
		ResponseBase rb = new ResponseBase();
		String email = request.getEmail();
		int testId = request.getTestid();
		
		Map map = validateUser(email,testId);
		Integer tuid = (Integer) map.get("tuid");
		if (tuid == null) {
			rb.setMessage(map.get("msg"));
			rb.setState(0);
			return rb;
		}
		//submit＝＝0表示为运行状态，并不执行测试用例
		int problemId =request.getProblem_id();
		if(request.getSubmit() == 0){
			request.setProblem_id(0);
		}
		
		ResponseMessage msg = new ResponseMessage();
		int solution_id = solutionRunService.addSolutionRun(request);
		
		//将solution_id加入到test_user_problem
	    Invite invite = (Invite)map.get("invite");
	    //将solutionid更新到problem
		tuserProblemDao.updateSolutionByIids(invite.getIid(),problemId,solution_id);
	    
		
		msg.setMsg(""+solution_id);
		rb.setMessage(msg);
		rb.setState(1);	
		return rb;
	}
	
	
	@RequestMapping(value="/query")
	@ResponseBody
	public ResponseBase getResult(@RequestBody ReciveOK request){
		ResponseBase rb = new ResponseBase();
		List<ResultInfo> rs = new ArrayList<ResultInfo>();
		int solutionId = request.getSolution_id();
		rs = resultInfoService.getResultInfoBySolutionId(solutionId);
		
		
		rb.setMessage(rs);
		return rb;
	}

}
