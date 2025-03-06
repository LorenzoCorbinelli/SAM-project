package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
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
	public Teacher delete(Long id) {
		entityManager.getTransaction().begin();
		Teacher teacher = entityManager.find(Teacher.class, id);
		if(teacher != null) {
			entityManager.remove(teacher);
		}
		entityManager.getTransaction().commit();
		return teacher;
	}
	
	public List<Teacher> findByNameAndSurname(String name, String surname) {
		return entityManager.createQuery("SELECT t FROM Teacher t WHERE t.name = :name AND t.surname = :surname", 
				Teacher.class)
				.setParameter("name", name)
				.setParameter("surname", surname)
				.getResultList();
	}

}
