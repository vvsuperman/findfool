package zpl.oj.util.wx;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import zpl.oj.util.Constant.ExamConstant;

class JsSdk {

	private static MemCachedClient cachedClient;

	static {
		cachedClient = new MemCachedClient();
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(ExamConstant.MEMCACHED_SERVERS);
		pool.setWeights(ExamConstant.MEMCACHED_WEIGHTS);
		pool.setInitConn(5);
		pool.setMinConn(5);
		pool.setMaxConn(10);
		pool.setMaxIdle(10);
		pool.initialize();
	}

	public static void main(String[] args) {
//		String jsapi_ticket = "jsapi_ticket";
//
//		// 注意 URL 一定要动态获取，不能 hardcode
//		String url = "http://example.com";
//		Map<String, String> ret = sign(jsapi_ticket, url);
//		for (Map.Entry entry : ret.entrySet()) {
//			System.out.println(entry.getKey() + ", " + entry.getValue());
//		}
		System.out.println(JsSdk.getAccessToken());
	};

	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
		System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public static String getAccessToken() {
		String accessToken = "";
		if (cachedClient.get("access_token") == null) {
			StringBuilder urlSb = new StringBuilder();
			urlSb.append("https://api.weixin.qq.com/cgi-bin/token?");
			urlSb.append("grant_type=client_credential&appid=");
			urlSb.append(ExamConstant.WX_APP_ID).append("&secret=");
			urlSb.append(ExamConstant.WX_APP_SECRET);

			String result = doGet(urlSb.toString());
			Gson gson = new Gson();
			WxResp resp = gson.fromJson(result, WxResp.class);
			cachedClient.set("access_token", resp.getAccess_token(),
					resp.getExpires_in());
		} else {
			accessToken = cachedClient.get("access_token").toString();
		}

		return accessToken;
	}

	private static String doGet(String strUrl) {
		HttpsURLConnection conn = null;
		BufferedReader reader = null;
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(strUrl);
			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setReadTimeout(500000);
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}

			return result.toString();
		}
	}

	class WxResp {
		private String access_token;
		private int expires_in;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public int getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(int expires_in) {
			this.expires_in = expires_in;
		}
	}
}
