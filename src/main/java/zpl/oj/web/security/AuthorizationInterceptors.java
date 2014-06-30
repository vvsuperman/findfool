package zpl.oj.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import zpl.oj.service.security.inter.SecurityService;

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
		// 不过滤的uri  
        String[] notFilter = new String[] { "/user/add/hr", "index.html" };  
  
        // 请求的uri  
        String uri = request.getRequestURI();  
        
        //TODO check uri privilege

        //TODO chek request's privilege
        
        //TODO is permit? if no, return 401,else contiune filter chain
        boolean doFilter = true;  
        for (String s : notFilter) {  
            if (uri.indexOf(s) != -1) {  
                // 如果uri中包含不过滤的uri，则不进行过滤  
                doFilter = false;  
                break;  
            }  
        }  
        if(doFilter){
        	//进行过滤
        	String token = request.getHeader("Authorization");
            if(null == token || "null".equals(token)){
            	//no authorization
            	response.sendError(401);
            }else{
            	//check authorization
            	boolean isok = securityService.checkToken(uri,token);
            	if(isok == true){
            		 // 允许  
            		return true;
            	}else{
            		//wrong authorization token
                	response.sendError(401);
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
