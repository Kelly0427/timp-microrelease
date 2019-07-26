package com.timp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.timp.tool.WechatTokenUtil;

public class OpenIdRepositoryImpl implements OpenIdRepositoryCustom{
	@Autowired
	OpenIdRepository openIdRepository;
	@Autowired
	WechatTokenRepository wechatTokenRepository;
	@Autowired
	WechatTokenUtil wechatTokenUtil;
	private static final String get_user_url="http://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN";
	/**
	 * 获取粉丝列表
	 */
	@Override
	public OpenId searchOpenIdList() {
		//获取access_token
		String access_token=wechatTokenUtil.checkAccessToken();		
		//请求地址
		String url=get_user_url.replace("ACCESS_TOKEN", access_token);
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//异步请求
		RestTemplate restTemplate = new RestTemplate();
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(headers);
        //请求结果
		String strbody = restTemplate.exchange(url, HttpMethod.GET, entity,String.class).getBody();        
        //将请求结果转换为json对象
		JSONObject params = JSONObject.parseObject(strbody);
        //获取data值
		String data=params.getString("data"); 
        JSONObject dataParams = JSONObject.parseObject(data);
        OpenId openId=new OpenId();               
        for (Object value:dataParams.values()){
        	openId.setOpenId(value.toString());
        	openIdRepository.save(openId);
        }
        return openId;
	}

}
