package zpl.oj.service;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.util.mail.MailSenderInfo;

public interface InviteService {

	public String inviteUserToQuiz(InviteUser u,Quiz q,String duration);
	
	public void sendmail(RequestTestInviteUser request, Quiz q, InviteUser tu,
			String pwd,User hrUser);
	
	public MailSenderInfo initialEmail();
	
}
