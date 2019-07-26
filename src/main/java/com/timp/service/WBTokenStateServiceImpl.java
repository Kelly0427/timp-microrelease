package com.timp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.timp.domain.WBTokenState;
import com.timp.domain.WBTokenStateRepository;

@Service
public class WBTokenStateServiceImpl implements WBTokenStateService{
	@Autowired
	WBTokenStateRepository  wBTokenStateRepository;
	private String authorizeURL="https://api.weibo.com/oauth2/authorize?client_ID=2865329630&response_type=code&redirect_URI=https://api.weibo.com/oauth2/default.html";
	/**
	 * 将获取到的AccessToken相关信息保存到数据库
	 */
	@Override
	public WBTokenState addWBTokenState(WBTokenState wBTokenState) {
		return wBTokenStateRepository.save(wBTokenState);
	}
    /**
     * 读取用户授权的唯一票据信息
     */
	@Override
	public String getAccessToken(String code) {
		return wBTokenStateRepository.getAccessToken(code);
	}
	/**
     * 获取code
     */
	@Override
	public String getCode() {		
		//异步请求
		AsyncRestTemplate restTemplate = new AsyncRestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String url=authorizeURL;
        //组装
      	HttpEntity<String> entity = new HttpEntity<String>(headers);
        //请求开始
	    ListenableFuture<ResponseEntity<String>> forEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
	    //回调
	  	forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
	  		@Override
	  		public void onSuccess(ResponseEntity<String> result) {		
	  			System.out.println("请求成功"+url+result.getBody());
	  			String strBody=result.getBody().toString();
	  		}
	  		@Override
	  		public void onFailure(Throwable ex) {
	  			System.out.println("请求失败"+url+ex.getMessage());
	  		}			
	  	});	
	    return null;			
	}

}
