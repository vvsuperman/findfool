package zpl.oj.service.security.inter;

import java.util.List;

import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;

public interface SecurityService {

	//得到权限等级
	List<Integer> getLevel(String uri);
	
	//计算token
	String computeToken(User u);
	
	//计算token
	String computeToken(Testuser u);
	
	//check token
	boolean checkToken(String uri,String token);
}
