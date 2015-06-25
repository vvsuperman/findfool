package zpl.oj.service.imp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CandidateDao;
import zpl.oj.model.common.Candidate;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SendCloud;
import zpl.oj.util.mail.SimpleMailSender;

@Service
public class CadService {
	
	@Autowired
	CandidateDao cadDao;
	
	@Autowired
	SendCloud sendCloud;
	
	public void resetPwdApply(String email,Candidate cad) {
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
        cad.setReseturl(sb.toString());
        cadDao.updateUserById(cad);
        //发送邮件
        String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
        String content ="<p>您好，这是来自foolrank.com的重置密码邮件，请到</p><p><a href='"+baseurl+"/#/dp/"+sb.toString()+"'>"+baseurl+"/#/dp/"+sb.toString()+"</a></p><p>重置密码</p>";
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
