package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.LeftMessageDao;
import zpl.oj.model.requestjson.RequestMessage;
import zpl.oj.service.LeftMessageService;

@Service
public class LeftMessageServiceImp implements LeftMessageService {

	@Autowired
	private LeftMessageDao leftMessageDao;
	@Override
	public void addLeftMessage(RequestMessage msg) {
		// TODO Auto-generated method stub
		leftMessageDao.addLeftMessage(msg);
	}

	@Override
	public List<RequestMessage> getLeftMessages() {
		return leftMessageDao.getAllLeftMessage();
	}

}
