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
	public List<School> getSchoolsByName1(String name) {
		List<School> schools=schoolDao.getSchoolsByName1(name);
		return schools;
	}

}
