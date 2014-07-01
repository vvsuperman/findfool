package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTagDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.model.requestjson.RequestAddQuestion;
import zpl.oj.service.ProblemService;

@Service
public class ProblemServiceImp implements ProblemService{

	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;
	@Autowired
	private ProblemTagDao problemTagDao;
	@Override
	public Question getProblemById(int problemId) {
		Question q = null;
		Problem p =  problemDao.getProblem(problemId);
		if(p == null)
			return null;
		q = new Question();
		q.setQid(p.getProblemId());
		q.setName(p.getTitle());
		q.setContext(p.getDescription());
		
		List<ProblemTestCase> cases = problemTestCaseDao.getProblemTestCases(p.getProblemId());
		List<QuestionTestCase> answer = new ArrayList<QuestionTestCase>();
		int score = 0;
		for(ProblemTestCase c :cases){
			QuestionTestCase qt = new QuestionTestCase();
			qt.setText(c.getArgs());
			qt.setIsright(c.getExceptedRes());
			qt.setScore(c.getScore());
			answer.add(qt);
		}
		q.setAnswer(answer);
		List<String> tags = problemTagDao.getTagByProblemId(p.getProblemId());
		q.setTag(tags);
		return q;
	}

	@Override
	public void addProblem(RequestAddQuestion q) {
		Problem p = new Problem();
		p.setCreator(q.getUser().getUid());
		p.setDate(new Date());
		p.setDescription(q.getQuestion().getContext());
		p.setLimitMem(512);
		p.setLimitTime(5);
		p.setModifydate(new Date());
		p.setSloved(0);
		p.setSubmit(0);
		p.setTitle(q.getQuestion().getName());
		p.setType(q.getQuestion().getType());
		problemDao.insertProblem(p);
		int pid = problemDao.getProblemId(p.getCreator());
		p.setUuid(pid);
		
		for(String tag:q.getQuestion().getTag()){
			Integer tagid = problemTagDao.getTagId(tag);
			if(tagid == null){
				problemTagDao.insertTag(tag);
				tagid = problemTagDao.getTagId(tag);
			}
			problemTagDao.insertTagProblem(tagid, pid);
		}
		
		for(QuestionTestCase qt:q.getQuestion().getAnswer()){
			ProblemTestCase pt = new ProblemTestCase();
			pt.setProblemId(pid);
			pt.setArgs(qt.getText());
			pt.setExceptedRes(qt.getIsright());
			pt.setScore(qt.getScore());
			problemTestCaseDao.insertProblemTestCase(pt);
		}
		problemDao.updateProblem(p);
	}

}
