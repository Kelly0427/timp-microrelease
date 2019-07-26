package com.timp.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timp.domain.WechatTokenRepository;
@Service
public class WechatTokenServiceImpl implements WechatTokenService{
	@Autowired
	WechatTokenRepository wechatTokenRepository;
	//获取授权票据接口
	@Override
	public Map<String, Object> getToken() {
		return wechatTokenRepository.getToken();
	}

}
