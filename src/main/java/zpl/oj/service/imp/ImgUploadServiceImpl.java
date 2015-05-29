package zpl.oj.service.imp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import zpl.oj.dao.ImgUploadDao;
import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.service.ImgUploadService;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;

@Service
public class ImgUploadServiceImpl implements ImgUploadService {

	@Autowired
	ImgUploadDao imgStoreDao;
	
	@Override
	public String saveImg(Img img) {
		
		String url ="/";
		PrintStream out=System.out;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String today=sdf.format(date);
		String filename=today;
		filename+=url+img.getEmail().replace(".", "_");
		sdf = new SimpleDateFormat("HH_mm_ss");
		date=new Date();
		String time=sdf.format(date);
		//fileKey+=url+time+".jpg";
		filename+=url+time+".jpg";
		byte[] imgBytes=null;
		try {
			imgBytes = BASE64.decodeBASE64(img.getImgData());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String accessKey=(String)PropertiesUtil.getContextProperty("qiniuAccessKey");
		String secretKey=(String)PropertiesUtil.getContextProperty("qiniuSecretKey");
		Auth auth=Auth.create(accessKey, secretKey);
		String upToken=auth.uploadToken("foolrank");
		UploadManager uploadManager=new UploadManager();
		try {
			//file =new File(filename);
			Response response=uploadManager.put(imgBytes, filename, upToken);
		} catch (QiniuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(filename);		
		
		return filename;
	}

	@Override
	public void insertImg(ImgForDao img) {
		// TODO Auto-generated method stub
		imgStoreDao.insertImg(img);
	}
	
	@Override
	public void upDateInsertImg(Img img){
		String location = saveImg(img);
		ImgForDao imgForDao=new ImgForDao();
	    imgForDao.setInvitedid(img.getInvitedid());
	    imgForDao.setLocation(location);
	    Date time = new Date();
	    imgForDao.setTime(time);	    
	    insertImg(imgForDao);		
	}

}
