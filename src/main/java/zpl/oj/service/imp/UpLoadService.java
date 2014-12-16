package zpl.oj.service.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.SetDao;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.Quiz;
import zpl.oj.util.Constant.ExamConstant;



public class UpLoadService{
	@Resource
	private QuizDao quizDao; 
	
	@Resource
	private ProblemDao problemDao;
	
	@Resource
	private SetDao setDao;
	
	@Resource
	private ProblemTestCaseDao problemTestCaseDao;
	
	private String getValuebyCell(HSSFCell cell){
		int cellType = cell.getCellType();
		switch (cellType){
     		case HSSFCell.CELL_TYPE_NUMERIC:
	    		return (int)cell.getNumericCellValue()+"";
     		case HSSFCell.CELL_TYPE_BOOLEAN:
     			if( cell.getBooleanCellValue() ){
     				return "1";
     			}else{
     				return "0";
     			}
     		case HSSFCell.CELL_TYPE_BLANK:
     			return "";
     	    default:
     	    	return cell.getStringCellValue();
     	         
		}
	}
	
//批量导入试题，若有错误则回滚, throw exception to rollback
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String batchImport(MultipartFile[] file)  throws  Exception  {
		// TODO Auto-generated method stub
		InputStream is = null;
		HSSFWorkbook workbook =null;  
  
		is = file[0].getInputStream();
		workbook = new HSSFWorkbook(is);
	   
	    for(int i=0;i < workbook.getNumberOfSheets();i++){
	    	
	    	HSSFSheet sheet = workbook.getSheetAt(i);
	    	String sheetName = workbook.getSheetName(i);
	    	HSSFRow rowName = sheet.getRow(0);
	    	
	    	//生成辅助对象，map中为对应列名的列号
	    	Map<String, Integer> map = new HashMap<String,Integer>();
	    	List<Integer> options = new ArrayList<Integer>();
	    	List<Integer> rights = new ArrayList<Integer>();
	    	int answerIndex =0; //excel中answer所在的位置
	    	//循环处理第一行，生成对应的列名、列号键值对。将option和right的列号分别存在一个list中
	    	//20141026 question中添加answer
	    	for(int j=0 ;j<rowName.getLastCellNum();j++){
	    		 String cellVal = rowName.getCell(j).getStringCellValue();
		    		 if(cellVal.equals(ExamConstant.QUESTION_OPTION)){
		    			 options.add(j);
		    		 }else if(cellVal.equals(ExamConstant.QUESTION_RIGHT)){
		    			 rights.add(j);
		    		 }else if(cellVal.equals(ExamConstant.QUESTION_ANSWER)){
		    			 answerIndex = j;
		    		 }else {
		    			 map.put(cellVal, j);
		    		 }
	    	}
	    	
	       //生成quiz,question及option对象，并存入数据库
	    	
	    	//quiz必须是先定义好的
	    	Quiz quiz = quizDao.getQuizByName(sheetName);
	    	if(quiz == null){
	    		 return null;
	    	}
	    	
	        ProblemSet set = setDao.getSetByName(sheetName);
	    	
	    	for(int j=1;j<sheet.getLastRowNum();j++){
	      		HSSFRow row = sheet.getRow(j);
	      		Problem problem = new Problem();
	      	    String questionContent = getValuebyCell(row.getCell(map.
	      				get(ExamConstant.QUESTION_CONTENT)));
	      	    
	      	    //若试题内容相同，则不做处理,去下一行
	      	    if(problemDao.getProblemByContent(questionContent)!=null){
	      	    	continue;
	      	    }
	      	    
	      		problem.setProblemSetId(quiz.getQuizid());
	      		problem.setDescription(questionContent);
	      		problem.setScore((int)Double.parseDouble(((getValuebyCell(row.getCell(map.
	      				get(ExamConstant.QUESTION_SCORE)))))));
	      		problem.setType(Integer.parseInt(getValuebyCell(row.getCell(map.
	      				get(ExamConstant.QUESTION_TYPE)))));
	      	
	      		
	      	/*	赶时间，tag先略过
	      	 * //处理tag
	      		Set<Tag> tagSet = new HashSet<Tag>(); 
	      		HSSFCell tagCell = row.getCell(map.get(ExamConstant.QUESTION_TAG));
	      		if(tagCell != null){
	      			String tags = tagCell.getStringCellValue();
		      		//若用户输入了tag
		      		if(tags!=null&&tags!=""){
		      			String[] tagList = null;
			      		//,可能是中文输入，取大的那个
			      		String[] tagList1 = tags.split(",");
			      		String[] tagList2 = tags.split("，");
			      		tagList = (tagList1.length>tagList2.length?tagList1:tagList2);
			      		
			      		for(String tagName:tagList){
			      			Tag tag= tagDao.getTagByName(tagName);
			      			if(tag != null){
			      				tagSet.add(tag);
			      			}else{
			      				tag = new Tag();
			      				tag.setTagName(tagName);
			      				tagDao.save(tag);
			      				tagSet.add(tag);
			      			}
			      		}
		      		}
	      		}
	      		*/
	      		String rightAnswer ="";
	      		
	      		//处理option
	      		for(int k=0;k<options.size();k++){
	      			ProblemTestCase option = new ProblemTestCase();
  	      			HSSFCell cell = row.getCell(options.get(k));
  	      			//若该选项不为空
	  	      	    if(getValuebyCell(cell).equals("") == false){
	  	      		   option.setArgs(getValuebyCell(cell));
	  	      		   if(getValuebyCell(row.getCell(rights.get(k))).equals("1")){
	  	      			   rightAnswer+="1";
	  	      		   }else{
	  	      			   rightAnswer+="0";
	  	      		   }
	  	      		   option.setProblemId(quiz.getQuizid());
	  	      		   problemTestCaseDao.insertProblemTestCase(option);;
	  	      		}
	      		}
	      		problem.setRightAnswer(rightAnswer);
	      		
	      		//处理answer,题目的解答
	      		HSSFCell cell = row.getCell(answerIndex);
	      		if(getValuebyCell(cell).equals("") == false){
	      			problem.setExplain(getValuebyCell(cell));
	      		}
	      		
	      		problem.setProblemSetId(quiz.getQuizid());
	      /*		problem.setOptions(optionSet);
	      		if(tagSet.size()!=0){
	      			problem.setTags(tagSet);
	      		}*/
	      		problemDao.insertProblem(problem);
	      	}
	    }
	
		is.close();
	    return "sucess";
	}
}