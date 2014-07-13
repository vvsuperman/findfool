package zpl.oj.web.Rest.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import zpl.oj.model.responsejson.ResponseBase;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@RequestMapping(value="/invite")
	@ResponseBody
	public ResponseBase uploadInvite(@RequestParam MultipartFile myfile, HttpServletRequest request){
		ResponseBase rs = new ResponseBase();
//		if(myfile.isEmpty()){  
//            System.out.println("文件未上传");  
//        }else{  
//            System.out.println("文件长度: " + myfile.getSize());  
//            System.out.println("文件类型: " + myfile.getContentType());  
//            System.out.println("文件名称: " + myfile.getName());  
//            System.out.println("文件原名: " + myfile.getOriginalFilename()); 
//            System.out.println("========================================");  
//            HSSFWorkbook wb = null;
//            try {
//                wb = new HSSFWorkbook(myfile.getInputStream());
//                // logger.debug(wb.getNumberOfSheets());
//                HSSFSheet sheet = wb.getSheetAt(0);
//                for(int i = sheet.getFirstRowNum();i<=sheet.getLastRowNum();i++){
//                    HSSFRow row = sheet.getRow(i);
//                    Iterator cells = row.cellIterator();
//                    while(cells.hasNext()){
//                        HSSFCell cell = (HSSFCell) cells.next();   
//                        cell.getStringCellValue();
//                    }
//                }
//                
//                wb.getNumberOfSheets();
////                return sheet.getFirstRowNum();
//            } catch (Exception e) {
//                // throw new
//                // BusinessServiceException("未知原因！保存Excel文件时，请不要将鼠标最终定位在Excel中的可以下拉选值的列上。");
//            }            
//        }  
		return rs;
		
	}

}
