package zpl.oj.service.security.inter;

import zpl.oj.model.common.User;

public interface SecurityService {

	//得到权限等级
	int getLevel(String uri);
	
	//计算token
	String computeToken(User u);
	
	//check token
	boolean checkToken(String token);
}
