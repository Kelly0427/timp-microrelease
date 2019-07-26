package com.timp.tool;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.timp.domain.WechatToken;
import com.timp.domain.WechatTokenRepository;

@RestController
public class WechatTokenUtil {
	@Autowired
	WechatTokenRepository wechatTokenRepository;
	private final static String appid="wx39052f9213e42225";
	private final static String secret="fa5e2267dfe21d2511bdb21d4899fa25";
	private final static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public WechatToken getAccessToken() {
		//获取access_token的地址
		String url=access_token_url.replace("APPID", appid).replace("APPSECRET", secret);
		//请求
		RestTemplate restTemplate = new RestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		//返回的请求体
		String strbody = restTemplate.exchange(url, HttpMethod.GET, entity,String.class).getBody();
		//转换成JSON格式
		JSONObject params = JSONObject.parseObject(strbody);
		System.out.println(params);
		//获取access_token值
        String data=params.getString("access_token");
        String expiresin=params.getString("expires_in");
        //保存请求的token信息--开始
        WechatToken accToken=new WechatToken();
        accToken.setToken(data);
        accToken.setExpiresin(expiresin);
        accToken.setCreateTime(System.currentTimeMillis());
        //保存请求的token信息--结束
        return wechatTokenRepository.save(accToken);	
	}
	/**
     * 校验access_token是否过期
     * @return
     */
    public String checkAccessToken() {
    	//接收token的对象
    	WechatToken acceToken=new WechatToken();
    	//获取数据库最新记录
    	Map<String, Object> map=wechatTokenRepository.getToken();
    	//获取数据库最新的key
    	String accToken;
    	//获取数据库最新的存储时间
    	Long createTime;
    	//获取当前时间
    	Long curTime=System.currentTimeMillis();
    	//初始化token值
    	String access_token=null;
    	//判断数据库是否有值
    	if(!map.isEmpty()) {
    		accToken=map.get("token").toString();
    		createTime=Long.parseLong(map.get("createtime").toString());     
    		//判断是否过期
    		if(curTime-createTime>7000*1000) {
    			//过期就调用接口
    			acceToken=getAccessToken();
    			//获取最新的token
            	access_token=acceToken.getToken();
    		}
    		access_token=accToken;
    	}else {
    		//不存在直接调用接口
			acceToken=getAccessToken();
			//获取最新的token
	    	access_token=acceToken.getToken();
		}
    	return access_token;
    }
}
