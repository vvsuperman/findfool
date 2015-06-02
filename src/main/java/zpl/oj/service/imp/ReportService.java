package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qiniu.util.Auth;

import zpl.oj.dao.ImgUploadDao;
import zpl.oj.dao.InviteDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.SetDao;
import zpl.oj.dao.SolutionRunDao;
import zpl.oj.dao.TuserProblemDao;
import zpl.oj.model.common.ImgForDao;
import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.ResultInfo;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.common.TuserProblem;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.model.responsejson.ResponseInvite;
import zpl.oj.model.responsejson.ResponseProDetail;
import zpl.oj.model.solution.SolutionRun;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;


@Service
public class ReportService {
	
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
	@Autowired
	private SolutionRunDao solutionRunDao;
	
	@Autowired
	private ImgUploadDao imgUploadDao;
	
	/*
	 * 返回用户的图片
	 * */
	public List<ImgForDao> getUserPhotos(Invite invite){
		List<ImgForDao> imgList=imgUploadDao.getImgsByIid(invite.getIid());
		
		String imgHome=(String)PropertiesUtil.getContextProperty("ImgUploadHome");
		String accessKey=(String)PropertiesUtil.getContextProperty("qiniuAccessKey");
		String secretKey=(String)PropertiesUtil.getContextProperty("qiniuSecretKey");
		
		
		for(ImgForDao img:imgList){
			String location=img.getLocation();
			
			location=imgHome+location;
			
			Auth auth=Auth.create(accessKey, secretKey);
			location=auth.privateDownloadUrl(location);
			img.setLocation(location);
		}
		return imgList;
	}
	
	/*
	 * 计算用户的分数，包括试题分数和用户的得分，并存入invite表
	 * */
	public String countUserAndTotalScore(Invite invite) {
		// TODO Auto-generated method stub
		//选择题总分
		//tuserProblemDao.getTotalScore(invite.getIid())可能返回空
		Object o=tuserProblemDao.getTotalScore(invite.getIid());
		if(o==null) return "0";
		int totalScore = (int)o;
		//选择题得分
		int mScore = tuserProblemDao.getUserScore(invite);
		//编程题总分
		
		//编程题得分
		 Map<String,Integer> map= getProblemScore(invite);
		Integer pScore = map.get("pScore");
		Integer pTotalScore = map.get("pTotalScore");
		int userScore=0;
		if(pScore!=null){
			 userScore = mScore + pScore;
		}
		if(pTotalScore != null){
			 totalScore +=pTotalScore;
		}
		
		Invite myInvite = inviteDao.getInviteById(invite.getIid());
		myInvite.setTotalScore(totalScore);
		myInvite.setScore(userScore);
		inviteDao.updateInvite(myInvite);
		return userScore+"/"+totalScore;
	}
	
	
	private Map getProblemScore(Invite invite) {
		// 循环处理编程题并生成得分
		List<TuserProblem> tuserProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
		int pScore = 0;
		int pTotalScore = 0;
		for(TuserProblem tuserProblem: tuserProblems){
			if(tuserProblem.getType() == ExamConstant.PROGRAM){
				if(tuserProblemDao.sumProblemScore(tuserProblem.getSolutionId())!=null){
					pScore+=tuserProblemDao.sumProblemScore(tuserProblem.getSolutionId());
				}
				if(tuserProblemDao.sumProTotalScore(tuserProblem.getProblemid())!=null){
					pTotalScore+=tuserProblemDao.sumProTotalScore(tuserProblem.getProblemid());
				}
				
			}
		}
		Map<String,Integer> rtMap = new HashMap<String, Integer>();
		rtMap.put("pScore", pScore);
		rtMap.put("pTotalScore", pTotalScore);
		return rtMap;
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
		Invite socreInvite = inviteDao.getInvitesByIds(invite.getTestid(), invite.getUid());
		List<Invite> invites = inviteDao.getInviteByTid(invite.getTestid());
		List<Integer> scores = new ArrayList<Integer>();
		for(Invite tmp:invites){
			scores.add(tmp.getScore());
		}
		Collections.sort(scores);
		int i=scores.size()-1;
		for(;i>=0;i--){
			if(scores.get(i)==socreInvite.getScore()){
				i++;
				break;
			}
		}
		return i+"/"+scores.size();
	}
	
	
	public Testuser getUserById(int tuid){
		return tuserProblemDao.getTestUserById(tuid);
	}
	
	
	private void sumMap(Map<Integer,Integer> map, int id){
		if(map.containsKey(id)){
			map.put(id, map.get(id)+1); //若包含，则+1
		}else{
			map.put(id, 1); 					//若还没有，则设为1
		}
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
		List<TuserProblem> tProblems = tuserProblemDao.findProblemByInviteId(invite.getIid());
		
		//按照setid进行划分，将总的答题数放入setMap，答对的题数放入rightmap
		Map<Integer, Integer> setMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rightMap = new HashMap<Integer,Integer>();
		//难度划分的set
		Map<Integer, Integer> levelMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> rightLevelMap = new HashMap<Integer,Integer>();
		
		for(TuserProblem tProblem:tProblems){
			int setid = tProblem.getSetid();
			//选择题
			int level = tProblem.getLevel();
			if(setid != ExamConstant.CUSTOM_SET_ID && tProblem.getType() == ExamConstant.OPTION){  //选择题才比较。若为自定义试题，则不列入统计
				sumMap(setMap,setid);
				sumMap(levelMap,level);
				if(tProblem.getRightanswer().equals(tProblem.getUseranswer())){
					sumMap(rightMap,setid);
					sumMap(rightLevelMap,level);
				}
			//编程题
			}else if(setid != ExamConstant.CUSTOM_SET_ID && tProblem.getType() == ExamConstant.PROGRAM){
				//编程题得分统计
				int solutionId = tProblem.getSolutionId();
				List<ResultInfo> results = tuserProblemDao.getProResult(solutionId);
				for(ResultInfo result: results){
					//总题目累加
					sumMap(setMap,setid);
					//正确的测试用例累加
					if(result.getScore()>0){
						sumMap(rightMap,setid);
					}
				}
			}
			
			
			
			
		}
		
		Map<String, Object> rtSetMap = genResultMap(setMap, rightMap,1);
		Map<String, Object> rtLevelMap = genResultMap(levelMap, rightLevelMap, 2);
		Map<String, Object> rtMap = new HashMap<String, Object>();
		rtMap.put("setRadar", rtSetMap);
		rtMap.put("levelRadar", rtLevelMap);
		
		return rtMap;
	}


