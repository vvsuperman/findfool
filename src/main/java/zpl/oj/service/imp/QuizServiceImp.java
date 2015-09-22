package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.QuizProblemDao;
import zpl.oj.dao.RandomQuizDao;
import zpl.oj.dao.SetDao;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.QuizTemplete;
import zpl.oj.model.common.RandomQuizSet;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestMeta;
import zpl.oj.model.responsejson.ResponseQuizDetail;
import zpl.oj.service.LabelService;
import zpl.oj.service.ProblemService;
import zpl.oj.service.QuizService;
@Service
public class QuizServiceImp implements QuizService {

	@Autowired
	private QuizDao quizDao;
	
	@Autowired
	private QuizProblemDao quizProblemDao;
	
	@Autowired
	private ProblemService problemService;
	
	@Autowired
	private InviteDao inviteDao;
	
	@Autowired
	private RandomQuizDao randomQuizDao;
	@Autowired
	private LabelService labelService;
	
	/*
	 *根据拥有者返回quiz 
	 * 返回：
	 * 1 题数，做题时间
	 * 2 已邀请数
	 * 3 已回答数
	 * */
	@Override
	public List<Quiz> getQuizByOwner(int owner) {
		List<Quiz> quizList = quizDao.getQuizs(owner);
		for (Quiz quiz : quizList) {
			int pNum = 0;
			int invited = 0;
			int finished = 0;
			if (quiz.getParent() == 0) {
				     List<RandomQuizSet> randomList=   randomQuizDao.getListByTestid(quiz.getQuizid());
				if((randomList!=null)&&(!randomList.isEmpty())){
				pNum = randomQuizDao.countPnumInRQ(quiz.getQuizid());
				}else{
					pNum=0;
				}
				invited = inviteDao.countInvitesByP(quiz.getQuizid());
				finished = inviteDao.countInviteFinishedByP(quiz.getQuizid());

			} else if(quiz.getParent()==quiz.getQuizid()) {
				pNum = quizProblemDao.countPnumInQuiz(quiz.getQuizid());
				invited = inviteDao.countInvites(quiz.getQuizid());
				finished = inviteDao.countInviteFinished(quiz.getQuizid());
			}

			quiz.setQuestionNum(pNum);
			quiz.setInvitedNum(invited);
			quiz.setFinishedNum(finished);
		}

		return quizList;

	}
	
	@Override
	public String addQuestionToQuiz(QuizProblem quizProblem){
		List<QuizProblem> lists = quizProblemDao.getQuizProblemsByQuizProblem(quizProblem);
		if(lists.size() !=0){
			return "测试已包含此试题";
		}else{
			quizProblemDao.insertQuizproblem(quizProblem);
			return null;
		}	
	}
	
	
	
	
	
	

	@Override
	public ResponseQuizDetail getQuizDetail(int tid) {
		ResponseQuizDetail detail = new ResponseQuizDetail();
		Quiz quiz = quizDao.getQuiz(tid);
		
		detail.setName(quiz.getName());
		detail.setEmails(quiz.getEmails());
		detail.setExtrainfo(quiz.getExtraInfo());
		detail.setTesttime(quiz.getTime());
		detail.setQuizid(tid);
		
		List<Question> qlist = new ArrayList<Question>();
		List<QuizProblem> qps = quizProblemDao.getQuizProblemsByQuizId(tid);
		//得到problems
		for(QuizProblem qp :qps){
			Question q = problemService.getProblemById(qp.getProblemid());
	
			if(q != null)
				qlist.add(q);
		}
		
		detail.setQs(qlist);
		return detail;
	}

	@Override
	public void updateQuizMetaInfo(RequestTestMeta tm) {
		Quiz quiz = quizDao.getQuiz(tm.getQuizid());
		if(quiz != null){
			quiz.setTime(tm.getTesttime());
			quiz.setEmails(tm.getEmails());
			quiz.setExtraInfo(tm.getExtrainfo());
			quizDao.updateQuiz(quiz);
		}
	}

