package zpl.oj.web.Rest.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.QuizEmail;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.InviteService;
import zpl.oj.service.QuizEmailService;

@Controller
@RequestMapping("/quizemail") 
public class QuizEmailController {

	@Autowired
	private QuizEmailService quizEmailService;
	@Autowired
	private InviteService inviteService;
	@RequestMapping(value="/getemails")
	@ResponseBody
	public ResponseBase getLabels(@RequestBody Map<String,Integer> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=map.get("testid");
		//获取该测试所设置的所有email
		List<QuizEmail> emails=quizEmailService.getEmailsByQuizId(testid);		
		rb.setState(1);
		rb.setMessage(emails);
		return rb;
	}
	//用户添加新的邮箱
	@RequestMapping(value="/addemail")
	@ResponseBody
	public ResponseBase addLabel(@RequestBody Map<String,Object> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=Integer.parseInt(map.get("testid").toString());
		String email=map.get("email").toString();
		if(email==null || "".equals(email)){
			rb.setState(0);
			rb.setMessage("邮箱不能为空！");
			return rb;
		}
		//如果该email已被添加过
		QuizEmail quizEmail=quizEmailService.getEmailByEmail(testid, email);
		if(quizEmail!=null){			
			rb.setState(0);
			rb.setMessage("邮箱已存在！");
			return rb;
		}
		else{
			quizEmailService.insertIntoPublicLinkEmail(testid,email);			
			rb.setState(1);
			return rb;
		}
	}
	
	class JsonLabel{
		
		private Integer labelid;
		private String labelname;
		private Boolean isSelected;
		public Integer getLabelid() {
			return labelid;
		}
		public void setLabelid(Integer labelid) {
			this.labelid = labelid;
		}
		public String getLabelname() {
			return labelname;
		}
		public void setLabelname(String labelname) {
			this.labelname = labelname;
		}
		public Boolean getIsSelected() {
			return isSelected;
		}
		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
		}
	}
}