	/**
	 * @param setMap
	 * @param rightMap
	 * @return
	 */
	private Map<String, Object> genResultMap(Map<Integer, Integer> setMap,
			Map<Integer, Integer> rightMap, int flag) {
		//生成返回数据
		Map<String, Object> rtMap = new HashMap<String,Object>();
		rtMap.put("name", new ArrayList<String>());
		rtMap.put("content", new ArrayList<String>());
		rtMap.put("faceproblem",  new ArrayList<String>());
		
		
		//构建二维数组，使用parentList二维数组来储存元素
		ArrayList parentList = new ArrayList<List<Integer>>();
		ArrayList scoreList = new ArrayList<Integer>();      //总分
		ArrayList userScoreList = new ArrayList<Integer>();  //用户得分
		parentList.add(scoreList);
		parentList.add(userScoreList);
		rtMap.put("val", parentList);
		
		Iterator iter = setMap.entrySet().iterator();
		//处理set名字的雷达图
		if(flag == 1){
			while(iter.hasNext()){
				Map.Entry entry = (Map.Entry) iter.next();
				int setid = (Integer)entry.getKey();
				int val = (Integer)entry.getValue();
				int userVal =0;
				if(rightMap.get(setid)!=null){
					userVal = rightMap.get(setid);
				}
				ProblemSet set = setDao.getSet(setid);
				String setName = set.getComment();
				String setContent = set.getContent();
				String faceproblem = set.getFaceproblem();
				
				((ArrayList)rtMap.get("name")).add(setName);
				((ArrayList)rtMap.get("content")).add(setContent);
				((ArrayList)rtMap.get("faceproblem")).add(faceproblem);
				scoreList.add(val);
				userScoreList.add(userVal);
			}
		//处理level名字的雷达图
		}else if(flag == 2){
			while(iter.hasNext()){
				Map.Entry entry = (Map.Entry) iter.next();
				int level = (Integer)entry.getKey();
				int val = (Integer)entry.getValue();
				int userVal =0;
				if(rightMap.get(level)!=null){
					userVal = rightMap.get(level);
				}
				String levelName =(String) ExamConstant.LEVEL_MAP.get(level); 
				((ArrayList)rtMap.get("name")).add(levelName);
				scoreList.add(val);
				userScoreList.add(userVal);
			}
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
	 * 返回一个测试下所有未完成和已完成的测试
	 * 按完成时间倒序排列
	 * */
	public List<ResponseInvite> getInviteReport(int testid){
		return inviteDao.getOrderInviteByTid(testid);
	}

	//生成编程题的用户答案
	public ResponseProDetail getProDetail(TuserProblem tProblem) {
		ResponseProDetail proDetail = new ResponseProDetail();
		// TODO Auto-generated method stub
		TuserProblem problem = tuserProblemDao.findByPidAndIid(tProblem.getInviteId(), tProblem.getProblemid());
		
		List<ResultInfo> resultInfos = tuserProblemDao.getProResult(problem.getSolutionId());
		List<ProblemTestCase> rtSet = new ArrayList<ProblemTestCase>();
		if(resultInfos!=null){
			for(ResultInfo rs: resultInfos){
				ProblemTestCase pt = new ProblemTestCase();
			   	String input = ExamConstant.INPUT_STR + rs.getTestCase() + ExamConstant.BR;
			   	String output = ExamConstant.OUTPUT_STR + rs.getTestCaseExpected() + ExamConstant.BR;
			   	pt.setArgs(input+output);
			   	pt.setScore(rs.getScore());
			   	rtSet.add(pt);
			}
		}
		
		SolutionRun solution =  solutionRunDao.getSolutionById(problem.getSolutionId());
		if(solution!=null){
			proDetail.setUseranswer(solution.getSolution());
			proDetail.setLanguage(solution.getLanguage());
			proDetail.setResultInfos(rtSet);
		}else{
			proDetail.setUseranswer("");
			proDetail.setLanguage(1);
			proDetail.setResultInfos(null);
		}
		
		
		return proDetail;
	}
	
	
	
}
