package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.TestuserDao;
import zpl.oj.model.common.Testuser;

@Service
public class TestuserService {
	@Autowired
	public TestuserDao testuserDao;

	public int updateUser(Testuser testuser) {
		// TODO Auto-generated method stub
		Testuser tuser =testuserDao.findTestuserByName(testuser.getEmail()); 
		if(tuser!=null){
			//该邮箱的用户曾经做过题，执行更新操作
			testuserDao.updateTestuser(testuser);
			return tuser.getTuid(); 
		}else{
			testuserDao.insertTestuser(testuser);
			return testuserDao.findTestuserByName(testuser.getEmail()).getTuid();
		}
				
	}
	
	
	
}
