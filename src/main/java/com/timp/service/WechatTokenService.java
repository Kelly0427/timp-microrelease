package com.timp.service;

import java.util.Map;

public interface WechatTokenService {
	/**
	 * 获取授权票据接口
	 * @return
	 */
	public Map<String, Object> getToken();
}
