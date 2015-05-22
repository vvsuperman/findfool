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
		String imgHome=(String)PropertiesUtil.getContextProperty("ImgUploadHome");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String today=sdf.format(date);
		//first level directory
		String firstDir=imgHome+url+today;
		File file =new File(firstDir);
		if  (!file .exists())      
		{    
		    file.mkdir();    		
		} 
		//second level directory
		String secondDir=firstDir+url+img.getEmail().replace(".", "_");
		file =new File(secondDir);
		if  (!file.exists())      
		{    
		    file.mkdir();    
		} 
		sdf = new SimpleDateFormat("HH_mm_ss");
		date=new Date();
		String time=sdf.format(date);
		String filename=secondDir+url+time+".jpg";
		byte[] imgBytes=null;
		try {
			imgBytes = BASE64.decodeBASE64(img.getImgData());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
			fos.write(imgBytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null){
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
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
