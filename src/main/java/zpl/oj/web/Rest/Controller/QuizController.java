package zpl.oj.web.Rest.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.foolrank.model.CompanyModel;
import com.squareup.okhttp.Request;
import com.foolrank.util.RequestUtil;

import zpl.oj.dao.QuizDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.Labeltest;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.QuizTemplete;
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
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.InviteService;
import zpl.oj.service.LabelService;
import zpl.oj.service.QuizService;
import zpl.oj.service.imp.CompanyService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.base64.BASE64;
import zpl.oj.util.StringUtil;

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
	private LabelService labelService;
	@Autowired
	private QuizDao quizDao;

	@Autowired
	private ImgUploadService imgUploadService;
	
	@Autowired
	private CompanyService companyService;
	

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

	@RequestMapping(value = "/show", method = RequestMethod.POST)
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
		// 获取系统标签，并在labeltest中为该测试添加这些系统标签

		List<Label> labels = labelService.getSystemLabels();
		for (Label label : labels) {
			labelService.insertIntoLabelTest(q.getQuizid(), label.getId(),
					label.getIsSelected());
		}
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
	
	
	// 获得通用设置的回显数据
		@RequestMapping(value = "/getconfig")
		@ResponseBody
		public ResponseBase getconfig(@RequestBody Map<String, String> params) {
			ResponseBase rb = new ResponseBase();
			ResponseMessage msg = new ResponseMessage();
			String testid=params.get("testid");
			if(testid==null){
				msg.setMsg("当前试卷不存在，您可能未登录");
				rb.setState(1);
				rb.setMessage(msg);
				return rb;		
			}
			int tid=Integer.parseInt(testid);
			 Quiz quiz  =quizDao.getQuiz(tid);
			 if((quiz.getLogo()!=null)&&(quiz.getLogo().length()>0)){
			String logoString= companyService.getImg(quiz.getLogo());
			 quiz.setLogo(logoString);
			 }
			rb.setMessage(quiz);
			rb.setState(0);
		
			return rb;
		}
	
	
	
	

	// 保存通用设置中的开始时间和结束时间及摄像头是否必须开启
	@RequestMapping(value = "/saveTime")
	@ResponseBody
	public ResponseBase saveTime(@RequestBody Map<String, String> params) {
		ResponseBase rb = new ResponseBase();
		String openCamera = params.get("openCamera");
		
		String squizid = params.get("quizid");
		String durations = params.get("duration");
		ResponseMessage msg = new ResponseMessage();
		if(squizid==null){
			msg.setMsg("当前试卷不存在，您可能未登录");
			rb.setState(1);
			rb.setMessage(msg);
			return rb;
			
		}		
		if(openCamera==null){
			msg.setMsg("开启摄像头异常，请刷新！");
			rb.setState(2);
			rb.setMessage(msg);
			return rb;
		}
		
		
		if (durations == null) {
			msg.setMsg("考试时间不能为空！");
			rb.setState(3);
			rb.setMessage(msg);
			return rb;
		}
		
		int duration = Integer.parseInt(durations);
		int quizid = Integer.parseInt(squizid);
		Quiz quiz = quizDao.getQuiz(quizid);
		quiz.setTime(duration);
		quiz.setOpenCamera(Integer.parseInt(openCamera));
		quizDao.updateQuiz(quiz);
		
		msg.setMsg("update seccuss!!");
		rb.setState(0);
		rb.setMessage(msg);
		return rb;
	}

	// 插入竞赛图片或
	@RequestMapping(value = "/uploadimg")
	@ResponseBody
	public ResponseBase uploadCompanyImg(
			@RequestParam MultipartFile[] file,
			// @RequestBody Map<String, String> params,
			@RequestHeader(value = "Authorization", required = false) String imgifo) {

		ResponseBase rb = new ResponseBase();

		int quizId = Integer.parseInt(imgifo);
		Quiz quiz = quizDao.getQuiz(quizId);

		if (quiz == null) {
			rb.setState(1);
			rb.setMessage("图片插入错误，无法找到该公司");
			return rb;
		}

		if (file == null) {
			rb.setState(2);
			rb.setMessage("图片不可为空");
			return rb;
		}

		for (MultipartFile fileitem : file) {
			if (!fileitem.isEmpty()) {
				imgUploadService.saveTestImg(quiz, fileitem);
			}

		}
		rb.setMessage(quiz);
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
			// by fangwei 重写发送邀请逻辑，新建testusr表
			for (InviteUser tu : request.getInvite()) {
				// 由inviteuser生成testuser

				Invite oldInvite = inviteService.getInvites(q.getQuizid(),
						tu.getEmail());

				// 生成invite、testuser
				String pwd = inviteService.inviteUserToQuiz(tu, q, request, ht);
				List<Labeltest> labeltests = labelService.getLabelsOfTest(q
						.getQuizid());
				for (Labeltest lt : labeltests) {
					Invite invite = inviteService.getInvites(q.getQuizid(),
							tu.getEmail());
					if (labelService.getLabelUserByIidAndLid(invite.getIid(),
							lt.getLabelid()) == null) {
						labelService.insertIntoLabelUser(invite.getIid(),
								lt.getLabelid(), "");
					}

				}

				try {
					inviteService.sendmail(request, q, tu, pwd, ht);
				} catch (IOException e) {
					// TODO Auto-generated catch block
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

	@RequestMapping(value = "/addquestion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase addQuestion(@RequestBody QuizProblem quizProblem
	// @RequestParam(value="quizId", required=true) String quizId,
	// @RequestParam(value="questionId", required=true) Integer questionId
	) {
		ResponseBase rb = new ResponseBase();
		String msg = quizService.addQuestionToQuiz(quizProblem);
		if (msg != null) {
			rb.setState(1);
			rb.setMessage(msg);
		} else {
			rb.setState(0);
			rb.setMessage("success");
		}
		return rb;
	}

	@RequestMapping(value = "/delquestion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase deleteQuestionFromTest(
			@RequestBody QuizProblem quizProblem) {
		ResponseBase rb = new ResponseBase();
		quizService.deleteQuestionFromTest(quizProblem);
		rb.setState(1);
		return rb;
	}

	// 根据模板生成试题
	@RequestMapping(value = "/genquiz", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase genQuizFromTemplete(
			@RequestBody Map<String, String> param,
			@RequestHeader(value = "Authorization", required = true) String token)
			throws Exception {
		// 获取用户id
		int uid = -1;
		String regEx = "@,@,@,@";
		if (token != null) {
			String tokenUid = new String(BASE64.decodeBASE64(token));
			Pattern pat = Pattern.compile(regEx);
			String[] strs = pat.split(tokenUid);
			if (strs.length > 2)
				return null;
			uid = Integer.parseInt(strs[1]);
		}

		String quizName = param.get("quizName");
		int quizId = quizService.genQuiz(quizName, uid);
		// 获取系统标签，并在labeltest中为该测试添加这些系统标签

		// 获取系统标签，并在labeltest中为该测试添加这些系统标签
		List<Label> labels = labelService.getSystemLabels();
		for (Label label : labels) {
			labelService.insertIntoLabelTest(quizId, label.getId(),
					label.getIsSelected());
		}

		return null;

	}

	// 根据模板名获取id
	@RequestMapping(value = "/gettemp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase getTempIdByName(@RequestBody Map<String, String> param)
			throws Exception {
		// 获取用户id
		ResponseBase rb = new ResponseBase();

		String quizName = param.get("quizName");
		if (quizName == null) {
			rb.setState(1);
			rb.setMessage("试题名不得为空");
			return rb;
		}

		QuizTemplete quizT = quizDao.getQuizTByName(quizName);
		if (quizT == null) {
			rb.setState(2);
			rb.setMessage("试题模板为空");
			return rb;
		}
     	rb.setState(0);
		rb.setMessage(quizT.getQuizId());
		return rb;

	}

	// 设置公开挑战赛，并生成url
	@RequestMapping(value = "/setpub", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase setPub(@RequestBody Map<String, String> param) {
		ResponseBase rb = new ResponseBase();

		if (param.get("testid") == null || param.get("publicFlag") == null) {
			rb.setState(2);
			rb.setMessage("设置异常，id不可为空");
			return rb;
		}
		String testid = param.get("testid");
		String publicFlag = param.get("publicFlag");	
		Quiz quiz = quizDao.getQuiz(Integer.parseInt(testid));	
		String signedKey = "";
		
		if (publicFlag.equals("0")) {
			
			signedKey = MD5Util.stringMD5(testid
					+ StringUtil.toDateTimeString(new Date()));
			quiz.setSignedKey(signedKey);
			quiz.setType(ExamConstant.QUIZ_TYPE_CHALLENGE);
			quizDao.updateQuiz(quiz);
			rb.setState(0);
			rb.setMessage(signedKey);
			return rb;
		}else if(publicFlag.equals("1")){
            quiz.setSignedKey("");
            quiz.setType(ExamConstant.QUIZ_TYPE_PRIVATE);
            quizDao.updateQuiz(quiz);
            rb.setMessage("");
            rb.setState(0);
      		return rb;   
	
		}
		
		return null;
		
		
	}
	
	

	
	// 保存公开挑战赛信息
	@RequestMapping(value = "/setPublicConfig", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase setPublicConfig(@RequestBody Map<String, String> param) {
		ResponseBase rb = new ResponseBase();

		if (param.get("testid") == null ||param.get("testTail") == null || param.get("starttime") == null || param.get("deadtime") == null) {
			rb.setState(2);
			rb.setMessage("输入均不可为空");
			return rb;
		}

		String testid = param.get("testid");
		String testTail = param.get("testTail");
		String startTime = param.get("starttime");
		String deadTime = param.get("deadtime");
		
		Quiz quiz = quizDao.getQuiz(Integer.parseInt(testid));
		if (testTail == null || quiz.getLogo() == null) {
			rb.setState(3);
			rb.setMessage("竞赛logo和竞赛详情不能为空");
			return rb;
		}
	
		quiz.setDescription(testTail);
		quiz.setStartTime(startTime);
		quiz.setEndTime(deadTime);

		quizDao.updateQuiz(quiz);
		rb.setState(0);
		return rb;
	}
	
	

	// 判断是否有公开挑战赛
	@RequestMapping(value = "/checkpub", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase checkPub(@RequestBody Map<String, Object> param) {
		ResponseBase rb = new ResponseBase();
		String testid = (String) param.get("testid");
		if (testid == null) {
			rb.setState(1);
			rb.setMessage("testid不可为空");
			return rb;
		}
		Quiz quiz = quizDao.getQuiz(Integer.parseInt(testid));

		rb.setState(0);
		rb.setMessage(quiz.getSignedKey());
		return rb;

	}


}