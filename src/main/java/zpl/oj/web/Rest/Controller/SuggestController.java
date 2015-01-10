package zpl.oj.web.Rest.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.SuggestDao;
import zpl.oj.model.common.Suggest;
import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestChangeUserInfo;
import zpl.oj.model.requestjson.RequestSolution;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.model.requestjson.RequestUserLogin;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.model.responsejson.ResponseUserInfo;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.solution.inter.ResultInfoService;
import zpl.oj.service.solution.inter.SolutionRunService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;

@Controller
@RequestMapping("/suggest") 
public class SuggestController {

	@Autowired
	private SuggestDao suggestDao;
	
	@RequestMapping(value="/add")
	@ResponseBody
	public ResponseBase submitSolution(@RequestBody Suggest suggest){
		
		ResponseBase rs = new ResponseBase();
		String content = suggest.getContent();
		if(content.length()>200){
			rs.setState(1);
			return rs;
		}
		
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    suggest.setSeggestTime(df.format(new Date()));
		suggestDao.insertSuggest(suggest);
		rs.setState(1);
		return rs;
	}
}
