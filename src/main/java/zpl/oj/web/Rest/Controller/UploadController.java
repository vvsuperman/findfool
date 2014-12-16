package zpl.oj.web.Rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.model.responsejson.ResponseBase;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@RequestMapping(value="/file")
	@ResponseBody
	public ResponseBase uploadInvite(@RequestParam MultipartFile myfile, HttpServletRequest request){
		ResponseBase rs = new ResponseBase();
		return rs;
		
	}

}
