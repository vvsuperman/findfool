package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.requestjson.RequestTestMeta;
import zpl.oj.model.responsejson.ResponseQuizDetail;

public interface QuizService {

	List<Quiz> getQuizByOwner(int owner);
	
	ResponseQuizDetail getQuizDetail(int tid);
	
	Quiz getQuizMetaInfoByID(int tid);
	
	Quiz getQuizMetaInfoByName(String name,int uid);
	
	void updateQuizMetaInfo(RequestTestMeta tm);
	
	boolean updateQuiz(int tid,List<Integer> qids);
	
	Quiz addQuiz(RequestTestMeta tm);
	
	List<QuizProblem> getQuizsByProblemId(Integer pid);
	
}
