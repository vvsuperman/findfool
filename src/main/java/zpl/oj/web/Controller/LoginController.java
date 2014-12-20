package zpl.oj.web.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

@Controller
public class LoginController {

//	@Autowired
//	private LoginService loginService;
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private CookieService cookieService;
	
	//璐熻矗index鐨勮姹�
	@RequestMapping(value="/")
	public String toConfirm(){
		return "app/confirm";
	}
	//璐熻矗index鐨勮姹�
	@RequestMapping(value="/toCode")
	public String toCode(HttpServletRequest request){
		String code= WebUtils.findParameterValue(request, "passcode");
		
		if(code == null || "null".equals(code)){
			return "app/confirm";
		}
		//ok
		return "app/index";
	}
	//璐熻矗index鐨勮姹�
	@RequestMapping(value="/index.html")
	public String loginPage(){
		return "login";
	}
	
	//璐熻矗閫�嚭鐧婚檰璇锋眰
	@RequestMapping(value="/logout.html")
	public ModelAndView setLogout(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return new ModelAndView("login");
	}
	
//	@RequestMapping(value="/main.html")
//	public ModelAndView loginedCheck(HttpServletRequest request){
//		boolean u=false ;//= (User)request.getSession().getAttribute("user");
//		Cookie[] cookies = request.getCookies();
//		for(Cookie c:cookies){
//			if(c.getName().equals("loginToken")){
//				u = cookieService.checkCookie(c);
//			}
//		}
//		if(u == false){
//			return new ModelAndView("login","error","璇峰厛鐧诲綍");
//		}else
//			return new ModelAndView("main");
//	}
	
	@RequestMapping(value="/relogin.html")
	public ModelAndView relogin(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("p_login");
		return mv;
		
	}
	
	@RequestMapping(value="/user.html")
	public ModelAndView returnUser(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView();
		//TODO
		return mv;
	}
	//璐熻矗loginCheck鐨勮姹�
	@RequestMapping(value="/loginCheck.html")
	public ModelAndView loginCheck(HttpServletRequest request, HttpServletResponse response){
		String name= WebUtils.findParameterValue(request, "userName");
		String pwd = WebUtils.findParameterValue(request, "password");
		
		//User u = new User();
//		u.setName(name);
//		u.setPwd(pwd);
//		boolean isVaildUser = 
//				loginService.checkLogin(u);
//		if(!isVaildUser){
//			return new ModelAndView("login","error","鐢ㄦ埛鍚嶆垨鑰呭瘑鐮侀敊璇�");
//		}else{
//			u = userService.getUserByName(name);
//			ModelAndView main = new ModelAndView("main");
//			Cookie cookie_channel = new Cookie("loginToken",u.toString());
//			cookieService.addCookie(u, cookie_channel);
//			response.addCookie(cookie_channel);
//			//request.getSession().setAttribute("user", u);
//			return main;
//		}
		return null;
	}
}
