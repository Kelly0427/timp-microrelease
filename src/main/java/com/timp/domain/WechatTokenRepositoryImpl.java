package com.timp.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class WechatTokenRepositoryImpl implements WechatTokenRepositoryCustom{
	@PersistenceContext
	private EntityManager em;
	
	//获取授权票据接口
	@Override
	public Map<String, Object> getToken() {
		//从数据库获取最新的token
		String sql="select token,createtime from wechattoken order by id desc LIMIT 1";
		Query query=em.createNativeQuery(sql);
		List<Object> objects = query.getResultList();
		Iterator<Object> iterator = objects.iterator();
		Map<String, Object> map = new HashMap<>();
		while(iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();			
			map.put("token", row[0]==null?null:row[0]);
			map.put("createtime", row[1]==null?null:row[1]);
		}
		return map;
	}

}
