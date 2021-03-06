package zpl.oj.service.imp;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.QuizEmailDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.QuizEmail;
import zpl.oj.service.QuizEmailService;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.des.DESService;
import zpl.oj.util.mail.MailSenderInfo;
import zpl.oj.util.mail.SendCloud;
import zpl.oj.util.mail.SimpleMailSender;

@Service
public class QuizEmailServiceImpl implements QuizEmailService {

	@Autowired
	QuizEmailDao quizEmailDao;

	@Autowired
	private DESService desService;
	@Autowired
	private SendCloud sendCloud;
	
	@Override
	public List<QuizEmail> getEmailsByQuizId(Integer quizId) {
		// TODO Auto-generated method stub
		return quizEmailDao.getAllEmailsByQuizId(quizId);
	}

	@Override
	public QuizEmail getEmailByEmail(Integer quizId, String email) {
		// TODO Auto-generated method stub
		return quizEmailDao.getEmailByEmail(quizId, email);
	}

	@Override
	public void insertIntoPublicLinkEmail(Integer quizId, String email) {
		// TODO Auto-generated method stub
		quizEmailDao.insertIntoPublicLinkEmail(quizId, email);
	}
	//根据inviteid生成公开链接发送给quizemail
	@Override
	public void sendMail(QuizEmail qe, Invite invite) {
		// TODO Auto-generated method stub
		String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
		String url = desService.encode(invite.getIid().toString()+"|"+invite.getTestid().toString()+"|"+invite.getUid().toString());
		String context = "<p>这是来自foolrank公司的邮件，您收到一份测试报告，请登录到:</p>"
				+"<a href="+baseurl+"/#/publicReport/list/"+url+">"+baseurl+"/#/publicReport/list/"+url+"</a>"
				+ " 查看。"+"</p>";
		
		//发送邮件
		
		try {
			sendCloud.sendmail(qe.getEmail(), "测试报告--来自foolrank", context);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


}
