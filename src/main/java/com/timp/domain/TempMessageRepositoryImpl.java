package com.timp.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class TempMessageRepositoryImpl implements TempMessageRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
	@Override
	public Page<TempMessage> searchTempMsg(Pageable pageable, String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object> query = builder.createQuery(Object.class);
		Root<TempMessage> root = query.from(TempMessage.class);
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
		return new PageImpl<TempMessage>(qPage.getResultList(), localPageable, total);
	}

}
