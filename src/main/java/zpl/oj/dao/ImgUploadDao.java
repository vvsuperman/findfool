package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;

import zpl.oj.model.common.ImgForDao;

public interface ImgUploadDao {
	@Insert(" INSERT INTO img_store(INVITEDID,LOCATION,TIME)"
			+ " VALUES (#{invitedid}, #{location}, #{time})")
	  void insertImg(ImgForDao img);
}
