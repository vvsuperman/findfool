package zpl.oj.service.imp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.CadProblemDao;
import zpl.oj.dao.CadTestDao;
import zpl.oj.dao.CandidateDao;
import zpl.oj.dao.ChallengeRuleDao;
import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.model.common.CadProblem;
import zpl.oj.model.common.CadTest;
import zpl.oj.model.common.Candidate;
import zpl.oj.model.common.ChallengeRule;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.request.Question;
import zpl.oj.model.request.QuestionTestCase;
import zpl.oj.util.Constant.ExamConstant;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.des.DESService;

@Service
public class CadQuizService {
	
	
	
	
	
	@Autowired
	private ProblemDao problemDao;
	
	@Autowired
	private CadProblemDao cadProDao;
	
	@Autowired
	private ProblemTestCaseDao problemTestCaseDao;
	
	@Autowired
	private CandidateDao candidateDao;
	
	@Autowired
	private CadTestDao cadTestDao;
	
	@Autowired
	private DESService desService;
	
	@Autowired
	private ChallengeRuleDao crDao;
	
	
	//判断用户是否有权限访问到该题库
	public boolean checkLevel(int testid, String email){
		ChallengeRule cr = crDao.findCRByTestid(testid);
		int level = getLevel(email);
		if(cr.getDomid()>level){
			return false;
		}else{
			return true;
		}
	} 
	
	
	//判断用户的等级 1为红，2为白，3为蓝
	public int getLevel(String email){
		List<ChallengeRule> easys = crDao.findCRById(ExamConstant.DP_RED);
		int easyScore =0;
		for(ChallengeRule easy:easys){
			if(cadTestDao.getCdByIds(easy.getTestid(), email)!=null){
				easyScore += cadTestDao.getCdByIds(easy.getTestid(), email).getScore();
			}
			
		}
		
		int mediumScore =0;
		if(easyScore > ExamConstant.DP_M){
			List<ChallengeRule> mediums = crDao.findCRById(ExamConstant.DP_WHITE);
			for(ChallengeRule medium:mediums){
				if(cadTestDao.getCdByIds(medium.getTestid(), email)!=null){
					mediumScore += cadTestDao.getCdByIds(medium.getTestid(), email).getScore();
				}
				
			}
			if(easyScore+mediumScore>ExamConstant.DP_H){
				//得分高于500
				return 3;
			}else{
				//得分高于300，小于500
				return 2;
			}
			
		}else{
			//分数未达标，只能做红色题
			return 1;
		}
		
		
	}
	
	//生成试卷,包括invite和cadproblem
	public CadTest genQuiz(String email, int  testid){
		int cadid = candidateDao.findUserByEmail(email).getCaid();
		//invite用来记录用户的做题信息，比如分数等
		CadTest cadTest = new CadTest();
		cadTest.setTestid(testid);
		cadTest.setCadid(cadid);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cadTest.setBegintime(sdf.format(new Date()));  //设置用户的开始时间
		cadTest.setScore(0);
		cadTest.setState(ExamConstant.INVITE_PUB);
		cadTestDao.insertCadTest(cadTest);
	
		cadTest = cadTestDao.getCdByIds(testid, email);
		int ctid = cadTest.getCtid();
		
		//canproblem记录用户具体的答题信息
		List<Problem> listProblem = problemDao.getProblemByTestid(testid);
		for(Problem problem:listProblem){
		    CadProblem cadProblem = new CadProblem();
				cadProblem.setCtid(ctid);
				cadProblem.setProblemid(problem.getProblemId());
				cadProblem.setCadid(cadid);
				cadProDao.insertCadProblem(cadProblem);
		}
		
		return cadTest;
	}
	
	
	//生成试卷
	public CadTest startQuiz(String email, int testid){
	    CadTest cadTest = cadTestDao.getCdByIds(testid, email);
	    
	  //生成试卷
	    if(cadTest == null){
	    	cadTest = genQuiz(email,testid);
	    }
	    
	  
	    //返回id
	    return cadTest;
	}
	
	
	//返回用户的分数、排名、答题数、总题数
		public Map getExtraInfo(CadTest cadTest){
			Map map = new HashMap<String, Object>();
			map.put("score",cadTest.getScore());		//分数
			map.put("rank",cadTestDao.getRank(cadTest.getScore(),cadTest.getTestid()));//排名
			map.put("nums", cadTest.getNums());//答题数
			map.put("pnums" , cadProDao.countProblems(cadTest.getCtid()));//总题数
			return map;
		}
		
	
	
