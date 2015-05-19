package com.gowild.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ReadFileUtil {
	/**
	 * 读取txt文本文件的内容
	 * 两列
	 * 分隔符："->"
	 * 存放到Map<String,String>中
	 * @param path 文件的路径
	 * @return
	 */
	public static Map<String,String> readTxt(String path){
		Map<String,String> keywords = new HashMap<String,String>();
		File txtfile = new File(path);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtfile), "utf-8"));
			String line = null;
			while(null != (line = br.readLine())){
				if(line.contains("->")){
					String[] lines = line.split("->");
					keywords.put(lines[0].trim(), lines[1].trim());
				}
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException error");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException error");
			e.printStackTrace();
		}
		return keywords;
	}
}
