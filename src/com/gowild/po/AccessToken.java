package com.gowild.po;
/**
 * ΢��ͨ�ýӿ�ƾ֤
 * @author Administrator
 *
 */
public class AccessToken {
	private String token;
	//ƾ֤��Чʱ��
	private Integer expiresIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
