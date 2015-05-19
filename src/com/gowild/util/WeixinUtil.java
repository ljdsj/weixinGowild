package com.gowild.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.gowild.po.AccessToken;

import net.sf.json.JSONObject;

public class WeixinUtil {
	private static final Logger log = Logger.getLogger(WeixinUtil.class);
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"+
										"grant_type=client_credential&appid=APPID&secret=APPSECRET";

	/**
	 * 发起https请求并获取结果
	 * @param reqUrl 请求地址
	 * @param reqMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据 
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	 */
	
	public static JSONObject httpsRequest(String reqUrl,String reqMethod,String outputStr){
		JSONObject jsonObj = null;
		StringBuffer sb = new StringBuffer();
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = {new MyX509TrustManager()};
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
	        URL url = new URL(reqUrl);
	     // 创建HttpsURLConnection对象，并设置其SSLSocketFactory对象
	        HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
	        httpsConn.setSSLSocketFactory(ssf);
	        httpsConn.setDoInput(true);
	        httpsConn.setDoOutput(true);
	        httpsConn.setUseCaches(false);
	        //设置请求方式
	        httpsConn.setRequestMethod(reqMethod);
	        if("GET".equalsIgnoreCase(reqMethod)){
	        	httpsConn.connect();
	        }
	        //有数据提交时
	        if(outputStr != null){
	        	OutputStream os = httpsConn.getOutputStream();
	        	os.write(outputStr.getBytes("UTF-8"));
	        	os.close();
	        }
	        // 将返回的输入流转换成字符串
	        InputStream is = httpsConn.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	        BufferedReader bf = new BufferedReader(isr);
	        String str = null;
	        while((str = bf.readLine()) != null){
	        	sb.append(str);
	        }
	        bf.close();
	        isr.close();
	        is.close();
	        is = null;
	        httpsConn.disconnect();
	        jsonObj = JSONObject.fromObject(sb.toString());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObj;
	}
	/**
	 * 获取access_token的接口地址(GET)限200（次/天）
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid,String appsecret){
		AccessToken accessToken = null;
		String reqUrl = ACCESS_TOKEN_URL.replace("APPID", appid)
									.replace("APPSECRET", appsecret);
		JSONObject jsonObj = httpsRequest(reqUrl,"GET",null);
		log.info(" jsonObj result : "+jsonObj);
		//jsonObj = {"access_token":"ACCESS_TOKEN","expires_in":7200}
		if(null != jsonObj){
			accessToken = new AccessToken();
			accessToken.setToken(jsonObj.getString("access_token"));
			accessToken.setExpiresIn(jsonObj.getInt("expires_in"));
			log.info(" access token result : "+accessToken);
		}
		return accessToken;
	}
	
	
}
