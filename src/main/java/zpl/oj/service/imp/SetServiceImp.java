package zpl.oj.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.DomainDao;
import zpl.oj.dao.SetDao;
import zpl.oj.model.common.Domain;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.service.SetService;

@Service
public class SetServiceImp implements SetService {

	@Autowired
	private SetDao setDao;
	@Autowired
	private DomainDao domainDao;
	
	@Override
	public List<Domain> getSets() {
		List<Domain> domains = domainDao.getAllDomain();
		for(Domain domain: domains){
			List<ProblemSet> sets = setDao.getSetByDomainId(domain.getDomainId());
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

}
