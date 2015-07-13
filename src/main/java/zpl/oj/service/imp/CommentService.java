package zpl.oj.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CommentDao;
import zpl.oj.model.common.Comment;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;
	
	public int create(Comment comment) {
		return commentDao.add(comment);
	}
}
