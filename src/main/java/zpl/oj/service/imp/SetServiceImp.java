package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.SetDao;
import zpl.oj.model.common.Set;
import zpl.oj.service.SetService;

@Service
public class SetServiceImp implements SetService {

	@Autowired
	private SetDao setDao;
	@Override
	public List<Set> getSets() {
		return setDao.getSets();
	}

	@Override
	public boolean insertSet(Set s) {
		try{
			setDao.insertSet(s);
		}catch(Exception e){
			return false;
		}
		return true;
	}

}
