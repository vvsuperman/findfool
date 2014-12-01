package zpl.oj.service;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;

public interface InviteService {

	public String inviteUserToQuiz(Testuser u,Quiz q,String duration);
}
