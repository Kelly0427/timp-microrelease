package com.timp.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.timp.domain.ArticleMessage;
import com.timp.domain.Headlines;
import com.timp.domain.HeadlinesRepository;
import com.timp.domain.WBTokenStateRepository;

@Service
public class HeadlinesServiceImpl implements HeadlinesService{
	@Autowired
	HeadlinesRepository headlinesRepository;
	@Autowired
	WBTokenStateRepository wbTokenStateRepository;
	//头条文章发布地址
	private static final String article_publish_url="https://api.weibo.com/proxy/article/publish.json?access_token=ACCESS_TOKEN";
	//发布头条文章
	@Override
	public Headlines sendHeadlines(Long id,String sendOperator) {
		return headlinesRepository.sendHeadlines(id,sendOperator);
	}
	@Override
	public Headlines rejectHeadlines(Long id,String rejectOperator) {
		Headlines headlines=headlinesRepository.findOne(id);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		headlines.setId(id);
		headlines.setTitle(headlines.getTitle());
		headlines.setContent(headlines.getContent());
		headlines.setCover(headlines.getCover());
		headlines.setSummary(headlines.getSummary());
		headlines.setText(headlines.getText());
		headlines.setSendTime(df.format(new Date()));
		headlines.setAddOperator(headlines.getAddOperator());
		headlines.setSendOperator(rejectOperator);
		headlines.setStatus("待审核");
		return headlinesRepository.save(headlines);
	}
	//查询所有的头条文章列表
	@Override
	public Page<Headlines> searchHeadlines(Pageable pageable, String startTime, String endTime) {
		return headlinesRepository.searchHeadlines(pageable, startTime, endTime);
	}
	//添加到数据库，未审阅
	@Override
	public Headlines addHeadlines(Headlines headlines) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		headlines.setSendTime(df.format(new Date()));
		headlines.setStatus("待审核");
		return headlinesRepository.save(headlines);
	}
	//查询发布的记录
	@Override
	public Page<Object> queryCheckInfo(Pageable pageable, String startTime, String endTime) {
		return headlinesRepository.queryCheckInfo(pageable, startTime, endTime);
	}
	//查询未发布的记录
	@Override
	public Page<Object> queryUnCheckInfo(Pageable pageable, String startTime, String endTime) {
		return headlinesRepository.queryUnCheckInfo(pageable, startTime, endTime);
	}
	//展示所有信息
	@Override
	public List<Headlines> queryHeadlinesList(){
		return headlinesRepository.queryHeadlinesList();
	}
	//删除头条文章
	@Override
	public int deleteHeadlines(Long[] ids) {
        int res=0;
        for (Long id : ids) {
        	headlinesRepository.delete(id);
        	res++;
		}
		return res;
	}
	//保存并发布
	@Override
	public Headlines sendAndSave(Headlines headlines) {
		String accessToken=wbTokenStateRepository.getInfo();
		String url=article_publish_url.replace("ACCESS_TOKEN", accessToken);
		//异步请求
		RestTemplate restTemplate = new RestTemplate();
		//设置请求头
		HttpHeaders headers=new HttpHeaders();
		//设置编码格式
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		//参数
		MultiValueMap<String, String>  params=new LinkedMultiValueMap<> ();
		if(headlines.getSummary()!=null && headlines.getSummary().length()>0) {
			params.add("title", headlines.getTitle());
			params.add("content", headlines.getContent());
			params.add("cover", headlines.getCover());
			params.add("summary", headlines.getSummary());
			params.add("text", headlines.getText());
		}else {
			params.add("title", headlines.getTitle());
			params.add("content", headlines.getContent());
			params.add("cover", headlines.getCover());
			params.add("summary", "");
			params.add("text", headlines.getText());
		}
		//组装
		HttpEntity<MultiValueMap<String, String> > entity = new HttpEntity<MultiValueMap<String, String> >(params,headers);
		//请求开始
		ResponseEntity<String> forEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);					
		String strBody=forEntity.getBody().toString();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		headlines.setSendTime(df.format(new Date()));	
		headlines.setStatus("已审核");
		headlinesRepository.save(headlines);
		return headlines;
	}	

}
