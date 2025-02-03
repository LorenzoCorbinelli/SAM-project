package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.persistence.EntityManager;

public class TeacherDAO extends BaseDAO<Teacher>{

	@Override
	public Teacher findById(Long id) {
		return entityManager.find(Teacher.class, id);
	}

	@Override
	public List<Teacher> findAll() {
		return entityManager.createQuery("SELECT t FROM Teacher t", Teacher.class).getResultList();
	}

	@Override
	public void save(Teacher teacher) {
		entityManager.getTransaction().begin();
		entityManager.persist(teacher);
		entityManager.getTransaction().commit();
	}

	@Override
	public void delete(Teacher teacher) {
		entityManager.getTransaction().begin();
		entityManager.remove(teacher);
		entityManager.getTransaction().commit();
	}

}
