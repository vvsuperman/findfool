package zpl.oj.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import zpl.oj.model.common.Comment;

public interface CommentDao {

	@Insert("INSERT INTO comment(parent_id,user_id,subject_type,subject_id,content,create_time,status)"
			+ " VALUES (#{parentId},#{userId},#{subjectType},#{subjectId},#{content},#{createTime},#{status})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	int add(Comment comment);
}
