package zpl.oj.web.Rest.Controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
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
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;

@Controller
@RequestMapping("/test")
public class QuizController {

	@Autowired
	private QuizService quizService;
	@Autowired
	private UserService userService;
	@Autowired
	private InviteService inviteService;
	
	@Autowired
	private DESService desService;

	@RequestMapping(value = "/queryByID")
	@ResponseBody
	public ResponseBase queryQuizByID(@RequestBody RequestTestDetail request) {
		ResponseBase rb = new ResponseBase();

		ResponseQuizDetail rqd = quizService.getQuizDetail(request.getQuizid());
		if (null == rqd) {
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("no such quiz");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
		} else {
			rb.setMessage(rqd);
			rb.setState(1);
		}

		return rb;
	}

	@RequestMapping(value = "/queryByName")
	@ResponseBody
	public ResponseBase queryQuizByName(@RequestBody RequestTestMeta request) {
		ResponseBase rb = new ResponseBase();

		User u = userService.getUserById(request.getUser().getUid());
		if (null != u) {
			ResponseQuizDetail rq = new ResponseQuizDetail();
			Quiz quiz = quizService.getQuizMetaInfoByName(request.getName(),
					u.getUid());
			if (null == quiz) {
				ResponseMessage rm = new ResponseMessage();
				rm.setMsg("no such quiz");
				rm.setHandler_url("/error");
				rb.setMessage(rm);
				rb.setState(0);
			} else {
				rq.setQuizid(quiz.getQuizid());
				rb.setMessage(rq);
				rb.setState(1);
			}

		} else {
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("no such user");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
		}
		return rb;
	}

