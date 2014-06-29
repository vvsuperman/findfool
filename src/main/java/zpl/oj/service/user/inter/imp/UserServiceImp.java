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
	public void addUser(User u) {
		userDao.addUser(u);
	}

	@Override
	public void deleteUserById(int uid) {
		userDao.deleteUserbyId(uid);
	}

	@Override
	public void deleteUserByEmail(String email) {
		userDao.deleteUserbyEmail(email);
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
	public void updateUser(User u) {
		userDao.updateUser(u);
	}

	@Override
	public User userLogin(int uid) {
		userDao.updateLoginDateByUid(uid);
		return getUserById(uid);
	}

}
