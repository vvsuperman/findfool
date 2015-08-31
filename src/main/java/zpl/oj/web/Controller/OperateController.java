package zpl.oj.web.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
	public ResponseBase operateurl() {
		String filePath = (String) PropertiesUtil.getContextProperty("bapi");
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(filePath));

			String ss = "";
			while ((ss = in.readLine()) != null) {
				String[] s = ss.split(" ");

				for (int i = 0; i < s.length; i++) {

					String url = operateDao.getUrlbyApi(s[i]);
					if ((url.length()>0)&&(url!=null)) {
						operateDao.insertApi(s[i]);
						int apiid = operateDao.getApiidByApi(s[i]);
						int roleid = 2;
						operateDao.insertRoleapi(roleid, apiid);

					}

					System.out.println(s[i]);

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ResponseBase rb = new ResponseBase();

		return rb;
	}
	

}
