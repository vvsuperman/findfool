package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.SchoolDao;
import zpl.oj.model.common.School;
import zpl.oj.service.SchoolService;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public List<School> getAllSchools() {
		List<School> schools=schoolDao.getAllSchools();
		return schools;
	}

	@Override
	public School getSchoolByName(String name) {
		return schoolDao.getSchoolByName(name);
	}
	
	@Override
	public List<School> getSchoolsByName(String name) {
		List<School> schools=schoolDao.getSchoolsByName(name);
		return schools;
	}

	@Override
	public boolean addSchoolByName(String name) {
		int code=schoolDao.getMaxCode();
		//用户添加的学校编号从50000开始
		code=code>50000?code:50000;
		code=code+1;
		schoolDao.addSchoolByName(name,code);
		return false;
	}

	@Override
	public int getMaxCode() {
		// TODO Auto-generated method stub
		return schoolDao.getMaxCode();
	}


}
