package zpl.oj.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.foolrank.model.CompanyModel;

import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;

public interface ImgUploadService{
	abstract String saveImg(Img img);
	abstract void insertImg(ImgForDao img);
	void upDateInsertImg(Img img);
	abstract void saveCompanyImg(CompanyModel company, MultipartFile fileitem,
			int flag);
}
