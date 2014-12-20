package zpl.oj.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zpl.oj.model.security.Privilege;


public interface PrivilegeDataDao {

	@Select("SELECT a.api as uri,b.roleid as level FROM api as a,role_api as b WHERE b.apiid = a.apiid")
	List<Privilege> getPrivilegeData();
}
