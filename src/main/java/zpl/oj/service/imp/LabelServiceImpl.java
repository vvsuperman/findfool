package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.LabelDao;
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

}
