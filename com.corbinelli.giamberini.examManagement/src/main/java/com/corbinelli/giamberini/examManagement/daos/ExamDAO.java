package com.corbinelli.giamberini.examManagement.daos;

import java.time.LocalDate;
import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Exam;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class ExamDAO extends BaseDAO<Exam> {

	@Override
	public Exam findById(Long id) {
		return entityManager.find(Exam.class, id);
	}

	@Override
	public List<Exam> findAll() {
		return entityManager.createQuery("SELECT e FROM Exam e", Exam.class).getResultList();
	}

	@Override
	public void save(Exam exam) {
		entityManager.getTransaction().begin();
		entityManager.persist(exam);
		entityManager.getTransaction().commit();
	}

	@Override
	public void delete(Exam exam) {
		entityManager.getTransaction().begin();
		entityManager.remove(exam);
		entityManager.getTransaction().commit();
	}
	
	public List<LocalDate> findExamDatesByCourse(Long courseID) {
		return entityManager.createQuery("SELECT e.date FROM Exam e WHERE e.course.id = :courseID", LocalDate.class)
				.setParameter("courseID", courseID)
				.getResultList();
	}
	
	public List<Course> findExamCoursesByDate(LocalDate date) {
		return entityManager.createQuery("SELECT e.course FROM Exam e WHERE e.date = :date", Course.class)
				.setParameter("date", date)
				.getResultList();
	}
	
	public List<Exam> findExamsInAPeriod(LocalDate startPeriod, LocalDate endPeriod) {
		return entityManager.createQuery("SELECT e FROM Exam e WHERE e.date BETWEEN :startDate AND :endDate", Exam.class)
				.setParameter("startDate", startPeriod)
				.setParameter("endDate", endPeriod)
				.getResultList();
	}

}
