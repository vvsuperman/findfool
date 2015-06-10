package zpl.oj.web.Rest.tcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.dao.TestuserDao;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.LabelService;
import zpl.oj.util.Constant.ExamConstant;


@Controller
@RequestMapping("/tuser")
public class TuserController {
	@Autowired
    TestuserDao testuserDao;
	@Autowired
	LabelService labelService;
	
	

	
	@RequestMapping(value = "/preregister", method = RequestMethod.POST)
	@ResponseBody
	public ResponseBase preRegister(@RequestBody Testuser testuser) {
		ResponseBase rb = new ResponseBase();
		
	
		testuserDao.insertTestuser(testuser);
		
		
		
	}

}
