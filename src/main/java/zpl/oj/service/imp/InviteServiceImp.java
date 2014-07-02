package zpl.oj.service.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
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
	private UserService userService;
	
	@Override
	public String inviteUserToQuiz(User u, Quiz q) {
		
		//等级
		u.setPrivilege(1);
		//设置密码,5位的
		String pwd = RandomCode.randomString(5);
		u.setPwd(MD5Util.stringMD5(pwd));
		boolean res = userService.addUser(u);
		if(res == false)
			pwd = null;
		u = userService.getUserByEmail(u.getEmail());
		
		Invite invite = new Invite();
		invite.setTestid(q.getQuizid());
		invite.setHrid(q.getOwner());
		invite.setUid(u.getUid());
		invite.setScore("0/0");
		invite.setInvitetime(new Date());
		invite.setFinishtime(new Date());
		inviteDao.insertInvite(invite);
		
		return pwd;
	}

}