	//从未做过列表返回一道题
	public Question getProblem(int ctid){
		List<CadProblem> cPros = cadProDao.findFreshPros(ctid);
		if(cPros.size() == 0){
			return null;
		}
		
		int num = (new Random()).nextInt(cPros.size());
	    CadProblem cadPro = cPros.get(num);
		
		
		Question q = null;
		Problem p =  problemDao.getProblem(cadPro.getProblemid());
		if(p == null)
			return null;
		q = new Question();
		q.setQid(p.getProblemId());
		q.setName(p.getTitle());
		q.setContext(p.getDescription());
		q.setType(p.getType());
		q.setSetid(p.getProblemSetId());
		q.setLevel(p.getLevel());
		q.setLimittime(p.getLimitTime());
		
		List<ProblemTestCase> cases = problemTestCaseDao.getProTestCasesRandom(p.getProblemId());
		List<QuestionTestCase> answer = new ArrayList<QuestionTestCase>();
		for(ProblemTestCase c :cases){
			QuestionTestCase qt = new QuestionTestCase();
			qt.setText(c.getArgs());
			qt.setCaseId(c.getTestCaseId());
			answer.add(qt);
		}
		q.setAnswer(answer);
		
		return q;
		
	}

	//回答问题
	public Map answerQuestion(CadTest cadTest, int problemid, String useranswer) {
		// TODO Auto-generated method stub
		
		Map rtMap = new HashMap<String, Object>();
		//获取problem    将cadproblem设置为已回答 对比答案  累加用户分数  累加用户回答题数  计算排名并返回
		CadProblem cadProblem = cadProDao.findByPidAndIid(cadTest.getCtid(), problemid);
		Problem problem = problemDao.getProblem(problemid);
		
		cadProblem.setUseranswer(useranswer);
		cadProDao.updateAnswerByIds(cadProblem);
		
		double plus = crDao.findCRByTestid(cadTest.getTestid()).getPlus();
		String rightanser = problem.getRightanswer();
		if(rightanser.equals(useranswer)){
			if(problem.getScore()!=0){
				cadTest.setScore(cadTest.getScore()+problem.getScore()*plus);
			}
			
		}else{
			if(problem.getNegative()!=0){
				cadTest.setScore(cadTest.getScore()-problem.getNegative()*plus);
			}
			
			
		}
		//答题数+1
		int nums = cadTest.getNums()+1;
		cadTest.setNums(nums);
		cadTestDao.updateCadTest(cadTest);
		//总题数
		int pnums = cadProDao.countProblems(cadTest.getCtid());  
		//题目回答完了,将cadTest设置为完成状态，否则取下一道题
		Question q = null;
		
		if(nums>=pnums/2){
			cadTest.setState(ExamConstant.INVITE_FINISH);
			cadTestDao.updateCadTest(cadTest);
			
		}else{//未回答完，取下一道题
			q= getProblem(cadTest.getCtid());
			
		}
		//排名
		int rank = cadTestDao.getRank(cadTest.getScore(),cadTest.getTestid());
	    Map cadInfo = new HashMap<String, Integer>();
	    cadInfo.put("nums", nums);
//	    cadInfo.put("pnums", pnums);
	    cadInfo.put("rank", rank);
	    cadInfo.put("score", cadTest.getScore());
		
		
		
		rtMap.put("cadInfo",cadInfo);
		rtMap.put("question",q);
		
		return rtMap;
		
	}
	
	
	

	public  Map prepareTest(int testid, String email) {
		// TODO Auto-generated method stub
		
		int involve = cadTestDao.getInvolve(testid);
	    CadTest cdTest =cadTestDao.getCdByIds(testid, email);
	    //用户还未开始做题
		Map <String ,Object> map = new HashMap<String, Object>();
	    int percent =0;
	    if(cdTest == null){
            map.put("percent",0);
            map.put("state", 0);
	    }else{
	    	 int rank = cadTestDao.getRank(cdTest.getScore(),cdTest.getTestid());
	 		if(involve!=0){
	 			percent = (involve-rank)*100/involve;
	 		}
	 	    
	 	    int state =0;
	 	    if(cdTest.getState() == ExamConstant.INVITE_FINISH){
	 	       	state = 1;
	 	    }
	 	
	 		map.put("percent", percent);
			map.put("state", state);
	    }
	    
	    List<Candidate> cads = cadTestDao.getFrontCad(testid);
	    map.put("cads", cads);
	
		
		
 		
		
		return map;
	}

    //生成公共测试链接，加密testid,以及slogan
	public String genPublicUrl(int testid, String slogan) {
		// TODO Auto-generated method stub
		String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
		return baseurl+"/#/publictest/"+desService.encode(testid+"|"+slogan+"|"+ExamConstant.PUBLIC_COMPANY);
	}

}
