package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;
import com.corbinelli.giamberini.examManagement.model.Teacher;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnrollmentDAO extends BaseDAO<Enrollment> {

	@Override
	public Enrollment findById(Long id) {
		return entityManager.find(Enrollment.class, id);
	}

	@Override
	public List<Enrollment> findAll() {
		return entityManager.createQuery("SELECT e FROM Enrollment e", Enrollment.class).getResultList();
	}

	@Override
	public void save(Enrollment enrollment) {
		entityManager.getTransaction().begin();
		entityManager.persist(enrollment);
		entityManager.getTransaction().commit();
	}

	@Override
	public Enrollment delete(Long id) {
		entityManager.getTransaction().begin();
		Enrollment enrollment = entityManager.find(Enrollment.class, id);
		if(enrollment != null) {
			entityManager.remove(enrollment);
		}
		entityManager.getTransaction().commit();
		return enrollment;
	}
	
	public List<Exam> findExamsByStudent(Student student){
		return entityManager.createQuery("SELECT e.exam FROM Enrollment e WHERE e.student = :student",Exam.class)
				.setParameter("student", student)
				.getResultList();
	}
	
	public List<Student> findStudentsByExam(Exam exam){
		return entityManager.createQuery("SELECT e.student FROM Enrollment e WHERE e.exam = :exam", Student.class)
				.setParameter("exam", exam)
				.getResultList();
	}
	
	public List<Exam> findExamsEnrollmentsByStudentAndCourse(Student student, Course course) {
		return entityManager.createQuery("SELECT e.exam FROM Enrollment e WHERE e.student = :student AND e.exam.course = :course", Exam.class)
				.setParameter("student", student)
				.setParameter("course", course)
				.getResultList();
	}

	
}
