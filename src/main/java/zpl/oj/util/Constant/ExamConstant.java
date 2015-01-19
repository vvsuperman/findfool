package zpl.oj.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class ExamConstant{
	public final static int PROGRAM_MEM = 128;
	public final static int PROGRAM_TIME = 128;
	
	
	public final static int OPTION = 1;
	public final static int PROGRAM = 2;
	public final static int ESSAY =3;
	
	public final static int INVITE_PUB =0;    //邀请已发送
	public final static int INVITE_FINISH =1; //测试已完成
	
	//试题导入时excel定义的列名
	public static final String QUESTION_TYPE = "type";
	public static final String QUESTION_SCORE = "score";
	public static final String QUESTION_TAG = "tag";
	public static final String QUESTION_CONTENT = "questioncontent";
	public static final String QUESTION_OPTION = "option";
	public static final String QUESTION_RIGHT = "right";
	public static final String QUESTION_TSCORE = "optionscore";
	public static final String QUESTION_TESTCASE = "testcase";
	public static final String QUESTION_RESULT = "result";
	public static final String QUESTION_TESTSCORE = "testscore";
	public static final String QUESTION_REPONAME = "reponame";
	public static final String QUESTION_ANSWER = "answer";
	public static final String QUESTION_LEVEL = "level";
	
	//试题类型
	public static final String QUESTION_TYPE_OPTION = "option";
	public static final String QUESTION_TYPE_ESSAY ="essay";
	public static final String QUESTION_TYPE_PROGRAM ="pragram";
	
	//自定义题库，不列入统计
	public static final int CUSTOM_SET_ID = 0;
	public static final String CUSTOM_SET_NAME = "custom";
	
	//输入和输出
	public static final String INPUT_STR = "输入";
	public static final String OUTPUT_STR = "输出";
	public static final String BR = "<br>";
	
	public static final Map LEVEL_MAP = new HashMap<Integer,String>(){{put(1,"简单");put(2,"普通");put(3,"困难");}};
	
	
}	

