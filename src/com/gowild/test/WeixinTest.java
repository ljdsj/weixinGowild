package com.gowild.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.gowild.util.ReadExcelUtil;
import com.gowild.util.ReadFileUtil;
import com.gowild.util.StringUtil;

public class WeixinTest {
	@Test
	public void tuling(){
		String APIKEY = "a1b62b9a512d1718b35d4e14196b860e"; 
	    String INFO = null;
	    JSONObject jsonObj = null;
		try {
			INFO = URLEncoder.encode("睡不好", "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO; 
		    URL getUrl = new URL(getURL); 
		    HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection(); 
		    connection.connect();
		    // 取得输入流，并使用Reader读取 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); 
		    StringBuffer sb = new StringBuffer();
		    String line = ""; 
		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    } 
		    reader.close();
		     // 断开连接 
		    connection.disconnect();
		    System.out.println(sb);
		    jsonObj = JSONObject.fromObject(sb.toString());
		    System.out.println("jsonObj : "+jsonObj);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void xiaobaiChat(){
		String result = null;
		String Rawtext = null;
	    JSONObject jsonObj = null;
	    try {
	    	Rawtext = URLEncoder.encode("约么", "utf-8");
			String getURL = "http://183.61.189.197:8080/AndroidServer/getDialogCatagory.do?Rawtext="+Rawtext;
			URL getUrl = new URL(getURL);
		    HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		    connection.connect();
		    // 取得输入流，并使用Reader读取 
		    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8")); 
		    StringBuffer sb = new StringBuffer();
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		        sb.append(line);
		    }
		    reader.close();
		    // 断开连接
		    connection.disconnect();
		    System.out.println(sb);
		    jsonObj = JSONObject.fromObject(sb.toString());
		    System.out.println("是否包含result键："+jsonObj.containsKey("result"));
		    result = jsonObj.getString("result");
		    System.out.println("result : "+result);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void isContains(){
		String customer_service[] = {"1","2","3","4","5","6","X"};
		String str = "x";
		System.out.println(StringUtil.isContains(str, customer_service));
	}
	/**
	 * http://localhost:8080/weixinGowild/
	 */
	@Test
	public void readExcel(){
		String path = "WebContent/files/keywords.xls";
		URL url = this.getClass().getResource("/");
		String pathexcle = url.getPath().substring(0, url.getPath().indexOf("/build/"))+
				"/WebContent"+"/files/keywords.xls";
		System.out.println("url = "+url.getPath());
		System.out.println("pathexcle = "+pathexcle);
		Map<String,String> keywords = ReadExcelUtil.readExcel(path);
		System.out.println(keywords.size());
//		/*for(String str:keywords.keySet()){
//			System.out.println(str);
//		}*/
		
		for (Map.Entry<String, String> entry : keywords.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}
	@Test
	public void readtxt(){
		String path = "C:/Users/Administrator/Desktop/weixin/keywords.txt";
		String path2 = "WebContent/files/keywords.txt";
		URL url = this.getClass().getResource("/");
		String path3 = url.getPath().substring(0, url.getPath().indexOf("/build/"))+
				"/WebContent"+"/files/keywords.txt";
		Map<String,String> keywords = ReadFileUtil.readTxt(path3);
		for (Map.Entry<String, String> entry : keywords.entrySet()) {
			System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
		}
	}
	
}
