package zpl.oj.web.Rest.Controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//import com.facepp.error.FaceppParseException;
import com.sun.mail.util.BASE64DecoderStream;

import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.request.User;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.UpLoadService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;
//import zpl.oj.util.face.FaceUtil;

@Controller
public class UploadController {
	@Resource
	private UpLoadService upLoadService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ImgUploadService imgUploadService;
	
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
	
	
	@RequestMapping(value="/upload/img",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase uploadImg(
			@RequestBody Img img
	       ) {	
//		   ) throws FaceppParseException {	
		ResponseBase rs = new ResponseBase();
		String location = imgUploadService.saveImg(img);
		
//		FaceUtil faceUtil = new FaceUtil();
//		faceUtil.faceDetect(location);
		ImgForDao imgForDao=new ImgForDao();
	    imgForDao.setInvitedid(img.getInvitedid());
	    imgForDao.setLocation(location);
	    Date time = new Date();
	    imgForDao.setTime(time);	    
	    imgUploadService.insertImg(imgForDao);
		rs.setState(1);
		return rs;
	}
	
	

}
