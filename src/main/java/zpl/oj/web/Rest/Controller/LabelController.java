package zpl.oj.web.Rest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.requestjson.RequestMessage;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.service.LabelService;

@Controller
@RequestMapping("/label") 
public class LabelController {

	@Autowired
	private LabelService labelService;
	@RequestMapping(value="/add")
	@ResponseBody
	public ResponseBase userLogin(@RequestBody RequestMessage request){
		ResponseBase rb = new ResponseBase();
		ResponseMessage msg = new ResponseMessage();


		msg.setMsg("ok");
		rb.setState(1);
		
		rb.setMessage(msg);
		return rb;
	}
}
