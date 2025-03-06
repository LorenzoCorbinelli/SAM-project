package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public abstract class BaseDAO<T> {
	
	@Inject
	protected EntityManager entityManager;
	
	public BaseDAO() {}

	public abstract T findById(Long id);
	
	public abstract List<T> findAll();
	
	public abstract void save(T t);
	
	public abstract T delete(Long id);

}