	@Override
	public Quiz updateQuiz(int tid, List<Integer> qids) {
		//step1:得到tid的uuid
		Quiz q = quizDao.getQuiz(tid);
		if(q == null)
			return null;
		int uuid = q.getUuid();
		//step2:构造一个quiz，其uuid为刚刚取出的quiz的值
		//step3:将这个quiz插入数据库,并且得到最新的版本的Quiz
		quizDao.insertQuiz(q);
		q = quizDao.getNewestQuizByUuid(uuid);
		for(Integer qid:qids){
			QuizProblem qp = new QuizProblem();
			qp.setDate(new Date());
			qp.setQuizid(q.getQuizid());
			qp.setProblemid(qid);
			quizProblemDao.insertQuizproblem(qp);
		}
		return q;
	}

	@Override
	public Quiz addQuiz(RequestTestMeta tm) {
		Quiz quiz = new Quiz();

		quiz.setTime(tm.getTesttime());
		quiz.setEmails(tm.getEmails());
		quiz.setExtraInfo(tm.getExtrainfo());
		quiz.setName(tm.getName());
		quiz.setDate(new Date());
		quiz.setOwner(tm.getUser().getUid());
		quiz.setOpenCamera(1);//默认不开启摄像头
		quizDao.insertQuiz(quiz);
		quiz = quizDao.getNewestQuizByOwner(tm.getUser().getUid());
		quiz.setUuid(quiz.getQuizid());
		if(quiz != null)
			quizDao.updateQuiz(quiz);
		return quiz;
	}

	@Override
	public Quiz getQuizMetaInfoByID(int tid) {
		return quizDao.getQuiz(tid);
	}

	@Override
	public Quiz getQuizMetaInfoByName(String name,int uid) {
		return quizDao.getQuizByOwnerAndName(name,uid);
	}

	@Override
	public List<QuizProblem> getQuizsByProblemId(Integer pid) {
		List<QuizProblem> res = new ArrayList<QuizProblem>();
		List<QuizProblem> quizs= quizProblemDao.getQuizProblemsByProblemId(pid);
		for(QuizProblem q:quizs){
			List<QuizProblem> tmp = quizProblemDao.getQuizProblemsByQuizId(q.getQuizid());
			res.addAll(tmp);
		}
		return res;
	}

	@Override
	public void deleteQuestionFromTest(QuizProblem quizProblem) {
		// TODO Auto-generated method stub
		 quizProblemDao.deleteQuestionFromTest(quizProblem);
	}

	@Override
	public Integer genQuiz(String quizName, int uid) {
		// TODO Auto-generated method stub
		QuizTemplete quizT = quizDao.getQuizTByName(quizName);
		Quiz quiz = new Quiz();
		quiz.setDate(new Date());
		quiz.setOwner(uid);
		quiz.setName(quizT.getQuizTDesc());
		quiz.setTime(quizT.getTime());
		quizDao.insertQuiz(quiz);
		quiz = quizDao.getNewestQuizByOwner(uid);
					quiz.setParent(quiz.getQuizid());
					quizDao.updateQuiz(quiz);
		
		List<QuizProblem> quizProbs = quizProblemDao.getQuizProblemsByQuizId(quizT.getQuizId());
		for(QuizProblem quizProblem:quizProbs){
			 quizProblem.setQuizid(quiz.getQuizid());
			 quizProblemDao.insertQuizproblem(quizProblem);
		}
		
		return quiz.getQuizid();
		
	}

	@Override
	public void saveTime(int quizid, String openCamera, String startTime,
			String deadTime) {
	 
	           Quiz   quiz =quizDao.getQuiz(quizid);
	           quiz.setEndTime(deadTime);
	           quiz.setStartTime(startTime);
	           int openCameras =Integer.parseInt(openCamera);
	           quiz.setOpenCamera(openCameras);        	             
		       quizDao.saveTime(quiz);
		     		
	}

	@Override
	public Quiz addQuiz(Quiz quiz) {
	

		quiz.setOpenCamera(1);//默认不开启摄像头
		quizDao.insertQuiz(quiz);
		quiz = quizDao.getNewestQuizByOwner(quiz.getOwner());
		quiz.setUuid(quiz.getQuizid());
		if(quiz != null)
			quizDao.updateQuiz(quiz);
		return quiz;
		
	}

	@Override
	public void addRandomQuiz(List<RandomQuizSet> randomQuizs) {
		
	for (RandomQuizSet randomQuiz : randomQuizs) {
		randomQuizDao.add(randomQuiz);
		
	}
		
	}

	@Override
	public Quiz getQuizByTestid(Integer testid) {
		// TODO Auto-generated method stub
		return  quizDao.getQuizByTestid(testid);
	}





}
