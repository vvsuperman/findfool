package zpl.oj.service;

import java.util.List;

import zpl.oj.model.requestjson.RequestMessage;

public interface LeftMessageService {
	//增加一个留言
	public void addLeftMessage(RequestMessage msg);
	
	//返回留言列表
	public List<RequestMessage> getLeftMessages();
}
