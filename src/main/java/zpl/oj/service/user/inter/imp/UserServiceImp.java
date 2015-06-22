package zpl.oj.service.user.inter.imp;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.VerifyQuestion;
import zpl.oj.model.request.User;
import zpl.oj.service.InviteService;
import zpl.oj.service.VerifyQuestionService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SendCloud;
import zpl.oj.util.mail.SimpleMailSender;
import zpl.oj.util.randomCode.ValidateCode;

@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserDao userDao;
    @Autowired
    private InviteService inviteService;
    @Autowired
    private VerifyQuestionService vfQuestion;
    
    @Autowired
    private SendCloud sendCloud;
    
    
	@Override
	public boolean addUser(User u) {
		//检查邮箱是否存在，
		User dbU = userDao.getUserIdByEmail(u.getEmail());
		if(dbU != null){
			if(dbU.getPrivilege() <2 && u.getPrivilege() >1){
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

	@Override
	public void resetPwdApply(String email,User user) {
		// TODO Auto-generated method stub
		//生成随机字符串
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<24;i++){
         int number=random.nextInt(62);
         sb.append(str.charAt(number));
        }
        //保持到数据库
        user.setResetUrl(sb.toString());
        userDao.updateUser(user);
        //发送邮件
        
        String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
        String content ="<p>您好，这是一封重置密码的邮件，请到</p><a href='"+baseurl+"/#/"+sb.toString()+"'>"+baseurl+"/#/"+sb.toString()+"</a><p>重置密码</p>";
      //发送邮件
        try {
			sendCloud.sendmail(email, "重置密码", content);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
      	
      	
      	
        
	}

}
