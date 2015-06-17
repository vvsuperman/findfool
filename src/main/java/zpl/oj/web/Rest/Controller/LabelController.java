package zpl.oj.web.Rest.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import zpl.oj.model.common.Invite;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;
import zpl.oj.model.responsejson.ResponseBase;
import zpl.oj.service.InviteService;
import zpl.oj.service.LabelService;

import com.google.gson.Gson;

@Controller
@RequestMapping("/label") 
public class LabelController {

	@Autowired
	private LabelService labelService;
	@Autowired
	private InviteService inviteService;
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
			l.setIsSelected((lt.getIsSelected()==1?true:false));
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
		Gson gson=new Gson();

		String labels=map.get("labels").toString();
		labels=labels.substring(2,labels.length()-2);
		String[] lArray=labels.split("\\}, \\{");
		for(int i=0;i<lArray.length;i++){
			lArray[i]="{"+lArray[i]+"}";
			JsonLabel label=gson.fromJson(lArray[i],JsonLabel.class);
			labelService.updateLabelTest(testid, label.getLabelid(), label.getIsSelected()?1:0);
		}
		rb.setState(1);
		return rb;
	}

	//用户添加新的标签
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
		if(!labelService.isLableTestExist(testid, label.getId())){
			labelService.insertIntoLabelTest(testid, label.getId(), 0);
			//在labeluser中，对于该test所对应的invite，都插入一条数据
			List<Invite> invites=inviteService.getInvitesByTid(testid);
			for(Invite invite:invites){
				labelService.insertIntoLabelUser(invite.getIid(), label.getId(), "");
			}
		}
		rb.setState(1);
		return rb;
	}
	
	class JsonLabel{
		
		private Integer labelid;
		private String labelname;
		private Boolean isSelected;
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
		public Boolean getIsSelected() {
			return isSelected;
		}
		public void setIsSelected(Boolean isSelected) {
			this.isSelected = isSelected;
		}
	}
}
