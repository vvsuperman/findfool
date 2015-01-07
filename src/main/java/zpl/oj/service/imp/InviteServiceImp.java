package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
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
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;

@Service
public class InviteServiceImp implements InviteService {

	@Autowired 
	private InviteDao inviteDao;
	@Autowired 
	private TuserService testuserService;	
	
	@Autowired
	private DESService desService;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	
	/*
	 * 生成testuser
	 * 生成invite
	 * 目前只支持单sheet的excel，使用inviteuser方便以后扩展
	 * */
	@Override
	public String inviteUserToQuiz(InviteUser u, Quiz q, String duration) {
		//生成testuser
		Testuser tuser = new Testuser();
		tuser.setUsername(u.getUsername());
		tuser.setEmail(u.getEmail());
		
		//等级
		//设置密码,5位的
		String pwd = RandomCode.randomString(5);
		tuser.setPwd(pwd);
		int tuid = testuserService.updateUser(tuser);
		
		Invite invite = new Invite();
		//如果对同一个地址发送了同一份试题，则更新invite
		Invite oldInvite = inviteDao.getInvites(q.getQuizid(), u.getEmail());
		if(oldInvite!=null){
			invite = oldInvite;
		}
		
		
		invite.setTestid(q.getQuizid());
		invite.setHrid(q.getOwner());
		invite.setUid(tuid);
		invite.setScore(0);
		invite.setState(ExamConstant.INVITE_PUB);
		//邀请生成时间
		invite.setInvitetime(df.format(new Date()));
		if(duration!=null&&duration.equals("")==false){
			invite.setDuration(duration);
		}else{
			invite.setDuration(q.getTime().toString());
		}
		
		
	
		if(invite.getIid()!=0){
			inviteDao.updateInvite(invite);
		}else{
			inviteDao.insertInvite(invite);
		}
		return pwd;
	}
	
	/**
	 * @param request
	 * @param q
	 * @param tu
	 * @param pwd
	 * 应使用线程，发送邮件耗时太久
	 */
	public void sendmail(RequestTestInviteUser request, Quiz q, InviteUser tu,
			String pwd, User hrUser) {
		String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
		String url = desService.encode(tu.getEmail()+"|"+q.getQuizid());
		final MailSenderInfo mailSenderInfo = new MailSenderInfo();
		mailSenderInfo.setMailServerHost("smtp.163.com");   
		mailSenderInfo.setMailServerPort("25");
		mailSenderInfo.setUserName("weifangscs@163.com");   
		mailSenderInfo.setPassword("fw820721");//您的邮箱密码  
		
		mailSenderInfo.setFromAddress("weifangscs@163.com");
		mailSenderInfo.setToAddress(tu.getEmail());
		mailSenderInfo.setReplyToAddress(request.getReplyTo());
		mailSenderInfo.setSubject(request.getSubject());
		String context = request.getContext();
		context += "<p>这是来自findfool的邮件，您收到"+hrUser.getCompany()+"公司的测试邀请，请登录到"
				+"<a>"+baseurl+"/#/testing/"+url+"</a>"
				+ "<br/>您的登录账号为：" + tu.getEmail() + " <br/>您的密码为：" + pwd
				+ "<br/>您的测试时间为：" + q.getTime()+"</p>";
		mailSenderInfo.setContent(context);
		
		//使用线程，避免长时间等候
		new Thread(new  Runnable() {
			public void run() {
				try {
					SimpleMailSender.sendHtmlMail(mailSenderInfo);
				} catch (Exception e) {
					e.printStackTrace();    
				}
			}
		}).start();
		
	}

}

