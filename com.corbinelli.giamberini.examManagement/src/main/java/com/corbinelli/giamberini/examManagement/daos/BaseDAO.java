package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import jakarta.persistence.EntityManager;

public abstract class BaseDAO<T> {
	
	protected EntityManager entityManager;
	
	protected BaseDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public abstract T findById(Long id);
	
	public abstract List<T> findAll();
	
	public abstract void save(T t);
	
	public abstract void delete(T t);

}
