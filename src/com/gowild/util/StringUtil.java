package com.gowild.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

public class StringUtil {
	/**
	 * 调用图灵机器人api接口，获取智能回复内容
	 * 
	 * @param textFromUser
	 * @return
	 */
	public static String textByTuling(String textFromUser) {
		String result = null;
		String APIKEY = "a1b62b9a512d1718b35d4e14196b860e";
		String INFO = null;
		JSONObject jsonObj = null;
		try {
			INFO = URLEncoder.encode(textFromUser.trim(), "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key="
					+ APIKEY + "&info=" + INFO;
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			connection.connect();
			// 取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			jsonObj = JSONObject.fromObject(sb.toString());
			result = jsonObj.getString("text");
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
		if (null == result) {
			result = "对不起，您的问题暂时无法回答您";
		}
		return result;
	}

	public static String chatWithXiaoBai(String textFromUser) {
		String result = null;
		String Rawtext = null;
		JSONObject jsonObj = null;
		try {
			Rawtext = URLEncoder.encode(textFromUser.trim(), "utf-8");
			String getURL = "http://183.61.189.197:8080/AndroidServer/getDialogCatagory.do?Rawtext="
					+ Rawtext;
			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			connection.connect();
			// 取得输入流，并使用Reader读取
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			jsonObj = JSONObject.fromObject(sb.toString());
			if (jsonObj.containsKey("result")) {
				result = jsonObj.getString("result");
			}
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
		return result;
	}

	/**
	 * 判断一个字符串数组中是否包含某个字符串
	 * 
	 * @param str
	 * @param contents
	 * @return
	 */
	public static boolean isContains(String str, String[] contents) {
		if (null == str || str.length() <= 0) {
			return false;
		}
		for (String s : contents) {
			if (s.equalsIgnoreCase(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件的后缀
	 * 
	 * @param path
	 * @return
	 */
	public static String getPostfix(String path) {
		if (path == null || "".equals(path.trim())) {
			return null;
		}
		if (path.contains(".")) {
			return path.substring(path.lastIndexOf(".") + 1, path.length());
		}
		return null;
	}


}
