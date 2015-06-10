package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.util.mail.MailSenderInfo;

public interface InviteService {

	public String inviteUserToQuiz(InviteUser u,Quiz q,String duration);

	//通过testid获得所有的invite
	public List<Invite> getInvitesByTid(int testid);
	
	public Invite getInvites(int testid,String email);
	
	public void sendmail(RequestTestInviteUser request, Quiz q, InviteUser tu,
			String pwd,User hrUser);
	
	public MailSenderInfo initialEmail();
	
}
