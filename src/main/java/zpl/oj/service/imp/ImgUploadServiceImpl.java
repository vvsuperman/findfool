package zpl.oj.service.imp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.dao.CompanyDao;
import zpl.oj.dao.ImgUploadDao;
import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.service.ImgUploadService;
import zpl.oj.util.StringUtil;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;

import com.foolrank.model.CompanyModel;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service
public class ImgUploadServiceImpl implements ImgUploadService {

	private int COMPANY_COVER=1;
	private int COMPANY_LOGO=2;
	
	@Autowired
	ImgUploadDao imgStoreDao;
	
	@Autowired
	CompanyDao companyDao;
	
	
	
	
	@Override
	public String saveImg(Img img) {
		
		String url ="-";
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
		String imgdata = img.getImgData();
		//将img存到七牛
		pushImg(filename, imgdata);
		
		return filename;
	}
	

//	//保存公司的封面或logo
//	public void saveCompanyImg(Integer companyId, File fileitem,Integer flag){
//		//get company		
//		 
//		CompanyModel company=companyDao.getById(companyId);
//	
//		
//		                        
//		//String filename = company.getName()+sdf.format(new Date());
//		                        
//		String filename = company.getName()+StringUtil.toDateTimeString(new Date());
//		pushImg(filename, fileitem);
//		//保存封面
//		if(flag==COMPANY_COVER){
//			company.setCover(filename);
//		}
//		//保存
//		else if(flag==COMPANY_LOGO){
//			company.setLogo(filename);
//		}
//		companyDao.modify(company);
//	}
	
	
	
	//保存公司的封面或logo
	public void saveCompanyImg(CompanyModel company, MultipartFile fileitem,int flag){
                       		                        
		String filename = company.getName()+StringUtil.toDateTimeString(new Date());
		pushImg(filename, fileitem);
		//保存封面
		if(flag==COMPANY_COVER){
			company.setCover(filename);
		}
		//保存
		else if(flag==COMPANY_LOGO){
			company.setLogo(filename);
		}
		
		companyDao.modify(company);
	}
	
	/**
	 * @param filename
	 * @param imgdata
	 */
	public void pushImg(String filename, MultipartFile imgFile) {
		byte[] imgBytes=null;
	try {
		imgBytes=imgFile.getBytes();
	} catch (IOException e1) {
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
	}
	
	/**
	 * @param filename
	 * @param imgdata
	 */
	public void pushImg(String filename, String imgdata) {
		byte[] imgBytes=null;
		try {
			imgBytes = BASE64.decodeBASE64(imgdata);
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



	














	@Override
	public void saveCompanyImg(CompanyModel company, String img, Integer flag) {
		// TODO Auto-generated method stub
		
	}

}
