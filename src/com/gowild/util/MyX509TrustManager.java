package com.gowild.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;
/**
 * 1)https������Ҫһ��֤�����ι��������������������Ҫ�Լ����壬����Ҫʵ��X509TrustManager�ӿ�
 * 2)���֤������������þ���������������ָ����֤�飬����Ĵ�����ζ����������֤�飬�����Ƿ�Ȩ�������䷢
 * @author Administrator
 *
 */
public class MyX509TrustManager implements X509TrustManager {

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
