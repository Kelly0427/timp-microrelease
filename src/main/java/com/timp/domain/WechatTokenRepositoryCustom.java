package com.timp.domain;

import java.util.Map;

public interface WechatTokenRepositoryCustom {
	/**
	 * 获取授权票据接口
	 * @return
	 */
	public Map<String, Object> getToken();
}
