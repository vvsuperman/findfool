package zpl.oj.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.LabelDao;
import zpl.oj.dao.TestuserDao;
import zpl.oj.model.common.Label;
import zpl.oj.model.common.LabelUser;
import zpl.oj.model.common.Labeltest;
import zpl.oj.service.LabelService;
import zpl.oj.util.json.JsonLabel;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Autowired
	private TestuserDao testuserDao;
	
	@Override
	public List<Label> getSystemLabels() {
		// TODO Auto-rgenerated method stub
		return labelDao.getSystemLabels();
	}
	
	/**
	 * @param testid
	 * @return
	 */
	@Override
	public List<JsonLabel> getTestLabels(Integer testid) {
		List<Labeltest> list=getLabelsOfTest(testid);
		List<JsonLabel> labels=new ArrayList<JsonLabel>();
		for(Labeltest lt:list){
			JsonLabel l=new JsonLabel();
			l.setLabelid(lt.getLabelid());
			String labelname=getLabelNameByLabelId(lt.getLabelid());
			l.setLabelname(labelname);
		    l.setLabeltype(lt.getType());
			l.setIsSelected((lt.getIsSelected()==1?true:false));
			labels.add(l);
		}
		return labels;
	}

	@Override
	public void insertIntoLabelTest( int testid, int labelid, int isSelected) {
		// TODO Auto-generated method stub
		labelDao.insertIntoLabelTest(testid, labelid, isSelected);
	}

	@Override
	public String getLabelNameByLabelId(int labelid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelNameByLabelId(labelid);
	}
	
	@Override
	public List<Labeltest> getLabelsOfTest(int testid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelsOfTest(testid);
	}

	@Override
	public void updateLabelTest(int testid, int labelid, int isSelected) {
		// TODO Auto-generated method stub
		labelDao.updateLabelTest(testid, labelid, isSelected);
	}

	@Override
	public boolean isLableExist(String labelname) {
		List<Label> list=labelDao.getLabelByLabelName(labelname);
	
		if(list.size()==1)
			return true;
		
		else return false;
	}

	@Override
	public void insertNewLabel(int type, String labelname) {
		labelDao.insertNewLabel(1, labelname);		
	}

	@Override
	public Label getLabelByLabelName(String labelname) {
		// TODO Auto-generated method stub
		if(isLableExist(labelname))
		return labelDao.getLabelByLabelName(labelname).get(0);
		else return null;
	}

	@Override
	public boolean isLableTestExist(Integer testid,Integer labelid) {
		List<Labeltest> labeltests=labelDao.getLabelTestByTestidAndLabelName(testid,labelid);
		if(labeltests.size()>=1) return true;
		else return false;
	}

	@Override
	public void insertIntoLabelUser(Integer inviteid, int labelid, String value) {
		// TODO Auto-generated method stub
		labelDao.insertIntoLabelUser(inviteid, labelid, value);
	}

	@Override
	public Label getLabelById(Integer labelid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelById(labelid);
	}

	@Override
	public LabelUser getLabelUserByIidAndLid(Integer inviteid, Integer labelid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelUserByIidAndLid(inviteid, labelid);
	}

	@Override
	public void updateLabelUser(int testid, int labelid, String value) {
		// TODO Auto-generated method stub
		labelDao.updateLabelUser(testid, labelid, value);
	}

	@Override
	public List<LabelUser> getLabelUserByIid(Integer inviteid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelUserByIid(inviteid);
	}


	@Override
	public Object findLableId(String ln) {
		return  labelDao.findLableId(ln);
		
	}

	@Override
	public void deleteLable(Integer id,Object testid) {
	
		labelDao.deleteLable(id,testid);			
	}



	private String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer findLableType(String ln) {
		// TODO Auto-generated method stub
		return labelDao.findLableType(ln);
	}





}
