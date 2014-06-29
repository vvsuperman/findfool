package zpl.oj.service.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zpl.oj.model.common.User;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config-*.xml"})
public class SecurityServiceImpTest {

	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		User u = new User();
//		u.setFname("zpl");
//		u.setCompany("hello kitty");
//		u.setEmail("524510356@qq.com");
//		u.setPwd(MD5Util.stringMD5("zpl"));
//		u.setPrivilege(2);
//		userService.addUser(u);
		u = userService.getUserByEmail("524510356@qq.com");
		String token = securityService.computeToken(u);
		boolean res = securityService.checkToken(token);
		assertTrue(res == true);
	}

}
