package com.gail.daoImpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.GenericDao;

@SuppressWarnings("unchecked")
@Repository
public abstract class GenericDaoImpl<E, K extends Serializable> implements GenericDao<E, K> {
	
	public static final Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	protected Class<? extends E> daoType;

	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		daoType = (Class<? extends E>) pt.getActualTypeArguments()[0];
	}

	protected Session currentSession() {
		if (sessionFactory.getCurrentSession() == null) {
			synchronized (GenericDaoImpl.class) {
				if (sessionFactory.getCurrentSession() == null) {
					return sessionFactory.openSession();
				}
			}
		} else
			return sessionFactory.getCurrentSession();
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void add(E entity) {
		currentSession().save(entity);
		logger.info("Data saved for {}",entity.getClass().getSimpleName());
	}

	@Transactional
	public void saveOrUpdate(E entity) {
		currentSession().saveOrUpdate(entity);
		logger.info("Data updated for {}",entity.getClass().getSimpleName());
	}

	@Transactional
	public void update(E entity) {
		currentSession().update(entity);
	}

	@Transactional
	public void remove(E entity) {
		currentSession().delete(entity);
		logger.info("Data deleted for {}",entity.getClass().getSimpleName());
	}

	@Transactional
	public E find(long key) {
		return (E) currentSession().get(daoType, key);
	}

	@Transactional
	public List<E> getAll() {
		return currentSession().createCriteria(daoType).list();
	}

}