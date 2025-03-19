package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
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
	
	public List<Course> findCoursesByTeacher(Long teacherID) {
		return entityManager.createQuery("SELECT c FROM Course c WHERE c.teacher.id = :teacherID", Course.class)
				.setParameter("teacherID", teacherID)
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
