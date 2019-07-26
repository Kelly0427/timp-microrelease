package com.timp.domain;

public interface WBTokenStateRepositoryCustom {
	/**
	 * 读取用户授权的唯一票据信息
	 * @param code
	 * @return
	 */
	public String getAccessToken(String code);
	/**
	 * 读取accessToken
	 * @return
	 */
	public String getInfo();
}
