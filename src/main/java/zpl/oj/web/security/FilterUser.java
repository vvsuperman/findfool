package zpl.oj.web.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import zpl.oj.service.security.inter.SecurityService;

public class FilterUser extends OncePerRequestFilter  {

	@Autowired
	private SecurityService securityService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doFilterInternal(HttpServletRequest request,  
            HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
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
                    filterChain.doFilter(request, response);  
            	}else{
            		//wrong authorization token
                	response.sendError(401);
            	}
            }
        }
        else {  
            // 如果uri中不包含background，则继续  
            filterChain.doFilter(request, response);  
        }  
		
	}

}
