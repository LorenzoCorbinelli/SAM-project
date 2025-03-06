package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
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
	public Course delete(Long id) {
		entityManager.getTransaction().begin();
		Course course = entityManager.find(Course.class, id);
		if(course != null) {
			entityManager.remove(course);
		}
		entityManager.getTransaction().commit();
		return course;
	}
	
	public List<Course> findCoursesByTeacher(Teacher teacher) {
		return entityManager.createQuery("SELECT c FROM Course c WHERE c.teacher = :teacher", Course.class)
				.setParameter("teacher", teacher)
				.getResultList();
	}

	public List<Course> findCoursesByName(String name) {
		return entityManager.createQuery("SELECT c FROM Course c WHERE c.name = :name", Course.class)
				.setParameter("name", name.toUpperCase())
				.getResultList();
	}
	
	public List<Course> findCoursesByPartialDescription(String partialDescription) {
		return entityManager.createQuery("SELECT c FROM Course c WHERE LOWER(CAST(c.description AS string)) LIKE :partialDescription", Course.class)
				.setParameter("partialDescription", "%" + partialDescription.toLowerCase() + "%")
				.getResultList();
	}
}
