package zpl.oj.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.Invite;
@Service
public class PersonalService {

	@Autowired
	private TestuserDao testuserDao;
	
	@Autowired
	private InviteDao inviteDao;
	

	public Map<String, Object> findAllList(String email) {
		
       Testuser testuser=testuserDao.findTuserByEmail(email);
		                   
		List<Invite>     inviteList    =  inviteDao.getInviteByUid(testuser.getTuid());
	
		  Map<String,Object> map=new HashMap<String,Object>();
		  
		map.put("testuser", testuser);
		map.put("inviteList",inviteList);
		
		
		return map;
		
		
		
		
		
		
		
		
	}
}
