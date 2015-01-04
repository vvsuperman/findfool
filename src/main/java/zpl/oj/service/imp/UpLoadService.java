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
			Exception e = new Exception();
			System.out.print("error in row..........................."+cell.getRow().getRowNum());
			StackTraceElement ste = new StackTraceElement("UpLoadService", "getValuebyCell", "", cell.getRow().getRowNum());
			StackTraceElement[] stes = {ste};
			e.setStackTrace(stes);
			throw e;
		}
	    return getValue(cell);
	}
	
	private String getValuebyCell(HSSFRow row, int k) throws  Exception{
		// TODO Auto-generated method stub
		HSSFCell cell = row.getCell(k);
		return getValue(cell);
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
	    	//循环处理第一行，生成对应的列名、列号键值对。将option和right的列号分别存在一个list中
	    	//20141026 question中添加answer
	    	for(int j=0 ;j<rowName.getLastCellNum();j++){
	    		 String cellVal = rowName.getCell(j).getStringCellValue();
		    		 if(cellVal.equals(ExamConstant.QUESTION_OPTION)){
		    			 options.add(j);
		    		 }else if(cellVal.equals(ExamConstant.QUESTION_RIGHT)){
		    			 rights.add(j);
		    		 }else{
		    			 map.put(cellVal, j);
		    		 }
	    	}
	    	
   	        //生成set,question及option对象，并存入数据库
	    	
	    	//set必须是先定义好的
	        ProblemSet set = setDao.getSetByName(sheetName);
	        if(set == null){
	        	return null;
	        }
	    	
	    	for(int j=1;j<sheet.getLastRowNum();j++){
	      		HSSFRow row = sheet.getRow(j);
	      		Problem problem = new Problem();
	      	    String questionContent = getValuebyCell(row,map,ExamConstant.QUESTION_CONTENT);
	      	    questionContent = addPTag(questionContent);
	      	   
	      	    
	      		problem.setProblemSetId(set.getProblemSetId());
	      		problem.setDescription(questionContent);
	      		problem.setScore((int)Double.parseDouble(((getValuebyCell(row,map,ExamConstant.QUESTION_SCORE)))));
	      		problem.setType(Integer.parseInt(getValuebyCell(row,map,ExamConstant.QUESTION_TYPE)));
	      		problem.setProblemSetId(set.getProblemSetId());
	      		problem.setBelong(0);
	      		
	      	 	
	        	//处理answer,题目的解答,answer可能为空
	      		if(row.getCell(map.get(ExamConstant.QUESTION_ANSWER))!=null){
	      			problem.setExplain(getValuebyCell(row,map,ExamConstant.QUESTION_ANSWER));
	      		}
	      	
	      		//处理level
	      		if(row.getCell(map.get(ExamConstant.QUESTION_LEVEL))!=null){
	      			problem.setLevel(Integer.parseInt(getValuebyCell(row,map,ExamConstant.QUESTION_LEVEL)));
	      		}
	      		
	      		//保存问题，获取id,最好能insert的同事获取主键，否则太难看了
	      		problemDao.insertProblem(problem);
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
	  	      		   option.setArgs(getValue(optionCell));
	  	      		   if(getValuebyCell(row,rights.get(k)).equals("1")){
	  	      			   rightAnswer+="1";
	  	      		   }else{
	  	      			   rightAnswer+="0";
	  	      		   }
	  	      		   option.setProblemId(problem.getProblemId());
	  	      		   problemTestCaseDao.insertProblemTestCase(option);;
	  	      		}
	      		}
	      		problem.setRightAnswer(rightAnswer);
	      		
	     
	      		problemDao.updateProblemRightAnswer(problem);
	      	}
	    }
	
		is.close();
	    return "sucess";
	}
	

	//给内容加上html<p>标签，以符合simditor的标准
	private String addPTag(String questionContent) {
		// TODO Auto-generated method stub
		String rtStr = questionContent.replace("\n", "</p>\n<p>");
		return "<p>"+rtStr+"<br></p>";
	}
}