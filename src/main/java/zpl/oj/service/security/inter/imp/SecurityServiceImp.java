package zpl.oj.service.security.inter.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zpl.oj.dao.TestuserDao;
import zpl.oj.dao.security.PrivilegeDataDao;
import zpl.oj.model.common.Testuser;
import zpl.oj.model.request.User;
import zpl.oj.model.security.Privilege;
import zpl.oj.service.imp.TuserService;
import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.service.user.inter.UserService;
import zpl.oj.util.MD5.MD5Util;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
import zpl.oj.util.base64.BASE64;

@Service
public class SecurityServiceImp implements SecurityService {

	private Map<String,List<Integer>> map;
	private String regEx = "@,@,@,@";
	private Integer addmin;
	@Autowired
	private PrivilegeDataDao privilegeDataDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TuserService tuserService;
	

	
	public SecurityServiceImp() {
		map = new HashMap<String, List<Integer>>();
		addmin = null;
	}
	@Override
	public List<Integer> getLevel(String uri) {
		List<Integer> level = null;
		level = map.get(uri);
		if(null == level){
			List<Privilege> tmp = privilegeDataDao.getPrivilegeData();
			map.clear();
			for(Privilege p:tmp){
				List<Integer> tl = null;
				tl = map.get(p.getUri());
				if(tl == null){
					tl = new ArrayList<Integer>();
				}
				tl.add(p.getLevel());
				map.put(p.getUri(),tl);
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
	
	// testuser 登录计算token的方法,在testuid前拼接字符串**;
	public String computeToken(Testuser u) {
		/*
		 * 计算方法如下 用户登陆时，根据登录时间+70min得到的时间+email+pwd进行md5运算， 得出的结果+,uid
		 * 进行base64编码，这个就token
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String loginDate = u.getLastLoginDate();
		Date date = null;
		try {
			date = sdf.parse(loginDate);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// 增加token refresh time :min
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		if (addmin == null) {
			String t = (String) PropertiesUtil
					.getContextProperty("token-refresh");
			addmin = Integer.parseInt(t);
		}
		c.add(Calendar.MINUTE, addmin);
		date = c.getTime();
		String source = u.getTuid() + u.getPwd() + date.toString();
		String token = MD5Util.stringMD5(source);
		token += "@,@,@,@" +"**"+ u.getTuid();
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
	public boolean checkToken(String uri, String token) {
		// 解码出uid
		try {
			String tokenUid = new String(BASE64.decodeBASE64(token));
			Pattern pat = Pattern.compile(regEx);
			String[] strs = pat.split(tokenUid);
			if (strs.length > 2)
				return false;
			String md5 = strs[0];
			
			// 先进行权限检查
			List<Integer> level = getLevel(uri);
			if (level == null) {
				return false;
			}
			/*
			 * 查询出user信息，uid，pwd，时间+70min，进行md5运算，然后比md5值
			 */
			int flag = 0;

			if (strs[1].contains("**")) {
				String[] ustrs = strs[1].split("**");
				int tuid = Integer.parseInt(ustrs[1]);
				Testuser u = tuserService.getTestuserById(tuid);

				for (Integer i : level) {
					if (u.getPrivilege() == i) {
						flag = 1;
					}
				}
				if (flag == 0) {
					return false;
				}

				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				Date loginDate = sdf.parse(u.getLastLoginDate());
				// 增加70min
				Calendar c = Calendar.getInstance();
				c.setTime(loginDate);
				if (addmin == null) {
					String t = (String) PropertiesUtil
							.getContextProperty("token-refresh");
					addmin = Integer.parseInt(t);
				}
				c.add(Calendar.MINUTE, addmin);
				loginDate = c.getTime();
				String serverToken = MD5Util.stringMD5(u.getTuid() + u.getPwd()
						+ loginDate.toString());
				if (serverToken.equals(md5)) {
					// 验证成功
					// 第二部验证是否过期
					Date now = new Date();
					if (now.compareTo(loginDate) > 0) {
						// 过期
						return false;
					}
					return true;
				} else {
					// 验证失败
					return false;
				}

			} else {
				int uid = Integer.parseInt(strs[1]);
				User u = userService.getUserById(uid);
				for (Integer i : level) {
					if (u.getPrivilege() == i) {
						flag = 1;
					}
				}
				if (flag == 0) {
					return false;
				}

				Date loginDate = u.getLastLoginDate();
				// 增加70min
				Calendar c = Calendar.getInstance();
				c.setTime(loginDate);
				if (addmin == null) {
					String t = (String) PropertiesUtil
							.getContextProperty("token-refresh");
					addmin = Integer.parseInt(t);
				}
				c.add(Calendar.MINUTE, addmin);
				loginDate = c.getTime();
				String serverToken = MD5Util.stringMD5(u.getUid() + u.getPwd()
						+ loginDate.toString());
				if (serverToken.equals(md5)) {
					// 验证成功
					// 第二部验证是否过期
					Date now = new Date();
					if (now.compareTo(loginDate) > 0) {
						// 过期
						return false;
					}
					return true;
				} else {
					// 验证失败
					return false;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
