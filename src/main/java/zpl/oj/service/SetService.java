package zpl.oj.service;

import java.util.List;
import java.util.Set;

import zpl.oj.model.common.Domain;
import zpl.oj.model.common.ProblemSet;
import zpl.oj.model.requestjson.RequestUser;

public interface SetService {

	List<Domain> getSets(RequestUser request);
	
	boolean insertSet(ProblemSet s);

	List<ProblemSet> getSetsByPrivilege(int privilege);

}