	@RequestMapping(value = "/show",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase queryAllTest(@RequestBody RequestUser request) {
		ResponseBase rb = new ResponseBase();

		User u = userService.getUserById(request.getUid());
		if (null != u) {
			ResponseQuizs rq = new ResponseQuizs();
			rq.setUid(u.getUid());
			rq.setInvitedNum(u.getInvitedNum());
			rq.setInviteLeft(u.getInvited_left());

			List<Quiz> lists = quizService.getQuizByOwner(u.getUid());
			rq.setTests(lists);

			rb.setMessage(rq);
			rb.setState(1);
		} else {
			ResponseMessage rm = new ResponseMessage();
			rm.setMsg("no such user");
			rm.setHandler_url("/error");
			rb.setMessage(rm);
			rb.setState(0);
		}

		return rb;
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseBase addQuizMetaInfo(@RequestBody RequestTestMeta request) {
		ResponseBase rb = new ResponseBase();

		Quiz q = quizService.addQuiz(request);
		ResponseMessage msg = new ResponseMessage();
		if (q == null) {
			msg.setMsg("add failed!!");
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setMessage(msg);
		} else {
			msg.setMsg("" + q.getQuizid());
			msg.setHandler_url("/");
			rb.setState(1);
			rb.setMessage(msg);
		}
		return rb;
	}

	@RequestMapping(value = "/manage/setting/set")
	@ResponseBody
	public ResponseBase setQuizMetaInfo(@RequestBody RequestTestMeta request) {
		ResponseBase rb = new ResponseBase();

		quizService.updateQuizMetaInfo(request);

		ResponseMessage msg = new ResponseMessage();
		msg.setMsg("update seccuss!!");
		msg.setHandler_url("/");
		rb.setState(1);
		rb.setMessage(msg);

		return rb;
	}

	@RequestMapping(value = "/manage")
	@ResponseBody
	public ResponseBase queryQuizDetailByTid(
			@RequestBody RequestTestDetail request) {
		ResponseBase rb = new ResponseBase();

		ResponseQuizDetail rqd = quizService.getQuizDetail(request.getQuizid());

		rb.setMessage(rqd);
		rb.setState(1);
		return rb;
	}

	@RequestMapping(value = "/manage/invite")
	@ResponseBody
	public ResponseBase inviteUserToQuiz(
			@RequestBody RequestTestInviteUser request) {
		ResponseBase rb = new ResponseBase();
		Quiz q = quizService.getQuizMetaInfoByID(request.getQuizid());
		User ht = userService.getUserById(request.getUser().getUid());
		ResponseMessage msg = new ResponseMessage();
		int num = request.getInvite().size();
		if (ht.getInvited_left() - num <= 0) {
			msg.setMsg("failed you must be put more money!");
			msg.setHandler_url("/");
			rb.setState(0);
		} else {
			// 发送邀请
			for (InviteUser iu : request.getInvite()) {
				User u = new User();
				u.setFname(iu.getFname());
				u.setLname(iu.getLname());
				u.setTel(iu.getTel());
				u.setEmail(iu.getEmail());

				// 邀请
				String pwd = inviteService.inviteUserToQuiz(u, q);
				if (pwd == null) {
					pwd = "请使用网站账号密码登陆";
				}
				String url = desService.encode(""+q.getQuizid());
				MailSenderInfo mailSenderInfo = new MailSenderInfo();
				mailSenderInfo.setMailServerHost("smtp.163.com");   
				mailSenderInfo.setMailServerPort("25");
				mailSenderInfo.setUserName("weifangscs@163.com");   
				mailSenderInfo.setPassword("fw820721");//您的邮箱密码  
				
				mailSenderInfo.setFromAddress("weifangscs@163.com");
				mailSenderInfo.setToAddress(u.getEmail());
				mailSenderInfo.setReplyToAddress(request.getReplyTo());
				mailSenderInfo.setSubject(request.getSubject());
				String context = request.getContext();
				context += "<p>欢迎来xxoo做测试，请登录到"+"http://localhost:8080/oj/#/mchoice/"+url+",,"
						+ "<br/>您的登录账号为：" + u.getEmail() + " <br/>您的密码为：" + pwd
						+ "<br/>您的测试时间为：" + q.getTime()+"</p>";
				mailSenderInfo.setContent(context);
				try {
					SimpleMailSender.sendHtmlMail(mailSenderInfo);
				} catch (Exception e) {
					e.printStackTrace();    
				}
			}

			ht.setInvited_left(ht.getInvited_left() - num);
			ht.setInvitedNum(ht.getInvitedNum() + num);
			userService.updateUser(ht);
			msg.setMsg("invite all ok!");
			msg.setHandler_url("/");
			rb.setState(1);
		}
		rb.setMessage(msg);
		return rb;
	}

	@RequestMapping(value = "/manage/submite")
	@ResponseBody
	public ResponseBase submitAQuiz(@RequestBody RequestTestSubmit request) {
		ResponseBase rb = new ResponseBase();
		Quiz res = quizService.updateQuiz(request.getQuizid(),
				request.getQids());

		ResponseMessage msg = new ResponseMessage();
		if (res == null) {
			msg.setMsg("update failed");
			msg.setHandler_url("/error");
			rb.setState(0);
			rb.setMessage(msg);
		} else {

			rb.setMessage(res);
			rb.setState(1);
		}
		return rb;
	}
	
	@RequestMapping(value = "/addquestion",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase addQuestion(
			@RequestBody QuizProblem quizProblem
//			@RequestParam(value="quizId", required=true)   String quizId,
//			@RequestParam(value="questionId", required=true)   Integer questionId
			) {
		ResponseBase rb = new ResponseBase();
		String msg = quizService.addQuestionToQuiz(quizProblem);
		if(msg != null){
			rb.setState(1);
			rb.setMessage(msg);
		}else{
			rb.setState(0);
			rb.setMessage("success");
		}
		return rb;
	}
	
	@RequestMapping(value = "/delquestion",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase deleteQuestionFromTest(
			@RequestBody QuizProblem quizProblem
			){
		ResponseBase rb = new ResponseBase();
		quizService.deleteQuestionFromTest(quizProblem);
		rb.setState(1);
		return rb;
	}
	

}
