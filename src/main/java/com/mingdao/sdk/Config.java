package com.mingdao.sdk;

public class Config {
	
	public static final String AUTHURL="https://api.mingdao.com/oauth2/authorize?";
	public static final String ACCEURL="https://api.mingdao.com/oauth2/access_token?";
	public static final String USERURL ="https://api.mingdao.com/passport/detail?";
	
	public static final  String APPKEY="EB56F580B240";//需要换成您的应用的appkey
	public static final  String APPSECRET="9CF8EA795C925E4C43285942232E5D";//需要换成您的应用的appSecret
	public static final  String RESPONSE_TYPE="token";//token或者code
//	public static final  String REDIRECT_URI="http://localhost:8080/oj/#/";//需要换成您的应用设置的回调地址
	public static final  String REDIRECT_URI="http://findfool.com/#/";//需要换成您的应用设置的回调地址
	
	public static String getAuthorizeUrl(){
		String url=AUTHURL
			+"app_key="+APPKEY
			+"&redirect_uri="+REDIRECT_URI
			+"&response_type="+RESPONSE_TYPE;
		return url;
	}
	
	public static String getAccessTokenUrl(String code){
		String url=ACCEURL
			+"app_key="+APPKEY
			+"&app_secret="+APPSECRET
			+"&grant_type=authorization_code"
			+"&format=json"
			+"&code="+code
			+"&redirect_uri="+REDIRECT_URI;
		return url;
	}
	
	public static String getUserInfoUrl(String token){
		String url = USERURL
				+"format=json"
				+"&access_token="+token;
				
		return url;
	}
	
	public static String getAccessTokenByCode(String code){
		String url =getAccessTokenUrl(code);
		String result=HttpUtil.httpByGet2StringSSL(url, null, null);
		return result;
	}
	
	public static String getUserInfo(String token){
		String url = getUserInfoUrl(token);
		String result = HttpUtil.httpByGet2StringSSL(url, null, null);
		return result;
	}
}

