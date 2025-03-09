package com.corbinelli.giamberini.examManagement.services;

import java.util.List;

import com.corbinelli.giamberini.examManagement.daos.EnrollmentDAO;
import com.corbinelli.giamberini.examManagement.daos.ExamDAO;
import com.corbinelli.giamberini.examManagement.daos.StudentDAO;
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
	
	@Inject 
	private StudentDAO studentDAO;
	
	@Inject 
	private ExamDAO examDAO;

	
	public EnrollmentService() {}

	public void enroll(Enrollment enrollment) {
		Long studentID = enrollment.getStudent().getId();
		Student existingStudent = studentDAO.findById(studentID);
		System.out.println(existingStudent);
		if (existingStudent == null) {
			throw new IllegalArgumentException("Student not found with ID: " + studentID);
		}
		
		Long examID = enrollment.getExam().getId();
		Exam existingExam = examDAO.findById(examID);
		System.out.println(existingExam);
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
	
	public List<Exam> getExamsByStudent(Student student){
		return enrollmentDAO.findExamsByStudent(student);
	}
	
	/*
	 * public List<Exam> getExamsByStudent(Student student){
		Long studentID = student.getId();
		Student existingStudent = studentDAO.findById(studentID);
		if (existingStudent == null) {
			throw new IllegalArgumentException("Student not found with ID: " + studentID);
		}
		return enrollmentDAO.findExamsByStudent(existingStudent);
	}
	 */
	
	public List<Student> getStudentsEnrolledForAnExam(Exam exam){
		return enrollmentDAO.findStudentsByExam(exam);
	}
	
	public List<Exam> getTrialsForAnExamByStudentAndCourse(Student student, Course course){
		return enrollmentDAO.findExamsEnrollmentsByStudentAndCourse(student, course);
	}
}
