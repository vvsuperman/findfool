
package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTagDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.QuizProblemDao;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestAddQuestion;
import zpl.oj.model.requestjson.RequestSearch;
import zpl.oj.model.responsejson.ResponseSearchResult;
import zpl.oj.service.ProblemService;
import zpl.oj.service.QuizService;
import zpl.oj.service.user.inter.UserService;

@Service
public class ProblemServiceImp implements ProblemService{

	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;
	@Autowired
	private ProblemTagDao problemTagDao;
	@Autowired
	private UserService userService;
	@Autowired
	private QuizService quizService;
	
	@Resource
	private QuizProblemDao quizProblemDao;
	


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
		q.setType(p.getType());
		
		List<ProblemTestCase> cases = problemTestCaseDao.getProblemTestCases(p.getProblemId());
		List<QuestionTestCase> answer = new ArrayList<QuestionTestCase>();
		for(ProblemTestCase c :cases){
			QuestionTestCase qt = new QuestionTestCase();
			qt.setText(c.getArgs());
			qt.setIsright(c.getExceptedRes());
			qt.setScore(c.getScore());
			qt.setCaseId(c.getTestCaseId());
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
		p.setProblemSetId(q.getQuestion().getSetid());
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
		User u = userService.getUserById(q.getUser().getUid());
		if(u.getPrivilege() >=3)
			p.setBelong(0);
		else{
			p.setBelong(1);
		}
		problemDao.insertProblem(p);
		int pid = problemDao.getProblemId(p.getCreator());
		p.setUuid(pid);
		
		if(q.getQuizId()!=0){
			QuizProblem quizProblem = new QuizProblem();
			quizProblem.setQuizid(q.getQuizId());
			quizProblem.setProblemid(pid);
			quizProblemDao.insertQuizproblem(quizProblem);
		}
		
		
		for(String tag:q.getQuestion().getTag()){
			Integer tagid = problemTagDao.getTagId(tag);
			if(tagid == null){
				problemTagDao.insertTag(tag);
				tagid = problemTagDao.getTagId(tag);
			}
			problemTagDao.insertTagProblem(tagid, pid);
		}
		
		String rightAnswer ="";
		
		if(q.getQuestion().getAnswer() != null){
			for(QuestionTestCase qt:q.getQuestion().getAnswer()){
				ProblemTestCase pt = new ProblemTestCase();
				pt.setProblemId(pid);
				pt.setArgs(qt.getText());
				pt.setExceptedRes(qt.getIsright());
				if(qt.getIsright()=="true"){
					rightAnswer+="1";
				}else{
					rightAnswer+="0";
				}
				
				pt.setScore(qt.getScore());
				problemTestCaseDao.insertProblemTestCase(pt);
			}		
		}

		p.setProblemId(pid);
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
		//filter
		List<Problem> plist = filterResult(qsId);
		questions = checkTags(plist,tags);
		int total = questions.size();
		if(end > total){
			end = total;
		}
		questions =questions.subList(begin, end);
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
		end = (end -begin) == 0? 1:(end -begin);
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
		end = (end -begin) == 0? 1:(end -begin);
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

	
	//by fangwei 20141125 不管什么版本的问题，全部更新,加入事务
	@Override
	public int modifyProblem(RequestAddQuestion q) {
		Problem p = problemDao.getProblem(q.getQuestion().getQid());
		if(p == null){
			return addProblem(q);
		}
		//新建一个problem
		p.setModifier(q.getUser().getUid());
		p.setProblemSetId(q.getQuestion().getSetid());
		p.setDescription(q.getQuestion().getContext());
		p.setLimitMem(512);
		p.setLimitTime(5);
		p.setModifydate(new Date());
		p.setTitle(q.getQuestion().getName());
		p.setType(q.getQuestion().getType());
		User u = userService.getUserById(q.getUser().getUid());
		problemDao.updateProblemInstance(p);
		int pid = p.getProblemId();
		//更新tag
		for(String tag:q.getQuestion().getTag()){
			Integer tagid = problemTagDao.getTagId(tag);
			if(tagid == null){
				problemTagDao.insertTag(tag);
				tagid = problemTagDao.getTagId(tag);
			}
			problemTagDao.insertTagProblem(tagid, pid);
		}
		//新建选项
		for(QuestionTestCase qt:q.getQuestion().getAnswer()){
			ProblemTestCase pt = problemTestCaseDao.getProblemTestCaseById(qt.getCaseId());
			pt.setArgs(qt.getText());
			pt.setExceptedRes(qt.getIsright());
			pt.setScore(qt.getScore());
			problemTestCaseDao.updateProblemTestCase(pt);
		}
		
		//by fangwei，修改试题后，更新所有关联到该试题的测试，若测试已发送，则不更新
		/*
		List<QuizProblem> quizProblems= quizProblemDao.getQuizProblemsByProblemId(pid);
		for(QuizProblem quizProblem: quizProblems){
			quizProblemDao.updateByQuizproblem(quizProblem.getQuizid(), quizProblem.getProblemid(), pid);
		}*/
		
			
		
		//by fangwei,修改试题不用更新quiz的版本，太麻烦，改用quiz的clone好了
		//problemDao.updateProblem(p);
		//更新quiz们
		/*
		
		
		List<QuizProblem> qps = quizService.getQuizsByProblemId(q.getQuestion().getQid());
		Map<Integer,List<Integer>> quizs = new HashMap<Integer,List<Integer>>();
		for(QuizProblem qp:qps){
			List<Integer> ps = quizs.get(qp.getQuizid());
			if(ps == null){
				ps = new ArrayList<Integer>();
			}
			
			/*
			if(qp.getProblemid() ==q.getQuestion().getQid()){
				ps.add(pid);
			}else{
				ps.add(qp.getProblemid());
			}
			quizs.put(qp.getQuizid(), ps);
			
		}
		
		
		
		for(Map.Entry<Integer, List<Integer>> entry:quizs.entrySet()){
			quizService.updateQuiz(entry.getKey(), entry.getValue());
		}
		*/
		
		return pid;
	}
	
	List<Question> checkTags(List<Problem> prbs,String[] tags){
		List<Question> q = new ArrayList<Question>();
		int index = 0;
		for(Problem p:prbs){
			Question tmp = getProblemById(p.getProblemId());
			for(int i=0;i<tags.length;i++){
				if(tmp.getTag().contains(tags[i])){
					index++;
					q.add(tmp);
				}
			}
		}
		return q;
	}
	List<Problem> filterResult(List<Integer> pq){
		List<Problem> questions = new ArrayList<Problem>();
		Map<Integer,Problem> map = new HashMap<Integer, Problem>();
		for(Integer i:pq){
			Problem q = problemDao.getNewestProblemByid(i);
			map.put(q.getUuid(), q);
		}
		for(Map.Entry<Integer, Problem> entry: map.entrySet()){
			questions.add(entry.getValue());
		}
		return questions;
	}

	@Override
	public int deleteProblem(Integer problemId) {
		problemDao.deleteProblem(problemId);
		//更新quiz们
		List<QuizProblem> qps = quizService.getQuizsByProblemId(problemId);
		Map<Integer,List<Integer>> quizs = new HashMap<Integer,List<Integer>>();
		for(QuizProblem qp:qps){
			List<Integer> ps = quizs.get(qp.getQuizid());
			if(ps == null){
				ps = new ArrayList<Integer>();
			}
			if(!problemId.equals(qp.getProblemid())){
				ps.add(qp.getProblemid());
			}
			quizs.put(qp.getQuizid(), ps);
		}
		
		for(Map.Entry<Integer, List<Integer>> entry:quizs.entrySet()){
			quizService.updateQuiz(entry.getKey(), entry.getValue());
		}
		return 0;
	}

	
	//by fw, add simple modify
	@Override
	public String modifyQuestion(Question question) {
		// TODO Auto-generated method stub
	
		return null;
	}

}
