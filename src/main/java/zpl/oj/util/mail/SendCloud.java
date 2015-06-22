package zpl.oj.util.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import zpl.oj.model.common.Quiz;
import zpl.oj.model.request.InviteUser;
import zpl.oj.model.request.User;
import zpl.oj.model.requestjson.RequestTestInviteUser;
import zpl.oj.util.PropertiesUtil.PropertiesUtil;
@Service
public class SendCloud {
	
	public void sendmail(String email,String subject,String content) throws ClientProtocolException, IOException {

		String baseurl = (String) PropertiesUtil.getContextProperty("baseurl");
		String api_user = (String) PropertiesUtil.getContextProperty("api_user");
		String api_key = (String) PropertiesUtil.getContextProperty("api_key");
		String mailfrom = (String) PropertiesUtil.getContextProperty("mailfrom");
		String sendCloudUrl =  (String) PropertiesUtil.getContextProperty("sendcloudurl");
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(sendCloudUrl);
		
		
		List nvps = new ArrayList();
    	nvps.add(new BasicNameValuePair("api_user", api_user));
        nvps.add(new BasicNameValuePair("api_key", api_key));
        nvps.add(new BasicNameValuePair("from", mailfrom));
        nvps.add(new BasicNameValuePair("to", email));
        nvps.add(new BasicNameValuePair("subject", subject));
        nvps.add(new BasicNameValuePair("html", content));
        httpost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
        // 请求
        HttpResponse response = httpclient.execute(httpost);
        // 处理响应
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
          // 读取xml文档
          String result = EntityUtils.toString(response.getEntity());
          System.out.println(result);
        } else {
          System.err.println("error");
        }
	}

}
