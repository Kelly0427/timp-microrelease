package com.timp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.timp.domain.ArticleRecordRepository;
import com.timp.tool.WechatTokenUtil;
@Service
public class ArticleRecordServiceImpl implements ArticleRecordService{
	@Autowired 
	ArticleRecordRepository articleRecordRepository;
	@Autowired
	WechatTokenUtil tokenUtil;
	private static final String mass_delete_url="https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
	@Override
	public int deleteArticleRecord(String title) {
		int res=0;
		String msgid=articleRecordRepository.findByTitle(title).getMsgid();
		Long article_idx=articleRecordRepository.findByTitle(title).getId();
		String access_token=tokenUtil.checkAccessToken();
		String url = mass_delete_url.replace("ACCESS_TOKEN", access_token);
		JSONObject params = new JSONObject();
		params.put("msg_id", msgid);
		params.put("article_idx", article_idx);
		String str= params.toJSONString();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//异步请求
		RestTemplate restTemplate = new RestTemplate();
		//组装
		HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(),headers);
		//请求结果
		String strbody = restTemplate.exchange(url, HttpMethod.POST, entity,String.class).getBody();
		res++;
		return res;
	}

}
