package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.EnrollmentDAO;
import com.corbinelli.giamberini.examManagement.model.Course;
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnrollmentService {

	@Inject
	private EnrollmentDAO enrollmentDAO;
	
	public EnrollmentService() {}

	public void enroll(Enrollment enrollment) {
		enrollmentDAO.save(enrollment);
	}
	
	public void disenroll(Enrollment enrollment) {
		enrollmentDAO.delete(enrollment);
	}
	
	public Enrollment getEnrollmentInfoById(Long id) {
		return enrollmentDAO.findById(id);
	}
	
	public List<Exam> getExamsByStudent(Student student){
		return enrollmentDAO.findExamsByStudent(student);
	}
	
	public List<Student> getStudentsEnrolledForAnExam(Exam exam){
		return enrollmentDAO.findStudentsByExam(exam);
	}
	
	public List<Exam> getTrialsForAnExamByStudentAndCourse(Student student, Course course){
		return enrollmentDAO.findExamsEnrollmentsByStudentAndCourse(student, course);
	}
}
