package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.EnrollmentDAO;
import com.corbinelli.giamberini.examManagement.daos.ExamDAO;
import com.corbinelli.giamberini.examManagement.daos.StudentDAO;
import com.corbinelli.giamberini.examManagement.model.Enrollment;
import com.corbinelli.giamberini.examManagement.model.Exam;
import com.corbinelli.giamberini.examManagement.model.Student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EnrollmentService {

	@Inject
	private EnrollmentDAO enrollmentDAO;
	
	@Inject 
	private StudentDAO studentDAO;
	
	@Inject 
	private ExamDAO examDAO;

	
	public EnrollmentService() {}

	public void enroll(Enrollment enrollment) {
		Long studentID = enrollment.getStudent().getId();
		Student existingStudent = studentDAO.findById(studentID);
		if (existingStudent == null) {
			throw new IllegalArgumentException("Student not found with ID: " + studentID);
		}
		
		Long examID = enrollment.getExam().getId();
		Exam existingExam = examDAO.findById(examID);
		if (existingExam == null) {
			throw new IllegalArgumentException("Exam not found with ID: " + examID);
		}
		
		enrollment.setExam(existingExam);
		enrollment.setStudent(existingStudent);
		enrollmentDAO.save(enrollment);
	}
	
	public Enrollment disenroll(Long id) {
		return enrollmentDAO.delete(id);
	}
	
	public Enrollment getEnrollmentInfoById(Long id) {
		return enrollmentDAO.findById(id);
	}
	
	public List<Exam> getExamsByStudent(Long studentID){
		return enrollmentDAO.findExamsByStudent(studentID);
	}
	
	public List<Student> getStudentsEnrolledForAnExam(Long examID){
		return enrollmentDAO.findStudentsByExam(examID);
	}
	
	public List<Exam> getTrialsForAnExamByStudentAndCourse(Long studentID, Long courseID){
		return enrollmentDAO.findExamsEnrollmentsByStudentAndCourse(studentID, courseID);
	}
}
