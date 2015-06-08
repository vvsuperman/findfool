package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.LabelDao;
import zpl.oj.model.common.Labeltest;
import zpl.oj.service.LabelService;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private LabelDao labelDao;
	
	@Override
	public List<Integer> getSystemLabels() {
		// TODO Auto-generated method stub
		return labelDao.getSystemLabels();
	}

	@Override
	public void insertLabelToLabelTest( int testid, int labelid, int value) {
		// TODO Auto-generated method stub
		labelDao.insertLabelToLabelTest(testid, labelid, value);
	}

	@Override
	public String getLabelName(int labelid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelName(labelid);
	}
	
	@Override
	public List<Labeltest> getLabelsOfTest(int testid) {
		// TODO Auto-generated method stub
		return labelDao.getLabelsOfTest(testid);
	}

	@Override
	public void updateLabelValue(int testid, int labelid, int value) {
		// TODO Auto-generated method stub
		labelDao.updateLabelValue(testid, labelid, value);
	}


}
