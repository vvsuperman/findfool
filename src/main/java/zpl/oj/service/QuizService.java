package zpl.oj.service;

import java.util.List;
import java.util.Map;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.QuizProblem;
import zpl.oj.model.common.RandomQuizSet;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestRandomTestMeta;
import zpl.oj.model.requestjson.RequestTestMeta;
import zpl.oj.model.responsejson.ResponseQuizDetail;

public interface QuizService {

	List<Quiz> getQuizByOwner(int owner);
	
	ResponseQuizDetail getQuizDetail(int tid);
	
	Quiz getQuizMetaInfoByID(int tid);
	
	Quiz getQuizMetaInfoByName(String name,int uid);
	
	void updateQuizMetaInfo(RequestTestMeta tm);
	
	Quiz updateQuiz(int tid,List<Integer> qids);
	
	Quiz addQuiz(RequestTestMeta tm);
	
	List<QuizProblem> getQuizsByProblemId(Integer pid);


	String addQuestionToQuiz(QuizProblem quizProblem);

	void deleteQuestionFromTest(QuizProblem quizProblem);
	
	Integer genQuiz(String quizName, int uid);

	void saveTime(int quizid, String openCamera, String startTime,
			String deadTime);

	Quiz addQuiz(Quiz quiz);

	void addRandomQuiz(List<RandomQuizSet> randomQuizs);

	Quiz getQuizByTestid(Integer testid);

	RequestRandomTestMeta getRandomQuizDetail(int quizid);


	
	
	
}
