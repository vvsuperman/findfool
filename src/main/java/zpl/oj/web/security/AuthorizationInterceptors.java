package zpl.oj.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import zpl.oj.service.security.inter.SecurityService;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;

public class AuthorizationInterceptors implements HandlerInterceptor{
	@Autowired
	private SecurityService securityService;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {
		// 不过滤的url
		String WEBROOT = (String) PropertiesUtil.getContextProperty("WEBROOT");

		
//        String[] notFilter = new String[] { WEBROOT+"/user/confirm",WEBROOT+"/user/add/hr", 
//        		WEBROOT+"/page/","/contactus",WEBROOT+"/resource" };  
		
		String[] notFilter = new String[] { "/user/add/hr", "index.html" ,"/user/oauthorlogin",
	        	"/user/confirm","/user/add/admin","/user/getVerifyQtn","/tuser/getvertifycode",
	        	"/tuser/confirm","/tuser/register","main.html","/company/companyset","/operate/operateurl",
	        	"/resource/","/company/","/cad/","/dp/","/page/","/static/","/testing/checkurl","/user/getvertifycode","/publicReport/list","/testing/" };  


        // 请求的uri  
        String url ="";
        url = request.getRequestURI();
        System.out.println("url..............."+url);
        
        boolean doFilter = true;
        if(url.equals("/")){
        	doFilter=false;
        }else if(WEBROOT.equals("foolrank")){
        	
        }
        else if(url.length()>WEBROOT.length()+1){
        	url = url.substring(WEBROOT.length(),url.length()); 
        }else{
        	url="/";
        	doFilter=false;
        }
        //test 任何请求均不过滤
        doFilter = false;
//        
        //TODO check uri privilege

        //TODO chek request's privilege
        
        //TODO is permit? if no, return 401,else contiune filter chain
        
        for (String s : notFilter) {  
            if (url.indexOf(s) != -1) {  
                // 如果uri中包含不过滤的uri，则不进行过滤  
                doFilter = false;  
                break;  
            }  
        }  
        if(doFilter){
        	//进行过滤
        	String token = request.getHeader("Authorization");
            if(null == token || "null".equals(token)||"".equals(token)){
            	//no authorization
            	response.sendError(401);
            }else{
            	//check authorization
            	boolean isok = securityService.checkToken(url,token);
            	if(isok == true){
            		 // 允许  
            		return true;
            	}else{
            		//wrong authorization token
            		System.out.println(url);
                	response.sendError(401);
//                	response.sendRedirect(WEBROOT+"/page/401.html");
                	return false;     	
            	}
            }
        }
        else {  
            // 如果uri中不包含background，则继续  
        	return true;
        }  
		
		return false;
        
   
	}

}
