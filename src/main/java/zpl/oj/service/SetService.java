package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.ProblemSet;

public interface SetService {

	List<ProblemSet> getSets();
	
	boolean insertSet(ProblemSet s);
}
