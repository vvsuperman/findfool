package zpl.oj.service;

import zpl.oj.model.common.VerifyQuestion;

public interface VerifyQuestionService {
	VerifyQuestion getVerifyQuestion(int index);
	int getVerifyQuestionCount();
}
