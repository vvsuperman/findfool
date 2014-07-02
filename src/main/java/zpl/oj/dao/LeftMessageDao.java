package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.requestjson.RequestMessage;

public interface LeftMessageDao {

	@Select("select email,name,context as msg from contactus")
	public List<RequestMessage> getAllLeftMessage();
	
	@Insert("insert contactus (email,name,context) values(#{email},#{name},#{msg})")
	public void addLeftMessage(RequestMessage msg);
}
