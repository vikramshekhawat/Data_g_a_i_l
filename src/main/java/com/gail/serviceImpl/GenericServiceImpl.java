package com.gail.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gail.dao.GenericDao;
import com.gail.service.GenericService;

@Service
public abstract class GenericServiceImpl<E, K> implements GenericService<E, K> {

	@Autowired
	private GenericDao<E, K> genericDao;

	public GenericServiceImpl(GenericDao<E, K> genericDao) {
		this.genericDao = genericDao;
	}

	public GenericServiceImpl() {
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(E entity) {
		genericDao.saveOrUpdate(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<E> getAll() {
		return genericDao.getAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public E get(long id) {
		return genericDao.find(id);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void add(E entity) {
		genericDao.add(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void update(E entity) {
		genericDao.update(entity);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(long id) {
		E entity = genericDao.find(id);
		genericDao.remove(entity);
	}

}
