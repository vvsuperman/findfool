package zpl.oj.web.Rest.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.imp.UpLoadService;

@Controller
public class UploadController {
	@Resource
	UpLoadService upLoadService;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase uploadInvite(@RequestParam MultipartFile[] file) {
		ResponseBase rs = new ResponseBase();
		try {
			upLoadService.batchImport(file);
		} catch (Exception e) {
			// TODO: handle exception
			int num = e.getStackTrace()[0].getLineNumber();
			rs.setState(0);
			rs.setMessage(num);
		}
		
		rs.setState(1);
		return rs;
	}

}
