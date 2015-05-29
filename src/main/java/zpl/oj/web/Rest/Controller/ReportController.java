package zpl.oj.web.Rest.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.responsejson.ResponseInvite;
import zpl.oj.model.responsejson.ResponseProDetail;
import zpl.oj.service.InviteService;
import zpl.oj.service.QuizService;
import zpl.oj.service.imp.ReportService;
import zpl.oj.service.user.inter.UserService;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private QuizService quizService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviteService inviteService;
	@Autowired
	private ReportService reportService;
	
	
	
	/*
	 * 返回总体的报告
	 * 用户的得分、用户排名、及雷达图
	 * */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/overall")
	@ResponseBody
	public Map getOverall(@RequestBody Invite invite) {
		Map rtMap = new HashMap<String,Object>();
		rtMap.put("score", reportService.getInviteScore(invite)); 
		rtMap.put("rank", reportService.getRank(invite));
		rtMap.put("dimension", reportService.getDimension(invite).get("setRadar"));
		rtMap.put("user", reportService.getUserById(invite.getUid()));
		rtMap.put("levelDimension", reportService.getDimension(invite).get("levelRadar"));
		rtMap.put("imgs", reportService.getUserPhotos(invite));
		return rtMap;
	}
	
	/*
	 * 返回用户报告细节
	 * */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public List getDetail(@RequestBody Invite invite) {
		return reportService.getDetail(invite);
	}
	
	/*
	 * 返回测试下已完成的试题
	 * */
	@RequestMapping(value = "/list")
	@ResponseBody
	public List<ResponseInvite> getReportList(@RequestBody Map<String,Integer> map) {
		int testid = map.get("testid");
		return reportService.getInviteReport(testid);
	}
	
	/*
	 * 获取编程题的测试详情
	 * */
	@RequestMapping(value = "/getprodetail")
	@ResponseBody
	public ResponseProDetail getProDetail(@RequestBody  TuserProblem tProblem) {
		return reportService.getProDetail(tProblem);
	}
	
	

}
