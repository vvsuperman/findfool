	package zpl.oj.service.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import zpl.oj.dao.ProblemDao;
import zpl.oj.dao.ProblemTagDao;
import zpl.oj.dao.ProblemTestCaseDao;
import zpl.oj.dao.QuizDao;
import zpl.oj.dao.SetDao;
import zpl.oj.dao.TagDao;
import zpl.oj.model.common.Problem;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.common.ProblemTestCase;
import zpl.oj.model.common.Quiz;
import zpl.oj.model.common.Tag;
import zpl.oj.util.Constant.ExamConstant;


@Service
public class UpLoadService{
	@Resource
	private QuizDao quizDao; 
	
	@Resource
	private ProblemDao problemDao;
	
	@Resource
	private SetDao setDao;
	
	@Resource
	private TagDao tagDao;
	
	@Resource
	private ProblemTestCaseDao problemTestCaseDao;
	
	@Resource
	private ProblemTagDao problemTagDao;
	
	private String getValue(HSSFCell cell) throws Exception{
		
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
	
	private String getValuebyCell(HSSFRow row, Map<String, Integer> map,String colName) throws  Exception{
		// TODO Auto-generated method stub
		HSSFCell cell = row.getCell(map.get(colName));
		if(cell == null){
			throw createException(row,colName);
		}
	    return getValue(cell);
	}

	/**
	 * @param row
	 * @return
	 */
	public Exception createException(HSSFRow row, String colName) {
		Exception e = new Exception();
		StackTraceElement ste = new StackTraceElement("UpLoadService", "getValuebyCell", "第"+(row.getRowNum()+1)+"行"+colName+"列出错",row.getRowNum() );
		StackTraceElement[] stes = {ste};
		e.setStackTrace(stes);
		return e;
	}
	
	private String getValuebyCell(HSSFRow row, int k) throws  Exception{
		// TODO Auto-generated method stub
		HSSFCell cell = row.getCell(k);
		if(cell == null){
			return null;
		}
		return getValue(cell);
	}

//批量导入试题，若有错误则回滚, throw exception to rollback
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String batchImport(MultipartFile[] file, int uid)  throws  Exception  {
		// TODO Auto-generated method stub
		InputStream is = null;
		HSSFWorkbook workbook =null;  
  
		is = file[0].getInputStream();
		workbook = new HSSFWorkbook(is);
	   
	    for(int i=0;i < workbook.getNumberOfSheets();i++){
	    	
	    	HSSFSheet sheet = workbook.getSheetAt(i);
	    	String sheetName = workbook.getSheetName(i);
	    	HSSFRow rowName = sheet.getRow(0);
	    	
	    	  //生成set,question及option对象，并存入数据库
	    	ProblemSet set = new ProblemSet();
	    	if(uid != -1){
	    		//用户自定义试题
	    		set.setProblemSetId(ExamConstant.CUSTOM_SET_ID);
	    	}else{
	    		//set为库试题
	    	    set = setDao.getSetByName(sheetName);
	  	        if(set == null){
	  	        	return null;
	  	        }
	    	}
	    	
	    	//生成辅助对象，map中为对应列名的列号
	    	Map<String, Integer> map = new HashMap<String,Integer>();
	    	List<Integer> options = new ArrayList<Integer>();
	    	List<Integer> rights = new ArrayList<Integer>();
	    	List<Integer> optionScore = new ArrayList<Integer>();
	    	//循环处理第一行，生成对应的列名、列号键值对。将option和right的列号分别存在一个list中
	    	//20141026 question中添加answer
	    	for(int j=0 ;j<rowName.getLastCellNum();j++){
	    		 String cellVal = rowName.getCell(j).getStringCellValue();
		    		 if(cellVal.equals(ExamConstant.QUESTION_OPTION)){  //选项
		    			 options.add(j);
		    		 }else if(cellVal.equals(ExamConstant.QUESTION_RIGHT)){//答案
		    			 rights.add(j);
		    		 }else if(cellVal.equals(ExamConstant.QUESTION_TSCORE)){//分数
		    			 optionScore.add(j);
		    		 }
		    		 else{
		    			 map.put(cellVal, j);
		    		 }
	    	}
	    	
   	      
	    	
	    	
	    	
	      
	    	
	    	for(int j=1;j<sheet.getLastRowNum()+1;j++){
	    		int type =0;//问题类型
	      		HSSFRow row = sheet.getRow(j);
	      		
	      		
	      		Problem problem = new Problem();
	      	 
	      	    String questionContent = getValuebyCell(row,map,ExamConstant.QUESTION_CONTENT);
	      	    //若试题内容为空，则不合规范，跳过
	      	    if(questionContent.equals("")){
	      	    	continue;
	      	    }
	      	    questionContent = addPTag(questionContent);
	      	   
	      	    
	      		problem.setProblemSetId(set.getProblemSetId());
	      		problem.setDescription(questionContent);
	      		
	      		try {
	      			type = Integer.parseInt(getValuebyCell(row,map,ExamConstant.QUESTION_TYPE));
	      			problem.setType(type);
				} catch (Exception e) {
					// TODO: handle exception
					throw createException(row,ExamConstant.QUESTION_TYPE);
				}
	      		
      			try {
	      			problem.setScore((int)Double.parseDouble(((getValuebyCell(row,map,ExamConstant.QUESTION_SCORE)))));
				} catch (Exception e) {
					// TODO: handle exception
					throw createException(row,ExamConstant.QUESTION_SCORE);
				}
	      		
	      		
	      		
	      		problem.setProblemSetId(set.getProblemSetId());
	      		if(uid!=-1){
	      			problem.setCreator(uid);
	      			problem.setBelong(uid);
	      		}else{
	      			problem.setCreator(0);
	      			problem.setBelong(0);
	      		}
	      		
	      		//编程题的时间和内存
	      		if(type == ExamConstant.PROGRAM){
	      		   problem.setLimitMem(ExamConstant.PROGRAM_MEM);
	      		   problem.setLimitTime(ExamConstant.PROGRAM_TIME);
	      		}
	      		
	      	 	
	        	//处理answer,题目的解答,answer可能为空
	      		if(row.getCell(map.get(ExamConstant.QUESTION_ANSWER))!=null&&row.getCell(map.get(ExamConstant.QUESTION_ANSWER)).equals("")==false){
	      			problem.setExplain(getValuebyCell(row,map,ExamConstant.QUESTION_ANSWER));
	      		}
	      	
	      		//处理level
	      		if(row.getCell(map.get(ExamConstant.QUESTION_LEVEL))!=null){
	      			try {
	      				problem.setLevel(Integer.parseInt(getValuebyCell(row,map,ExamConstant.QUESTION_LEVEL)));
					} catch (Exception e) {
						// TODO: handle exception
					}
	      			
	      		}
	      		
	      		//保存问题，获取id,最好能insert的同时获取主键，否则太难看了
	      		problemDao.insertProblem(problem);
	      		//questionContent可能相同，取id最大的那个
	      		problem = problemDao.getProblemByContent(questionContent).get(0);
	      		
	      		//处理tag
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
			      			Tag tag= tagDao.getTagByContext(tagName);
			      			if(tag != null){
			      				problemTagDao.insertTagProblem(tag.getTagId(), problem.getProblemId());
			      			}else{
			      				tag = new Tag();
			      				tag.setTagName(tagName);
			      				tagDao.insertTag(tagName);;
			      				tag = tagDao.getTagByContext(tagName);
			      				problemTagDao.insertTagProblem(tag.getTagId(), problem.getProblemId());	
			      			}
			      		}
		      		}
	      		}
	      		
	      		String rightAnswer ="";
	      		
	      		//处理option
	      		for(int k=0;k<options.size();k++){
	      			ProblemTestCase option = new ProblemTestCase();
	      			
  	      			HSSFCell optionCell = row.getCell(options.get(k));
  	      			//因为可能有5个选项的情况，因此需判断选项是否为空
  	      		
	  	      	    if(optionCell!=null && getValue(optionCell).equals("") == false){
	  	      		   option.setArgs(addPTag(getValue(optionCell)));
		  	      		if(type == ExamConstant.OPTION){  //选择
			  	      		 if(getValuebyCell(row,rights.get(k)).equals("1")){
			  	      			   rightAnswer+="1";
			  	      		   }else{
			  	      			   rightAnswer+="0";
			  	      		   }
			  	      		  
		  	      		}else if(type == ExamConstant.PROGRAM){	//编程
		  	      			//测试用例输出
			  	      		if( getValuebyCell(row,rights.get(k))!= null ){
			  	      		   option.setExceptedRes( getValuebyCell(row,rights.get(k)));	
			  	      		}
			  	      		if( getValuebyCell(row,optionScore.get(k))!= null && getValuebyCell(row,optionScore.get(k)).equals("") == false ){
			  	      		   option.setScore( Integer.parseInt((getValuebyCell(row,optionScore.get(k)))));	
			  	      		}
		  	      		}
		  	      		
		  	      	   option.setProblemId(problem.getProblemId());
	  	      		   problemTestCaseDao.insertProblemTestCase(option);
	  	      		  
	  	      		}
	      		}
	      		if(type == ExamConstant.OPTION){
	      			problem.setRightanswer(rightAnswer);
	      		}
	     
	      		problemDao.updateProblemRightAnswer(problem);
	      	}
	    }
	
		is.close();
	    return "sucess";
	}
	

	//给内容加上html<p>标签，以符合simditor的标准
	private String addPTag(String questionContent) {
		// TODO Auto-generated method stub
//		String rtStr = questionContent.replace("\n", "</p>\n<p>");
//		return "<p>"+rtStr+"<br></p>";
		String rtStr = questionContent.replace("\n", "<br>");
		return rtStr;
	}
}