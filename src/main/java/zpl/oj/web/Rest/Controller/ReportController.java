package zpl.oj.web.Rest.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestDetail;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.model.requestjson.RequestTestMeta;
import zpl.oj.model.requestjson.RequestTestSubmit;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseQuizDetail;
import zpl.oj.model.responsejson.ResponseQuizs;
import zpl.oj.service.InviteService;
import zpl.oj.service.QuizService;
import zpl.oj.service.ScoreService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;

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
	private ScoreService scoreService;
	
	
	
	/*
	 * 返回总体的报告
	 * 用户的得分、用户排名、及雷达图
	 * */
	@RequestMapping(value = "/getoverall")
	@ResponseBody
	public Map getOverall(@RequestBody Invite invite) {
		Map rtMap = new HashMap<String,Object>();
		rtMap.put("score", scoreService.getInviteScore(invite)); 
		rtMap.put("rank", scoreService.getRank(invite));
		rtMap.put("dimension", scoreService.getDimension(invite));
		return rtMap;
	}
	
	
	/*
	 * 返回用户报告细节
	 * */
	@RequestMapping(value = "/getdetail")
	@ResponseBody
	public Map getDetail(@RequestBody Invite invite) {
		return null;
	}
	

}
