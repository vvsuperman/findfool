package zpl.oj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import zpl.oj.model.common.Domain;
import zpl.oj.model.common.ProblemSet;


public interface DomainDao {

	@Select("select domain_id as domainId,domain_name as domainName,type from domain where state=1")
	List<Domain> getAllDomain();
	

}