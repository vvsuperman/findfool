package zpl.oj.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class ExamConstant{
	
	
	//共有测试类别：是公司发出来的还是系统发出来的
	public final static int PUBLIC_COMPANY = 1;
	public final static int PUBLIC_SYSTEM = 0;
	
	//label名称的常量
	public final static String LABEL_EMAIL = "email";
	public final static String LABEL_PWD = "pwd";
	public final static String LABEL_TEL = "tel";
	
	
	
	//短信模板
	public final static String SMS_USERNAME = "XFTB702047";
	public final static String SMS_PWD ="fw123456";
	public final static String SMS_TEMPID_REMIND = "MB-2015030640";
	public final static String SMS_TEMPID_INVITE = "MB-2015060646";
	public final static String SMS_ADDRESS= "http://mssms.cn:8000/msm/sdk/http/sendsms.jsp";
	
	
	//统一登陆来源
	
	 public final static String SOURCE_MD = "mingdao";
	
	
	
	public final static int PROGRAM_MEM = 128;
	public final static int PROGRAM_TIME = 128;
	
	
	public final static int OPTION = 1;
	public final static int PROGRAM = 2;
	public final static int ESSAY =3;
	
	public final static int INVITE_PUB =0;    //邀请已发送
	public final static int INVITE_PROGRESS=1;//测试进行中
	public final static int INVITE_FINISH =2; //测试已完成
	
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

