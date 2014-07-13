package zpl.oj.service;

import java.util.List;

import zpl.oj.model.common.Set;

public interface SetService {

	List<Set> getSets();
	
	boolean insertSet(Set s);
}
