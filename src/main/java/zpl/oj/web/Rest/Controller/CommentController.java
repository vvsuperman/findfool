package zpl.oj.web.Rest.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.CandidateDao;
import zpl.oj.model.common.Candidate;
import zpl.oj.model.common.Comment;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.imp.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CandidateDao candidateDao;

	@RequestMapping(value = "/add")
	@ResponseBody
	public ResponseBase add(@RequestBody Map<String, String> map) {
		Comment comment = new Comment();
		int parentId = Integer.parseInt(map.get("pid").trim());
		String email = map.get("email").trim();
		Candidate candicate = candidateDao.findUserByEmail(email);
		int userId = candicate == null ? 0 : candicate.getCaid();
		int subjectType = Integer.parseInt(map.get("stype"));
		int subjectId = Integer.parseInt(map.get("sid"));
		String content = map.get("content");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createTime = format.format(new Date());
		comment.setParentId(parentId);
		comment.setUserId(userId);
		comment.setSubjectType(subjectType);
		comment.setSubjectId(subjectId);
		comment.setContent(content);
		comment.setCreateTime(createTime);
		comment.setStatus(1);
		commentService.create(comment);

		ResponseBase rb = new ResponseBase();
		rb.setMessage(comment.getId());

		return rb;
	}
}
