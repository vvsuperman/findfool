package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;
import zpl.oj.service.InviteService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.RandomCode;

@Service
public class InviteServiceImp implements InviteService {

	@Autowired 
	private InviteDao inviteDao;
	@Autowired 
	private TuserService testuserService;	
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	@Override
	public String inviteUserToQuiz(Testuser u, Quiz q, String duration) {
		
		//等级
		//设置密码,5位的
		String pwd = RandomCode.randomString(5);
		u.setPwd(MD5Util.stringMD5(pwd));
		int tuid = testuserService.updateUser(u);
		
		Invite invite = new Invite();
		invite.setTestid(q.getQuizid());
		invite.setHrid(q.getOwner());
		invite.setUid(tuid);
		invite.setScore("0/0");
		//邀请生成时间
		invite.setInvitetime(df.format(new Date()));
		invite.setDuration(duration);
		//如果对同一个地址发送了同一份试题，则更新invite
		Invite oldInvite = inviteDao.getInvites(q.getQuizid(), u.getEmail());
		if(oldInvite!=null){
			inviteDao.updateInvite(invite);
		}else{
			inviteDao.insertInvite(invite);
		}
		return pwd;
	}

}
