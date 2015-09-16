package zpl.oj.service.imp;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.DomainDao;
import zpl.oj.dao.SetDao;
import zpl.oj.model.common.Domain;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestUser;
import zpl.oj.service.SetService;
import zpl.oj.service.user.inter.UserService;

@Service
public class SetServiceImp implements SetService {

	@Autowired
	private SetDao setDao;
	@Autowired
	private DomainDao domainDao;
	@Autowired
	private UserService userService;
	
	@Override
	public List<Domain> getSets(RequestUser request) {
		
		 User user =userService.getUserById(request.getUid());
		List<Domain> domains = domainDao.getAllDomain();
		for(Domain domain: domains){
			//访问userset中存在的set
			List<ProblemSet> sets = setDao.getSetByDomainIdAndprivilege(domain.getDomainId(),user.getPrivilege());
			domain.setProblemSets(sets);
		}
		return domains;
	}

	@Override
	public boolean insertSet(ProblemSet s) {
		try{
			setDao.insertSet(s);
		}catch(Exception e){
			return false;
		}
		return true;
	}

	@Override
	public List<ProblemSet> getSetsByPrivilege(int privilege) {
		
		return setDao.getSetsByPrivilege(privilege);
	}





}
