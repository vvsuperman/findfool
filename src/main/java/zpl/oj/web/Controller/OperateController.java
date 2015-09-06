package zpl.oj.web.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.foolrank.model.CompanyModel;
import com.foolrank.response.json.CompanyJson;
import com.foolrank.util.RequestUtil;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.OperateDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.CompanyService;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;



@Controller
@RequestMapping("/operate")
public class OperateController {
	
	@Autowired
private OperateDao operateDao;
	
	
	@RequestMapping(value = "/operateurl")
	@ResponseBody
	public ResponseBase operateurl(@RequestBody Map<String, Integer> params) {

		int arg = params.get("arg");
		String filePath ="";
		int roleid = 0;
		ResponseBase rb = new ResponseBase();
		if (arg ==1) {
			filePath = (String) PropertiesUtil.getContextProperty("bapi");
			roleid = 2;
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));
				String ss = "";
				while ((ss = in.readLine()) != null) {
					String[] s = ss.split(" ");
					for (int i = 0; i < s.length; i++) {
						System.out.println("api............."+ s[i]);
						//判断api是否存在
						int num = operateDao.countApi(s[i]);
						if(num == 0){
							operateDao.insertApi(s[i]);
						}
						int apiid = operateDao.getApiidByApi(s[i]);
						//判断api_role是否存在
						String url = operateDao.getUrlbyApi(s[i],roleid);
						if ( (url == null)||(url.isEmpty())) {
							operateDao.insertRoleapi(roleid, apiid);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (arg ==2) {
			filePath = (String) PropertiesUtil.getContextProperty("capi");
			roleid = 1;
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(filePath));
				String ss = "";
				while ((ss = in.readLine()) != null) {
					String[] s = ss.split(" ");
					for (int i = 0; i < s.length; i++) {
						System.out.println("api............."+ s[i]);
						//判断api是否存在
						int num = operateDao.countApi(s[i]);
						if(num == 0){
							operateDao.insertApi(s[i]);
						}
						int apiid = operateDao.getApiidByApi(s[i]);
						//判断api_role是否存在
						String url = operateDao.getUrlbyApi(s[i],roleid);
						if ( (url == null)||(url.isEmpty())) {
							operateDao.insertRoleapi(roleid, apiid);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			rb.setMessage("参数传递出错，请核实！");
			rb.setState(1);
			return rb;
		}
		if (roleid == 0) {
			rb.setMessage("roleid在程序中未被初始化！");
			rb.setState(2);
			return rb;
		}
		
		rb.setMessage("操作完成！");
		rb.setState(0);
		return rb;

	}


	
	
}
