package zpl.oj.dao.security;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import zpl.oj.model.security.Privilege;


public interface PrivilegeDataDao {

	@Select("select api as uri,grant_level as level from privilege")
	List<Privilege> getPrivilegeData();
}
