package com.jspxcms.plug.repository.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.jspxcms.common.orm.JpqlBuilder;
import com.jspxcms.plug.domain.ScoreStatus;
import com.jspxcms.plug.domain.UserCode;
import com.jspxcms.plug.repository.UserCodeDao;
import com.jspxcms.plug.repository.UserCodeDaoPlus;
import com.sun.star.uno.RuntimeException;

@Component
public class UserCodeDaoImpl implements UserCodeDaoPlus{

	 
	public UserCode find(String id, String name) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from UserCode ub where ub.name = :name " +
				"and ub.idCard = :id");
		jpql.setParameter("name", name);
		jpql.setParameter("id", id);
		List list = jpql.list(em);
		if(list == null || list.isEmpty()) {
			return null;
		} else if(list.size() == 1) {
			return (UserCode) list.get(0);
		} else {
			throw new RuntimeException("more than one record found, name=" + name + ", id=" + id);
		}
	}
	
	public List<UserCode> findAll(){
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from UserCode ub where ub.scoreStatus in (:scoreStatus)");
		jpql.setParameter("scoreStatus", ScoreStatus.SCORE_USEABLE);
		return jpql.list(em);
	}
	
	 
	public UserCode find(Integer id) {
		JpqlBuilder jpql = new JpqlBuilder();
		jpql.append("from UserCode ub where ub.id in (:id)");
		jpql.setParameter("id", id);
		return (UserCode)jpql.list(em).get(0);
	}
	
	private EntityManager em;

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}


}
