package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class StudentDAO extends BaseDAO<Student> {

	@Override
	public Student findById(Long id) {
		return entityManager.find(Student.class, id);
	}

	@Override
	public List<Student> findAll() {
		return entityManager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
	}

	@Override
	public void save(Student student) {
		entityManager.getTransaction().begin();
		entityManager.persist(student);
		entityManager.getTransaction().commit();
	}

	@Override
	public void delete(Student student) {
		entityManager.getTransaction().begin();
		entityManager.remove(student);
		entityManager.getTransaction().commit();
	}
	
	public List<Student> findByNameAndSurname(String name, String surname) {
		return entityManager.createQuery("SELECT s FROM Student s WHERE s.name = :name AND s.surname = :surname", 
				Student.class)
				.setParameter("name", name)
				.setParameter("surname", surname)
				.getResultList();
	}

}
