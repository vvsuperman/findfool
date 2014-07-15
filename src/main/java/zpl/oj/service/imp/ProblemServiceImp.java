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
import zpl.oj.model.requestjson.RequestSearch;
import zpl.oj.model.responsejson.ResponseSearchResult;
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

	protected List<Integer> mergeLists(List<List<Integer>> lists){
		List<Integer> res = new ArrayList<Integer>();
		for(List<Integer> t:lists){
			res.removeAll(t);
			res.addAll(t);
		}
		return res;
	}
	@Override
	public int addProblem(RequestAddQuestion q) {
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
		return pid;
	}

	@Override
	public ResponseSearchResult getQuestionByTag(RequestSearch s) {
		ResponseSearchResult res = new ResponseSearchResult();
		if(s.getKeyword() == null)
			return res;
		String[] tags = s.getKeyword().split(" ");
		List<List<Integer>> questionIds = new ArrayList<List<Integer>>();
		List<Question> questions = new ArrayList<Question>();
		int begin = (s.getPage()-1)*s.getPageNum();
		int end = s.getPage()*s.getPageNum();
		for(int i=0;i<tags.length;i++){
			List<Integer> tp = null;
			if(s.getBelong() == 0){
				tp = problemTagDao.getProblemIdbyTagSite(tags[i],s.getType(),s.getSetid());
				
			}else{
				tp = problemTagDao.getProblemIdbyTagUser(tags[i],s.getType(), s.getUser().getUid());
			}	
			questionIds.add(tp);
		}
		
		//merge
		List<Integer> qsId = mergeLists(questionIds);
		int total = qsId.size();
		if(end > total){
			end = total;
		}
		for(int i=begin;i<end ;i++){
			Question question = getProblemById(qsId.get(i));			
			if (question !=null){
				questions.add(question);
			}
		}
		res.setCurPage(s.getPage());
		res.setPageNum(s.getPageNum());
		res.setTotalPage(total);
		res.setQuestions(questions);
		return res;
	}

	@Override
	public ResponseSearchResult getQuestionBySetId(RequestSearch s) {
		ResponseSearchResult res = new ResponseSearchResult();

		List<Question> questions = new ArrayList<Question>();
		int begin = (s.getPage()-1)*s.getPageNum();
		int end = s.getPage()*s.getPageNum();
		Integer total = problemDao.getCountSiteProblemIdbySet(s.getSetid(), s.getType());
		if(end > total){
			end = total;
		}
		List<Integer> tp = problemDao.getSiteProblemIdbySet(s.getSetid(), s.getType(), begin, end);

		for(Integer i:tp){
			Question question = getProblemById(i);			
			if (question !=null){
				questions.add(question);
			}
		}
		res.setCurPage(s.getPage());
		res.setPageNum(s.getPageNum());
		res.setTotalPage(total);
		res.setQuestions(questions);
		return res;
	}

	@Override
	public ResponseSearchResult getQuestionByUser(RequestSearch s) {
		ResponseSearchResult res = new ResponseSearchResult();

		List<Question> questions = new ArrayList<Question>();
		int begin = (s.getPage()-1)*s.getPageNum();
		int end = s.getPage()*s.getPageNum();
		Integer total = problemDao.getCountUserProblemIdbyUid(s.getUser().getUid(), s.getType());
		if(end > total){
			end = total;
		}
		List<Integer> tp = problemDao.getUserProblemIdbyUid(s.getUser().getUid(), s.getType(), begin, end);

		for(Integer i:tp){
			Question question = getProblemById(i);			
			if (question !=null){
				questions.add(question);
			}
		}
		res.setCurPage(s.getPage());
		res.setPageNum(s.getPageNum());
		res.setTotalPage(total);
		res.setQuestions(questions);
		return res;
	}

}
