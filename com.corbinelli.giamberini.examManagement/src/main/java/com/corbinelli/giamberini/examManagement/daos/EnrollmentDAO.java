package com.corbinelli.giamberini.examManagement.daos;

import java.util.List;

import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;

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
	
	public List<Exam> findExamsByStudent(Long studentID){
		return entityManager.createQuery("SELECT e.exam FROM Enrollment e WHERE e.student.id = :studentID",Exam.class)
				.setParameter("studentID", studentID)
				.getResultList();
	}
	
	public List<Student> findStudentsByExam(Long examID){
		return entityManager.createQuery("SELECT e.student FROM Enrollment e WHERE e.exam.id = :examID", Student.class)
				.setParameter("examID", examID)
				.getResultList();
	}
	
	public List<Exam> findExamsEnrollmentsByStudentAndCourse(Long studentID, Long courseID) {
		return entityManager.createQuery("SELECT e.exam FROM Enrollment e WHERE e.student.id = :studentID AND e.exam.course.id = :courseID", Exam.class)
				.setParameter("studentID", studentID)
				.setParameter("courseID", courseID)
				.getResultList();
	}

	
}
