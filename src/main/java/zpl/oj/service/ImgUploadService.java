package zpl.oj.service;

import zpl.oj.model.common.Img;
import zpl.oj.model.common.ImgForDao;

public interface ImgUploadService{
	abstract String saveImg(Img img);
	abstract void insertImg(ImgForDao img);
	void upDateInsertImg(Img img);
}
