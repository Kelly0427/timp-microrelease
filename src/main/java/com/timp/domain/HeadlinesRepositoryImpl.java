package com.timp.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.timp.tool.HelperUtils;

public class HeadlinesRepositoryImpl implements HeadlinesRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	@Autowired
	WBTokenStateRepository wbTokenStateRepository;
	@Autowired
    HeadlinesRepository headlinesRepository;	
	//头条文章发布地址
	private static final String article_publish_url="https://api.weibo.com/proxy/article/publish.json?access_token=ACCESS_TOKEN";
	//发布头条文章
	@Override
	public Headlines sendHeadlines(Long id,String sendOperator) {		
		Headlines headlines=headlinesRepository.findOne(id);
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
		headlines.setId(id);
		headlines.setTitle(headlines.getTitle());
		headlines.setContent(headlines.getContent());
		headlines.setCover(headlines.getCover());
		headlines.setSummary(headlines.getSummary());
		headlines.setText(headlines.getText());
		headlines.setSendTime(df.format(new Date()));
		headlines.setAddOperator(headlines.getAddOperator());
		headlines.setSendOperator(sendOperator);
		headlines.setStatus("已审核");
		headlinesRepository.save(headlines);
		return headlines;
	}
	//查询所有的头条文章列表
	@Override
	public Page<Headlines> searchHeadlines(Pageable pageable, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Root<Headlines> root = query.from(Headlines.class);
		try {
			if(startTime!=null && startTime.length()>0) {
					predicatesList.add(builder.greaterThanOrEqualTo(root.get("sendTime"), df.parse(startTime).getTime()));
			}
			if(endTime!=null && endTime.length()>0) {
				predicatesList.add(builder.lessThanOrEqualTo(root.get("sendTime"),  df.parse(endTime).getTime()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		long total = (long) em.createQuery(query.select(builder.count(root))).getSingleResult();
		query.orderBy(builder.desc(root.get("id")));
		Query qPage = em.createQuery(query.select(root));
		Pageable localPageable = pageable;
		qPage.setMaxResults(localPageable.getPageSize());
		qPage.setFirstResult(localPageable.getPageNumber() * localPageable.getPageSize());
		return new PageImpl<Headlines>(qPage.getResultList(), localPageable, total);
	}
	//查询发布的记录
	@Override
	public Page<Object> queryCheckInfo(Pageable pageable, String startTime, String endTime) {
		String sql="SELECT * FROM headlines WHERE `status`='已审核'";
		if(startTime !=null && startTime.length()>0) {
			sql+=" AND send_time >="+"'"+startTime+"'";
		}
		if(startTime != null && startTime.length()>0) {
			sql+=" AND send_time <="+"'"+endTime+"'";
		}
		sql+=" ORDER BY id DESC";
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Query qPage = em.createNativeQuery(sql);
		List<Object> objects=qPage.getResultList();
		Iterator<Object> iterator = objects.iterator();
		List<Object> resultList = new ArrayList<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id",  row[0]==null?null:row[0]);
			map.put("content", row[1]==null?null:row[1]);
			map.put("cover", row[2]==null?null:row[2]);
			map.put("sendTime", row[3]==null?null:row[3]);
			map.put("summary", row[4]==null?null:row[4]);
			map.put("text", row[5]==null?null:row[5]);
			map.put("title", row[6]==null?null:row[6]);
			map.put("sendOperator", row[7]==null?null:row[7]);
			map.put("status", row[8]==null?null:row[8]);
			map.put("addOperator", row[9]==null?null:row[9]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable, resultList);
	}
	//查询未发布的记录
	@Override
	public Page<Object> queryUnCheckInfo(Pageable pageable, String startTime, String endTime) {
		String sql="SELECT * FROM headlines WHERE `status`='待审核'";
		if(startTime !=null && startTime.length()>0) {
			sql+=" AND send_time >="+"'"+startTime+"'";
		}
		if(startTime != null && startTime.length()>0) {
			sql+=" AND send_time <="+"'"+endTime+"'";
		}
		sql+=" ORDER BY id DESC";
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Query qPage = em.createNativeQuery(sql);
		List<Object> objects=qPage.getResultList();
		Iterator<Object> iterator = objects.iterator();
		List<Object> resultList = new ArrayList<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			Map<String, Object> map = new HashMap<>();
			map.put("id",  row[0]==null?null:row[0]);
			map.put("content", row[1]==null?null:row[1]);
			map.put("cover", row[2]==null?null:row[2]);
			map.put("sendTime", row[3]==null?null:row[3]);
			map.put("summary", row[4]==null?null:row[4]);
			map.put("text", row[5]==null?null:row[5]);
			map.put("title", row[6]==null?null:row[6]);
			map.put("sendOperator", row[7]==null?null:row[7]);
			map.put("status", row[8]==null?null:row[8]);
			map.put("addOperator", row[9]==null?null:row[9]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable, resultList);
	}
	@Override
	public List<Headlines> queryHeadlinesList() {
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Root<Headlines> root = query.from(Headlines.class);
		query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		long total = (long) em.createQuery(query.select(builder.count(root))).getSingleResult();
		query.orderBy(builder.desc(root.get("id")));
		Query qPage = em.createQuery(query.select(root));
		return qPage.getResultList();
	}

}
