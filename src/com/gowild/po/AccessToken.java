package com.gowild.po;
/**
 * 微信通用接口凭证
 * @author Administrator
 *
 */
public class AccessToken {
	private String token;
	//凭证有效时间
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
