package zpl.oj.service;


import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.foolrank.model.CompanyModel;

import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.TuserProblem;

public interface ImgUploadService{
	abstract String saveImg(Img img);
	abstract void insertImg(ImgForDao img);
	void upDateInsertImg(Img img);

	abstract void saveCompanyImg(CompanyModel company, MultipartFile fileitem,
			int flag);

	abstract void saveCompanyImg(CompanyModel company, String img, Integer flag);
	abstract void saveTestImg(Quiz quiz, MultipartFile fileitem);
	abstract void saveTestingFile(TuserProblem testuserProblem,
			MultipartFile fileitem);
	abstract String uploadCompanyImageTail(MultipartFile fileitem);

}
