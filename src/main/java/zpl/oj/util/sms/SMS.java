﻿package zpl.oj.util.sms;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import zpl.oj.util.Constant.ExamConstant;

@Service
public class SMS {
	private static int connectTimeOut = 5000;
	private static int readTimeOut = 10000;
	private static String requestEncoding = "UTF-8";
	public static int getConnectTimeOut() {
		return connectTimeOut;
	}
	public static void setConnectTimeOut(int connectTimeOut) {
		SMS.connectTimeOut = connectTimeOut;
	}
	public static int getReadTimeOut() {
		return readTimeOut;
	}
	public static void setReadTimeOut(int readTimeOut) {
		SMS.readTimeOut = readTimeOut;
	}
	public static String getRequestEncoding() {
		return requestEncoding;
	}
	public static void setRequestEncoding(String requestEncoding) {
		SMS.requestEncoding = requestEncoding;
	}
	
	public static String doGet(String requrl,Map<?,?> parameters,String recvEndcoding){
		HttpURLConnection url_con=null;
		String responseContent = null;
		String vchartset=recvEndcoding==""?SMS.requestEncoding:recvEndcoding;
		try {
				StringBuffer params=new StringBuffer();
				for (Iterator<?> iter=parameters.entrySet().iterator();iter.hasNext();) {
					Entry<?, ?> element=(Entry<?, ?>) iter.next();
					params.append(element.getKey().toString());
					params.append("=");
					params.append(URLEncoder.encode(element.getValue().toString(),vchartset));
					params.append("&");
				}
				if(params.length()>0){
					params=params.deleteCharAt(params.length()-1);
				}
				URL url=new URL(requrl);
				url_con=(HttpURLConnection) url.openConnection();
				url_con.setRequestMethod("GET");
				System.setProperty("连接超时：", String.valueOf(SMS.connectTimeOut));
				System.setProperty("访问超时：", String.valueOf(SMS.readTimeOut)); 
				url_con.setDoOutput(true);//
				byte[] b=params.toString().getBytes();
				url_con.getOutputStream().write(b, 0,b.length);
				url_con.getOutputStream().flush();
				url_con.getOutputStream().close();
				InputStream in=url_con.getInputStream();
				byte[] echo=new byte[10*1024];
				int len=in.read(echo);
				responseContent=(new String(echo,0,len).trim());
				int code = url_con.getResponseCode();
				if (code != 200) {
					responseContent = "ERROR" + code;
				}
		} catch (Exception e) {
			System.out.println("网络故障:"+ e.toString());
		}finally{
			if(url_con!=null){
				url_con.disconnect();
			}
		}
		return responseContent;
		
	}
	public static String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		String vchartset=recvEncoding==""?SMS.requestEncoding:recvEncoding;
		try {
				StringBuffer params = new StringBuffer();
				String queryUrl = reqUrl;
				int paramIndex = reqUrl.indexOf("?");
				
				if (paramIndex > 0) {
					queryUrl = reqUrl.substring(0, paramIndex);
					String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
					String[] paramArray = parameters.split("&");
					for (int i = 0; i < paramArray.length; i++) {
						String string = paramArray[i];
						int index = string.indexOf("=");
						if (index > 0) {
							String parameter = string.substring(0, index);
							String value = string.substring(index + 1, string.length());
							params.append(parameter);
							params.append("=");
							params.append(URLEncoder.encode(value, vchartset));
							params.append("&");
						}
					}

					params = params.deleteCharAt(params.length() - 1);
				}
				URL url = new URL(queryUrl);
				url_con = (HttpURLConnection) url.openConnection();
				url_con.setRequestMethod("GET");
				System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(SMS.connectTimeOut));
				System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(SMS.readTimeOut));
				url_con.setDoOutput(true);
				byte[] b = params.toString().getBytes();
				url_con.getOutputStream().write(b, 0, b.length);
				url_con.getOutputStream().flush();
				url_con.getOutputStream().close();
				InputStream in = url_con.getInputStream();
				byte[] echo = new byte[10 * 1024];
				int len = in.read(echo);
				responseContent = (new String(echo, 0, len)).trim();
				int code = url_con.getResponseCode();
				if (code != 200) {
					responseContent = "ERROR" + code;
				}
		} catch (Exception e) {
			System.out.println("网络故障:"+ e.toString());
		}finally{
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
		
	}
	public static String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		String vchartset=recvEncoding==""?SMS.requestEncoding:recvEncoding;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), vchartset));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(SMS.connectTimeOut);
			url_con.setReadTimeout(SMS.readTimeOut);
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			byte[] echo = new byte[10 * 1024];
			int len = in.read(echo);
			responseContent = (new String(echo, 0, len)).trim();
			int code = url_con.getResponseCode();
			if (code != 200) {
				responseContent = "ERROR" + code;
			}

		}
		catch (IOException e) {
			System.out.println("网络故障:"+ e.toString());
		}
		finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}

	
	/**
	 * @param mobile content and id of the sms template
	 * @return
	 */
	public String send(String mobile,List contents,String tempid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", ExamConstant.SMS_USERNAME);// 此处填写用户账号
		map.put("scode", ExamConstant.SMS_PWD);// 此处填写用户密码
		map.put("mobile", mobile);// 此处填写发送号码
		map.put("tempid", tempid);// 此处填写模板短信编号
		// map.put("extcode","1234");
		if(tempid == ExamConstant.SMS_TEMPID_REMIND){
			map.put("content", "@1@=" + contents.get(0));// 此处填写模板短信内容
		}else if(tempid == ExamConstant.SMS_TEMPID_INVITE){
			map.put("content", "@1@=" + contents.get(0)+ ",@2@=" + contents.get(1));// 此处填写模板短信内容
		}
		
		
		String temp = SMS.doPost(
			  ExamConstant.SMS_ADDRESS, map, "GBK");
		return temp;
	}

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("username", "NFTB700084");//此处填写用户账号
//		map.put("scode", "388527");//此处填写用户密码
//		map.put("mobile","15005163332");//此处填写发送号码
//		map.put("tempid","MB-2013102300");//此处填写模板短信编号
//		//map.put("extcode","1234");
//		map.put("content","@1@=123456");//此处填写模板短信内容
//		String temp = SMS.doPost("http://mssms.cn:8000/msm/sdk/http/sendsms.jsp",map, "GBK");
//		System.out.println("值:" + temp);//此处为短信发送的返回值
//	}
}

