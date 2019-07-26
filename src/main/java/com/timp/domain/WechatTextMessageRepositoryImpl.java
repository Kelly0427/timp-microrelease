package com.timp.domain;

import java.util.ArrayList;
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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.timp.tool.HelperUtils;

public class WechatTextMessageRepositoryImpl implements WechatTextMessageRepositoryCustom{
	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Object> queryCheckText(Pageable pageable,  String startTime, String endTime) {
		String sql="SELECT * FROM wechattextmessage WHERE `status`='已审核'";
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
			map.put("sendTime", row[2]==null?null:row[2]);
			map.put("status", row[3]==null?null:row[3]);
			map.put("addOperator", row[4]==null?null:row[4]);
			map.put("sendOperator", row[5]==null?null:row[5]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable, resultList);			
	}

	@Override
	public Page<Object> queryUncheckText(Pageable pageable, String startTime, String endTime) {
		String sql="SELECT * FROM wechattextmessage WHERE `status`='待审核'";
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
			map.put("sendTime", row[2]==null?null:row[2]);
			map.put("status", row[3]==null?null:row[3]);
			map.put("addOperator", row[4]==null?null:row[4]);
			map.put("sendOperator", row[5]==null?null:row[5]);
			resultList.add(map);
		}
		return HelperUtils.getPageList(pageable,resultList);
	}
    //展示所有内容
	@Override
	public List<WechatTextMessage> queryTextList() {
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Root<WechatTextMessage> root = query.from(WechatTextMessage.class);
		query.where(predicatesList.toArray(new Predicate[predicatesList.size()]));
		long total = (long) em.createQuery(query.select(builder.count(root))).getSingleResult();
		query.orderBy(builder.desc(root.get("id")));
		Query qPage = em.createQuery(query.select(root));
		return qPage.getResultList();
	}

}
