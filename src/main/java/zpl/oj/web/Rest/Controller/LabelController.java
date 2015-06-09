package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.generic.LSTORE;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import zpl.oj.model.common.Label;
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
		List<JsonLabel> labels=new ArrayList<JsonLabel>();
		for(Labeltest lt:list){
			JsonLabel l=new JsonLabel();
			l.setLabelid(lt.getLabelid());
			String labelname=labelService.getLabelNameByLabelId(lt.getLabelid());
			l.setLabelname(labelname);
			l.setValue(lt.getIsSelected()==1?true:false);
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
//		String test="{\"labelid\":33,\"labelname\":\"sss\",\"value\":true}";
//		Gson gson=new Gson();
//		JsonLabel labels=gson.fromJson(test,JsonLabel.class);
//		Labels labels=new Labels();
//		labels.setLabelid(33);
//		labels.setLabelname("sss");
//		labels.setValue(true);
//		System.out.println(gson.toJson(labels));
		List<Map<String, Object>> labels=(List<Map<String,Object>>)map.get("labels");
		System.out.println(labels.toString());
		//List<Labels> labels=(List<Labels>)gson.fromJson(map.get("labels").toString(), Labels.class);
		rb.setState(1);
		return rb;
	}

	@RequestMapping(value="/addlabel")
	@ResponseBody
	public ResponseBase addLabel(@RequestBody Map<String,String> map){
		ResponseBase rb = new ResponseBase();
		Integer testid=Integer.parseInt(map.get("testid"));
		String newlabel=map.get("label");
		//如果该标签已经存在
		if(!labelService.isLableExist(newlabel)){
			labelService.insertNewLabel(0, newlabel);
		}
		Label label=labelService.getLabelByLabelName(newlabel);
		//如果标签未被加入到该test中
		if(!labelService.isLableTestExist(testid, label.getId()))
			labelService.insertLabelToLabelTest(testid, label.getId(), 0);
		rb.setState(1);
		return rb;
	}
	
	class JsonLabel{
		
		private Integer labelid;
		private String labelname;
		private Boolean value;
		public Integer getLabelid() {
			return labelid;
		}
		public void setLabelid(Integer labelid) {
			this.labelid = labelid;
		}
		public String getLabelname() {
			return labelname;
		}
		public void setLabelname(String labelname) {
			this.labelname = labelname;
		}
		public Boolean getValue() {
			return value;
		}
		public void setValue(Boolean value) {
			this.value = value;
		}
		@Override
		public String toString(){
			return "labelid="+labelid+",labelname="+labelname+",value="+value;
		}
	}
}
