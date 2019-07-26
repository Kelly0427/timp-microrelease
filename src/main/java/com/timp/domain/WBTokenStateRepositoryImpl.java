package com.timp.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WBTokenStateRepositoryImpl implements WBTokenStateRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	@Autowired
    WBTokenStateRepository wBTokenStateRepository;
	
	private static String access_token;
	@Override
	public String getAccessToken(String code) {
		String url="https://api.weibo.com/oauth2/access_token";
		//异步请求
		AsyncRestTemplate restTemplate = new AsyncRestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//参数
		MultiValueMap<String, String>  params=new LinkedMultiValueMap<> ();
		params.add("client_id", "2865329630");
		params.add("client_secret", "9a614db48f88304b06071226158b73fc");
		params.add("grant_type", "authorization_code");
		params.add("code", code);
		params.add("redirect_uri", "https://www.hbsyzn.com");
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//组装
		HttpEntity<MultiValueMap<String, String> > entity = new HttpEntity<MultiValueMap<String, String> >(params,headers);
		//请求开始
		ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		//回调
		forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
			@Override
			public void onSuccess(ResponseEntity<String> result) {		
				String strBody=result.getBody().toString();
				if(strBody != null) {
					JSONObject data=(JSONObject) JSON.parse(strBody);
					access_token=data.getString("access_token");
					String remind_in=data.getString("remind_in");
					String expires_in=data.getString("expires_in");
					String uid=data.getString("uid");
					WBTokenState wbTokenState=new WBTokenState();
					wbTokenState.setAccessToken(access_token);
					wbTokenState.setRemind_in(remind_in);
					wbTokenState.setExpireIn(expires_in);
					wbTokenState.setUid(uid);
					wbTokenState.setCreateTime(System.currentTimeMillis());
					wBTokenStateRepository.save(wbTokenState);
				}
				System.out.println(strBody);
			}
			@Override
			public void onFailure(Throwable ex) {
				System.out.println("请求失败"+url+"--"+ex.getMessage());
			}			
		});	
		return access_token;
	}
	//读取accesstoken
	@Override
	public String getInfo() {
		String sql="select access_token,create_time FROM wbtoken ORDER BY id DESC LIMIT 1";
		Query query=em.createNativeQuery(sql);
		List<Object> objects = query.getResultList();
		Iterator<Object> iterator = objects.iterator();
		Map<String, Object> map = new HashMap<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			map.put("accessToken", row[0]==null?null:row[0]);			
			map.put("createTime", row[1]==null?null:row[1]);
		}
		String token=String.valueOf(map.get("accessToken"));		
		return token;
	}

}
