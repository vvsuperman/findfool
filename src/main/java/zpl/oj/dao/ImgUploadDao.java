package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.ImgForDao;

public interface ImgUploadDao {
	@Insert(" INSERT INTO img_store(INVITEDID,LOCATION,TIME)"
			+ " VALUES (#{invitedid}, #{location}, #{time})")
	  void insertImg(ImgForDao img);
	
	
	@Select("select * from img_store where invitedid = #{0}")
	List<ImgForDao> getImgsByIid(int inviteid);
}
