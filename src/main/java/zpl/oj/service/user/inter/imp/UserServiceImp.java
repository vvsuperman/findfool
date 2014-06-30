package zpl.oj.service.user.inter.imp;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.User;
import zpl.oj.service.user.inter.UserService;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserDao userDao;
	@Override
	public boolean addUser(User u) {
		//检查邮箱是否存在，
		User dbU = userDao.getUserIdByEmail(u.getEmail());
		if(dbU != null){
			if(dbU.getPrivilege() <2){
				//用户原来接受过测试，如今当hr了
				dbU.setPrivilege(u.getPrivilege());
				dbU.setPwd(u.getPwd());
				dbU.setFname(u.getFname());
				updateUser(dbU);
			}
			else{
				//用户已存在
				return false;				
			}
		}else{
			userDao.addUser(u);			
		}
		return true;
	}

	@Override
	public boolean deleteUserById(int uid) {
		userDao.deleteUserbyId(uid);
		return true;
	}

	@Override
	public boolean deleteUserByEmail(String email) {
		userDao.deleteUserbyEmail(email);
		return true;
	}

	@Override
	public User getUserById(int uid) {
		return userDao.getUserIdByUid(uid);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserIdByEmail(email);
	}

	@Override
	public boolean updateUser(User u) {
		userDao.updateUser(u);
		return true;
	}

	@Override
	public User userLogin(int uid) {
		userDao.updateLoginDateByUid(uid);
		return getUserById(uid);
	}

}
