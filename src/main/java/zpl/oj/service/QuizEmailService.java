package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.QuizEmail;

public interface QuizEmailService {
	abstract List<QuizEmail> getEmailsByQuizId(Integer quizId);
	abstract QuizEmail getEmailByEmail(Integer quizId,String email);
	abstract void insertIntoPublicLinkEmail(Integer quizId,String email);
	abstract void sendMail(QuizEmail qe,Invite invite);
}
