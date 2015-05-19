package com.gowild.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CheckUtil {
	private static final String token = "gowild20131225";
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{token,timestamp,nonce};
		//��token��timestamp��nonce�������������ֵ�������
		Arrays.sort(arr);
		//�����������ַ���ƴ�ӳ�һ���ַ���
		StringBuffer content = new StringBuffer();
		for(String str : arr){
			content.append(str);
		}
		//sha1 ����
		String temp = getSha1(content.toString());
		return temp.equals(signature);
	}
	public static String getSha1(String str){
		if(null == str || str.length() <= 0){
			return null;
		}
		char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9',
				'a','b','c','d','e','f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j*2];
			int k = 0;
			for(int i=0;i<j;i++){
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
