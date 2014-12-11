package zpl.oj.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.InviteDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.SetDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.util.Constant.ExamConstant;


@Service
public class ScoreService {
	
	@Autowired
	private TuserProblemDao tuserProblemDao;
	@Autowired
	private ProblemDao problemDao;
	@Autowired
	private InviteDao inviteDao;
	@Autowired
	private SetDao setDao;
	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;
	
	
	/*
	 * 计算用户的分数，包括试题分数和用户的得分，并存入invite表
	 * */
	public String countUserAndTotalScore(Invite invite) {
		// TODO Auto-generated method stub
		int totalScore = tuserProblemDao.getTotalScore(invite.getIid());
		int userScore = tuserProblemDao.getUserScore(invite);
		Invite myInvite = inviteDao.getInviteById(invite.getIid());
		myInvite.setTotalScore(totalScore);
		myInvite.setScore(userScore);
		inviteDao.updateInvite(invite);
		return userScore+"/"+totalScore;
	}
	
	
	/*
	 * 获取用户分数。若还未生成则生成，若已生成则直接获取
	 * */
	public String getInviteScore(Invite invite){
		Invite myInvite = inviteDao.getInviteById(invite.getIid());
		if(myInvite.getScore() == 0){
			return countUserAndTotalScore(myInvite);
		}else{
			return myInvite.getScore()+"/"+myInvite.getTotalScore();
		}
	}
	
	/*
	 * 计算用户在某套试题中的排名
	 * */
	public String getRank(Invite invite){
		int testid = invite.getTestid();
		List<Invite> invites = inviteDao.getInviteByTid(invite.getTestid());
		List<Integer> scores = new ArrayList<Integer>();
		for(Invite tmp:invites){
			scores.add(tmp.getScore());
		}
		Collections.sort(scores);
		int i=scores.size();
		for(;i>0;i--){
			if(scores.get(i)==invite.getScore()){
				break;
			}
		}
		return i+"/"+scores.size();
	}
	
	/*
	 * 根据试题集计算用户的做题维度
	 * 返回Radar chart
	 * 结果类似于
	 * 名称：["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"];
	   值：[
	     [65, 59, 90, 81, 56, 55, 40],
	     [28, 48, 40, 19, 96, 27, 100]
	   ];
	 * */
	public Map getDimension(Invite invite){
		List<Problem> problems = problemDao.getProblemByTestid(invite.getTestid());
		List<TuserProblem> tProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
		//排序，便于比较
		Collections.sort(problems,new Comparator<Problem>() {
			@Override
			public int compare(Problem problem1,Problem problem2) {
				// TODO Auto-generated method stub
				return problem1.getProblemId().compareTo(problem2.getProblemId());
			}
		});
		
		Collections.sort(tProblems,new Comparator<TuserProblem>() {
			@Override
			public int compare(TuserProblem problem1,TuserProblem problem2) {
				// TODO Auto-generated method stub
				return problem1.getProblemid().compareTo(problem2.getProblemid());
			}
		});
		
		//按照setid进行划分，将总的答题数放入setMap，答对的题数放入rightmap
		Map<Integer, Integer> setMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rightMap = new HashMap<Integer,Integer>();
		for(int i=0;i<problems.size();i++){
			int setid = problems.get(i).getProblemSetId();
			if(setMap.containsKey(setid)){
				setMap.put(setid, setMap.get(setid)+1);
			}else{
				setMap.put(setid, 1);
			}
			
			if(problems.get(i).getRightAnswer().equals(tProblems.get(i).getUseranswer())){
				if(rightMap.containsKey(setid)){
					rightMap.put(setid, setMap.get(setid)+1);
				}else{
					rightMap.put(setid, 1);
				}
			}
		}
		
		//生成返回数据
		Map<String, Object> rtMap = new HashMap<String,Object>();
		rtMap.put("name", new ArrayList<String>());
		rtMap.put("val", new ArrayList<ArrayList<Integer>>());
		
		Iterator iter = setMap.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			int setid = (Integer)entry.getKey();
			int val = (Integer)entry.getValue();
			int userVal =0;
			if(rightMap.get(setid)!=null){
				userVal = rightMap.get(setid);
			}
			String setName = setDao.getSet(setid).getName();
			((ArrayList)rtMap.get("name")).add(setName);
			((ArrayList)((ArrayList)rtMap.get("val")).get(0)).add(val);
			((ArrayList)((ArrayList)rtMap.get("val")).get(1)).add(userVal);
		}
		return rtMap;
	}
	
	
	
	/*
	 * 返回报告的细节
	 * 包括，题干，选项，用户的回答，正确选项、以及用户的选项
	 * */
	public List<Question> getDetail(Invite invite){
	   List<Question> questions = tuserProblemDao.getUserQuestion(invite);
	   for(Question question:questions){
		   int qid = question.getQid();
		   //选择题，获取选项
		   if(question.getType().equals(ExamConstant.OPTION)){
			  List<QuestionTestCase> qts = new ArrayList<QuestionTestCase>();
			  List<ProblemTestCase> pts =  problemTestCaseDao.getProblemTestCases(qid);
			 
			  for(ProblemTestCase pt:pts){
				  QuestionTestCase qt = new QuestionTestCase();
				  qt.setCaseId(pt.getTestCaseId());
				  qt.setText(pt.getArgs());
				  qt.setIsright(pt.getExceptedRes());
				  qts.add(qt);
			  }
			  question.setAnswer(qts);
		   }
	   }
	   return questions;
	}
	
	/*
	 * 返回一个测试下所有已完成的报告
	 * 按完成时间倒序排列
	 * */
	public List getFinishedInvite(int testid){
		return inviteDao.getOrderInviteByTid(testid,1);
	}
	
	/*
	 * 返回测试下所有未完成的报告
	 * 按完成时间倒序排列
	 * */
	public List getUnfinishedInvite(int testid){
		return inviteDao.getOrderInviteByTid(testid,0);
	}
	
	
}
