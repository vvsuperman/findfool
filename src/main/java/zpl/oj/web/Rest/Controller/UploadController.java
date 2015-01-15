package zpl.oj.web.Rest.Controller;

import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.imp.UpLoadService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.base64.BASE64;

@Controller
public class UploadController {
	@Resource
	private UpLoadService upLoadService;
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase uploadInvite(
			@RequestParam MultipartFile[] file,
			@RequestHeader (value="Authorization",required=false) String token
	       ) throws Exception {
		
		ResponseBase rs = new ResponseBase();
	    int uid =-1;
	    String regEx = "@,@,@,@";
		if(token != null){
			String tokenUid = new String(BASE64.decodeBASE64(token));
			Pattern pat = Pattern.compile(regEx);
			String[] strs = pat.split(tokenUid);
			if(strs.length >2)
				return null;
			uid= Integer.parseInt(strs[1]);
		}
		
		
		
		
		try {
			upLoadService.batchImport(file,uid);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			String msg = e.getStackTrace()[0].getFileName();
			
			rs.setState(0);
			rs.setMessage(msg);
			return rs;
		}
		
		rs.setState(1);
		return rs;
	}

}
