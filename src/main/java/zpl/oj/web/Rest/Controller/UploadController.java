package zpl.oj.web.Rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.model.responsejson.ResponseBase;

@Controller
public class UploadController {
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase uploadInvite(@RequestParam MultipartFile file, HttpServletRequest request){
		ResponseBase rs = new ResponseBase();
		return rs;
		
	}

}
