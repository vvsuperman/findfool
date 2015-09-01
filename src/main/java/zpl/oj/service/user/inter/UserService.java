package zpl.oj.service.user.inter;

import java.util.Map;

import zpl.oj.model.request.User;

public interface UserService {

	//增加一个用户
	boolean addUser(User u);
	
	//根据用户id来删除用户
	boolean deleteUserById(int uid);
	//根据用户email来删除用户
	boolean deleteUserByEmail(String email);
	
	//根据用户id来查用户
	User getUserById(int uid);
	//根据用户email来查用户
	User getUserByEmail(String email);
	
	//更新用户信息
	boolean updateUser(User u);
	
	//user login service 刷新登陆时间并且返回user对象
	User userLogin(int uid);

	void resetPwdApply(String email,User user);

	User getUserByName(String username);
}
