package com.timp.service;

import com.timp.domain.WBTokenState;

public interface WBTokenStateService {
	/**
	 * 添加获取的AccessToken
	 * @param wBTokenState
	 * @return
	 */
	public WBTokenState addWBTokenState(WBTokenState wBTokenState);
	/**
	 * 读取用户授权的唯一票据信息
	 * @param code
	 * @return
	 */
	public String getAccessToken(String code);
	/**
	 * 获取code
	 * @return
	 */
	public String getCode();
}
