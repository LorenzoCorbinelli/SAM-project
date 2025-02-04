package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Teacher;

public class CourseDAO extends BaseDAO<Course> {

	@Override
	public Course findById(Long id) {
		return entityManager.find(Course.class, id);
	}

	@Override
	public List<Course> findAll() {
		return entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
	}

	@Override
	public void save(Course course) {
		entityManager.getTransaction().begin();
		entityManager.persist(course);
		entityManager.getTransaction().commit();
	}

	@Override
	public void delete(Course course) {
		entityManager.getTransaction().begin();
		entityManager.remove(course);
		entityManager.getTransaction().commit();
	}
	
	public List<Course> findCoursesByTeacher(Teacher teacher) {
		return entityManager.createQuery("SELECT c FROM Course c WHERE c.teacher = :teacher", Course.class)
				.setParameter("teacher", teacher)
				.getResultList();
	}

}
