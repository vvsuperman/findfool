package zpl.oj.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.util.mail.MailSenderInfo;

public interface InviteService {

	
	public void sendmail(RequestTestInviteUser request, Quiz q, InviteUser tu,
			String pwd,User hrUser) throws UnsupportedEncodingException, ClientProtocolException, IOException;
	
	public MailSenderInfo initialEmail();

	String inviteUserToQuiz(InviteUser u, Quiz q,RequestTestInviteUser requestUser, User user);
	
}
