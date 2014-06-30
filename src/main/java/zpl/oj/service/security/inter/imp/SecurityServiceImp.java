package zpl.oj.service.security.inter.imp;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.security.PrivilegeDataDao;
import zpl.oj.model.common.User;
import zpl.oj.model.security.Privilege;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;

@Service
public class SecurityServiceImp implements SecurityService {

	private Map<String,Integer> map;
	private String regEx = "@,@,@,@";
	private Integer addmin;
	@Autowired
	private PrivilegeDataDao privilegeDataDao;
	
	@Autowired
	private UserService userService;
	
	public SecurityServiceImp() {
		map = new HashMap<String, Integer>();
		addmin = null;
	}
	@Override
	public Integer getLevel(String uri) {
		Integer level = null;
		level = map.get(uri);
		if(null == level){
			List<Privilege> tmp = privilegeDataDao.getPrivilegeData();
			map.clear();
			for(Privilege p:tmp){
				map.put(p.getUri(), p.getLevel());
			}
			level = map.get(uri);
		}
		return level;
	}

	@Override
	public String computeToken(User u) {
		/*
		 * 计算方法如下
		 * 用户登陆时，根据登录时间+70min得到的时间+email+pwd进行md5运算，
		 * 得出的结果+,uid 进行base64编码，这个就token
		 */
		Date loginDate = u.getLastLoginDate();
		//增加token refresh time :min
		Calendar c = Calendar.getInstance();
		c.setTime(loginDate);
		if(addmin == null){
			String t = (String) PropertiesUtil.getContextProperty("token-refresh");
			addmin = Integer.parseInt(t);
		}
		c.add(Calendar.MINUTE, addmin);
		loginDate = c.getTime();
		String source = u.getUid()+u.getPwd()+loginDate.toString();
		String token = MD5Util.stringMD5(source);
		token +="@,@,@,@"+u.getUid();
		try {
			token = BASE64.encodeBASE64(token.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return token;
	}
	@Override
	public boolean checkToken(String uri,String token) {
		//解码出uid
		try {
			String tokenUid = new String(BASE64.decodeBASE64(token));
			Pattern pat = Pattern.compile(regEx);
			String[] strs = pat.split(tokenUid);
			if(strs.length >2)
				return false;
			String md5 = strs[0];
			int uid = Integer.parseInt(strs[1]);
			//先进行权限检查
			Integer level = getLevel(uri);
			if(level == null)
			{
				return false;
			}
			/*
			 * 查询出user信息，uid，pwd，时间+70min，进行md5运算，然后比md5值
			 * 
			 */
			
			User u = userService.getUserById(uid);
			if(u.getPrivilege() <level){
				//no auth
				return false;
			}
			Date loginDate = u.getLastLoginDate();
			//增加70min
			Calendar c = Calendar.getInstance();
			c.setTime(loginDate);
			if(addmin == null){
				String t = (String) PropertiesUtil.getContextProperty("token-refresh");
				addmin = Integer.parseInt(t);
			}
			c.add(Calendar.MINUTE, addmin);
			loginDate = c.getTime();
			String serverToken = MD5Util.stringMD5(u.getUid()+u.getPwd()+loginDate.toString());
			if(serverToken.equals(md5)){
				//验证成功
				return true;
			}else{
				//验证失败
				return false;
			}

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
