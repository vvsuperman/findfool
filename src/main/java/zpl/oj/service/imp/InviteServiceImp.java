package zpl.oj.service.imp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.service.InviteService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SendCloud;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;
import zpl.oj.util.sms.SMS;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;

@Service
public class InviteServiceImp implements InviteService {

	@Autowired 
	private InviteDao inviteDao;
	@Autowired 
	private TuserService testuserService;	
	
	@Autowired
	private DESService desService;
	

	@Autowired 
	private QuizDao quizDao;
	
	
	@Autowired
	private SMS sms;
	@Autowired
	private SendCloud sendCloud;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	
	/*
	 * 生成testuser
	 * 生成invite
	 * 目前只支持单sheet的excel，使用inviteuser方便以后扩展
	 * */
	@Override
	public String inviteUserToQuiz(InviteUser u, Quiz q, RequestTestInviteUser requestUser,User user) {
		//生成testuser
		Testuser tuser = new Testuser();
		tuser.setUsername(u.getUsernameame());
		tuser.setEmail(u.getEmail());
	    tuser.setPrivilege(ExamConstant.ROLE_TEST);
		
		//发送短信提醒
	    if(u.getTel().equals("") == false ){
	    	tuser.setTel(u.getTel());
			//给应聘者发送笔试邀请
			List<String> ls = new ArrayList<String>();
			ls.add(user.getCompany());
			ls.add(u.getEmail());
			sms.send(u.getTel(), ls, ExamConstant.SMS_TEMPID_INVITE);
	    }
		
		
		//等级
		//设置密码,5位的
		String pwd = RandomCode.randomString(5);
		int tuid = testuserService.updateUser(tuser);
		
		Invite invite = new Invite();
		//如果对同一个地址发送了同一份试题，则更新invite
		Invite oldInvite = inviteDao.getInvites(q.getQuizid(), u.getEmail());
		if(oldInvite!=null){
			invite = oldInvite;
		}
		  Quiz  quiz=quizDao.getQuiz(q.getQuizid());

		invite.setPwd(pwd);
		invite.setTestid(q.getQuizid());
		invite.setHrid(q.getOwner());
		invite.setUid(tuid);
		invite.setScore(0);
		invite.setTotalScore(0);
		invite.setState(ExamConstant.INVITE_PUB);
		invite.setBegintime("");
		invite.setStarttime(requestUser.getStarttime());
		invite.setDeadtime(requestUser.getDeadtime());
		//邀请生成时间
		invite.setInvitetime(df.format(new Date()));
	    invite.setSystem(requestUser.getSystem());
		
		String duration = requestUser.getDuration();
		if(duration!=null&&duration.equals("")==false){
			invite.setDuration(duration);
		}else{
			invite.setDuration(q.getTime().toString());
		}
		invite.setOpenCamera(quiz.getOpenCamera());
		
		if(invite.getIid()!=0){
			inviteDao.updateInvite(invite);
		}else{
			inviteDao.insertInvite(invite);
		}
		return pwd;
	}

	//更换邮件
	//初始化邮件类
	public MailSenderInfo initialEmail(){
		MailSenderInfo mailSenderInfo = new MailSenderInfo();
		String host = (String) PropertiesUtil.getContextProperty("mailServerHost");
		String port = (String) PropertiesUtil.getContextProperty("mailServerPort");
		String userName = (String) PropertiesUtil.getContextProperty("userName");
		String password = (String) PropertiesUtil.getContextProperty("password");
		String fromAddress = (String) PropertiesUtil.getContextProperty("fromAddress");
		
		mailSenderInfo.setMailServerHost(host);   
		mailSenderInfo.setMailServerPort(port);
		mailSenderInfo.setUserName(userName);   
		mailSenderInfo.setPassword(password);//您的邮箱密码  
		mailSenderInfo.setFromAddress(fromAddress);
		return mailSenderInfo;
	}
	
	
	/**
	 * @param request
	 * @param q
	 * @param tu
	 * @param pwd
	 * 应使用线程，发送邮件耗时太久
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * 0605更改，改用新浪云服务批量发送邮件
	 */
	public void sendmail(RequestTestInviteUser request, Quiz q, InviteUser tu,
			String pwd, User hrUser) throws ClientProtocolException, IOException {
		String testurl = desService.encode(tu.getEmail()+"|"+q.getQuizid());
		String subject = request.getSubject();
		 String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");

	
        String content = "<p> 这是来自"+hrUser.getCompany()+"公司的邮件，我们非常荣幸能收到您的简历，做为优秀的候选人之一，我们诚挚的邀请您参加此次笔试，使我们能进一步了解您的能力。请登陆到：</p>"
        		+"<a href="+baseurl+"#/testing/"+testurl+">"+baseurl+"/#/testing/"+testurl+"</a>"
        		+" <p>完成笔试测试</p>"
        		+"<p>您的密码是："+pwd+"</p>"
        		+"<p>请务必使用火狐或Chrome等非IE浏览器打开链接</p>"
        		+"<p>感谢您的参加!";
   		
       sendCloud.sendmail(tu.getEmail(), subject, content);
      
	}
	
	
	
	

	@Override
	public Invite getInvites(int testid, String email) {
		// TODO Auto-generated method stub
		return inviteDao.getInvites(testid, email);
	}

	@Override
	public List<Invite> getInvitesByTid(int testid) {
		// TODO Auto-generated method stub
		return inviteDao.getInviteByTid(testid);
	}

	@Override
	public String inviteUserToQuiz(InviteUser u, Quiz q, String duration) {
		// TODO Auto-generated method stub
		return null;
	}

}

