package com.timp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timp.domain.OpenId;
import com.timp.domain.OpenIdRepository;

@Service
public class OpenIdServiceImpl implements OpenIdService{
	@Autowired
	OpenIdRepository openIdRepository;
	//将获取的粉丝放到粉丝表
	@Override
	public OpenId addOpenId() {
		//获取微信公众平台中的粉丝列表
		OpenId openId=openIdRepository.searchOpenIdList();
		//将获取到的粉丝存储到本地数据库
		return openIdRepository.save(openId);
	}
	
}
