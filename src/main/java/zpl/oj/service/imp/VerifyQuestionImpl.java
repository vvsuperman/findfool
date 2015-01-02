package zpl.oj.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.VerifyQuestionDao;
import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.service.VerifyQuestionService;

@Service
public class VerifyQuestionImpl implements VerifyQuestionService {
	@Autowired
	private VerifyQuestionDao verifyQuestionDao;
	
	@Override
	public VerifyQuestion getVerifyQuestion(int index) {
		VerifyQuestion vq=verifyQuestionDao.getVerifyQuestion(index);
		return vq;
	}

	@Override
	public int getVerifyQuestionCount() {		
		return verifyQuestionDao.getVerifyQuestionCount();
	}

}
