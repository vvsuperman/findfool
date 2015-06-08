package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.Labeltest;
import zpl.oj.model.requestjson.RequestMessage;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.model.responsejson.ResponseMessage;
import zpl.oj.service.LabelService;

@Controller
@RequestMapping("/label") 
public class LabelController {

	@Autowired
	private LabelService labelService;
	@RequestMapping(value="/getlabels")
	@ResponseBody
	public ResponseBase getLabels(@RequestBody Map<String,Integer> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=map.get("testid");
		List<Labeltest> list=labelService.getLabelsOfTest(testid);
		List<Labels> labels=new ArrayList<Labels>();
		for(Labeltest lt:list){
			Labels l=new Labels();
			l.setLabelid(lt.getLabelid());
			String labelname=labelService.getLabelName(lt.getLabelid());
			l.setLabelname(labelname);
			l.setValue(lt.getValue());
			labels.add(l);
		}
		rb.setState(1);
		rb.setMessage(labels);
		return rb;
	}
	@RequestMapping(value="/saveconfig")
	@ResponseBody
	public ResponseBase saveConfig(@RequestBody Map<String,Object> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=Integer.parseInt(map.get("testid").toString());
		List list=new ArrayList();
		Iterator iter=map.entrySet().iterator();
		while()
		for(Labels lt:list){
			labelService.updateLabelValue(testid, lt.getLabelid(), lt.getValue());;
		}
		rb.setState(1);
		return rb;
	}

	@RequestMapping(value="/addlabel")
	@ResponseBody
	public ResponseBase addLabel(@RequestBody Map<String,Object> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=Integer.parseInt(map.get("testid").toString());
		List<Labels> list=(List<Labels>)map.get("labels");
		
		for(Labels lt:list){
			labelService.updateLabelValue(testid, lt.getLabelid(), lt.getValue());;
		}
		rb.setState(1);
		return rb;
	}
	
	class Labels{
		
		private int labelid;
		private String labelname;
		private int value;
		
		public int getLabelid() {
			return labelid;
		}
		public void setLabelid(int labelid) {
			this.labelid = labelid;
		}
		public String getLabelname() {
			return labelname;
		}
		public void setLabelname(String labelname) {
			this.labelname = labelname;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
	}
}
