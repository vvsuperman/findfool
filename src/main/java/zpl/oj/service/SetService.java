package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Domain;
import zpl.oj.model.common.ProblemSet;

public interface SetService {

	List<Domain> getSets();
	
	boolean insertSet(ProblemSet s);
}
