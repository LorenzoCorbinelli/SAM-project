package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

public interface BaseDAO<T> {
	
	T findById(Long id);
	
	List<T> findAll();
	
	void save(T t);
	
	void delete(T t);

}
