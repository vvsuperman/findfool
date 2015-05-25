package zpl.oj.web.Rest.Controller;

import java.util.Date;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.user.UserDao;
import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.ImgUploadService;
import zpl.oj.service.imp.UpLoadService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.base64.BASE64;
//import zpl.oj.util.face.FaceUtil;
import zpl.oj.util.face.FaceUtil;
//import com.facepp.error.FaceppParseException;

@Controller
public class UploadController {
	@Resource
	private UpLoadService upLoadService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ImgUploadService imgUploadService;
	
	@Resource
	private FaceUtil faceUtil;
	
	@Resource
	private TestuserDao tuserDao;
	
	
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
	
	
	@RequestMapping(value="/uploadimg",method=RequestMethod.POST)
	@ResponseBody
	public ResponseBase uploadImg(
			@RequestBody Img img
	       ) throws JSONException {	
//		   ) throws FaceppParseException {	
		ResponseBase rs = new ResponseBase();
		byte[] imgBytes = null;
		try {
			imgBytes = BASE64.decodeBASE64(img.getImgData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject jFace = null;
		
		//检测人脸
		String faceid =""; 
		//首次发送，基准照片，将基准照片的face_id存入数据库
		if(img.getState().equals("1")){
			jFace =	faceUtil.faceDetect(imgBytes);
			//未取到人脸，或人脸过多，返回错误，重新拍摄
			
			if(jFace.getJSONArray("face").length()!=1){
				rs.setState(1);
				rs.setMessage("照片不清晰或人脸过多，请重新拍摄"+jFace.toString());
				return rs;
			}
			
		    //取到了一张清晰的人脸
			faceid  =((JSONObject)jFace.getJSONArray("face").get(0)).get("face_id").toString();
//			faceid= jso.getString("facd_id");
			Testuser testUser = tuserDao.findTestuserByName(img.getEmail());
			testUser.setFaceid(faceid);
			tuserDao.updateTestuserById(testUser);
			imgUploadService.upDateInsertImg(img);
			rs.setState(0);
			rs.setMessage("保存基准照片"+jFace.toString());
			return rs;
		}else{//非基准照片
			jFace =	faceUtil.faceDetect(imgBytes);
			//未取到人脸，或人脸过多，直接保存到图片列表
			if(jFace.getJSONArray("face").length()!=1){
				imgUploadService.upDateInsertImg(img);
				rs.setState(2);
				rs.setMessage("照片不同"+jFace.toString());
			}
			else{
				String newFaceid =((JSONObject)jFace.getJSONArray("face").get(0)).get("face_id").toString();
				String baseFacdid = tuserDao.findTestuserByName(img.getEmail()).getFaceid();
				jFace = faceUtil.faceCompare(baseFacdid, newFaceid);
				//若相似度不足60，则判定人脸为不相似，存入数据库
				if(jFace.getDouble("similarity")<60){
					imgUploadService.upDateInsertImg(img);
					rs.setState(3);
					rs.setMessage("照片已保存"+jFace.toString());
				}else{
					rs.setState(4);
					rs.setMessage("照片相同，不用保存"+jFace.toString());
				}
			}
		}
		
		
		return rs;
	}
	
	

}
